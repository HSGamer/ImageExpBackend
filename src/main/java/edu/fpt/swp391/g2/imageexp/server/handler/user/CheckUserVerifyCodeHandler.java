package edu.fpt.swp391.g2.imageexp.server.handler.user;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.sun.net.httpserver.HttpExchange;
import edu.fpt.swp391.g2.imageexp.entity.User;
import edu.fpt.swp391.g2.imageexp.processor.UserProcessor;
import edu.fpt.swp391.g2.imageexp.processor.VerifyProcessor;
import edu.fpt.swp391.g2.imageexp.server.handler.SecuredJsonHandler;
import edu.fpt.swp391.g2.imageexp.utils.HandlerUtils;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.util.Optional;

public class CheckUserVerifyCodeHandler extends SecuredJsonHandler {
    @Override
    public void handleJsonRequest(HttpExchange httpExchange, JsonValue body) throws IOException {
        if (!body.isObject()) {
            HandlerUtils.sendServerErrorResponse(httpExchange, new InvalidObjectException("Only Json Object is allowed"));
            return;
        }
        JsonObject jsonObject = body.asObject();
        String email = jsonObject.getString("email", "");
        String code = jsonObject.getString("code", "");

        JsonObject response = new JsonObject();
        Optional<User> optionalUser;
        try {
            JsonObject message = new JsonObject();
            optionalUser = UserProcessor.getUserByEmail(email);
            if (code.isEmpty()) {
                response.set("success", false);
                message.set("message", "Invalid format");
            } else if (!optionalUser.isPresent()) {
                response.set("success", false);
                message.set("message", "That email doesn't exist");
            } else {
                User user = optionalUser.get();
                if (user.isVerified()) {
                    response.set("success", true);
                    message.set("message", "Already verified");
                } else if (VerifyProcessor.checkVerifyCode(user.getUserId(), code)) {
                    VerifyProcessor.setVerifyState(email, true);
                    response.set("success", true);
                    message.set("message", "Successfully verified");
                } else {
                    response.set("success", false);
                    message.set("message", "The code is incorrect");
                }
            }
            response.set("response", message);
            HandlerUtils.sendJsonResponse(httpExchange, 200, response);
        } catch (Exception e) {
            HandlerUtils.sendServerErrorResponse(httpExchange, e);
        }
    }
}
