package edu.fpt.swp391.g2.imageexp.server.handler.user;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.sun.net.httpserver.HttpExchange;
import edu.fpt.swp391.g2.imageexp.config.MainConfig;
import edu.fpt.swp391.g2.imageexp.entity.User;
import edu.fpt.swp391.g2.imageexp.processor.UserProcessor;
import edu.fpt.swp391.g2.imageexp.processor.VerifyProcessor;
import edu.fpt.swp391.g2.imageexp.server.handler.SecuredJsonHandler;
import edu.fpt.swp391.g2.imageexp.utils.HandlerUtils;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.util.Optional;

public class RegisterUserHandler extends SecuredJsonHandler {
    @Override
    public void handleJsonRequest(HttpExchange httpExchange, JsonValue body) throws IOException {
        if (!body.isObject()) {
            HandlerUtils.sendServerErrorResponse(httpExchange, new InvalidObjectException("Only Json Object is allowed"));
            return;
        }
        JsonObject jsonObject = body.asObject();
        String email = jsonObject.getString("email", "");
        String password = jsonObject.getString("password", "");

        JsonObject response = new JsonObject();
        try {
            JsonObject message = new JsonObject();
            if (email.isEmpty() || password.isEmpty()) {
                response.set("success", false);
                message.set("message", "Invalid format");
            } else if (UserProcessor.checkEmailExists(email)) {
                response.set("success", false);
                message.set("message", "That email already exists");
            } else {
                UserProcessor.registerUser(email, password);
                Optional<User> optionalUser = UserProcessor.loginUser(email, password);
                if (optionalUser.isPresent()) {
                    if (MainConfig.EMAIL_VERIFICATION_SEND_ON_REGISTER.getValue()) {
                        VerifyProcessor.createAndSendVerifyCode(optionalUser.get());
                    }
                    response.set("success", true);
                    message.set("message", "Successfully registered");
                } else {
                    response.set("success", false);
                    message.set("message", "Unknown error when registering");
                }
            }
            response.set("response", message);
            HandlerUtils.sendJsonResponse(httpExchange, 200, response);
        } catch (Exception e) {
            HandlerUtils.sendServerErrorResponse(httpExchange, e);
        }
    }
}
