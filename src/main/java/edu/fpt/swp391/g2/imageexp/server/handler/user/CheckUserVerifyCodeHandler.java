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
        String password = jsonObject.getString("password", "");
        String code = jsonObject.getString("code", "");

        JsonObject response = new JsonObject();
        Optional<User> optionalUser;
        try {
            optionalUser = UserProcessor.loginUser(email,password);
            if (email.isEmpty() || password.isEmpty() || code.isEmpty()) {
                response.set("success", false);
                JsonObject message = new JsonObject();
                message.set("message", "Invalid format");
                response.set("response", message);
            } else if (optionalUser.isPresent()) {
                int userId = optionalUser.get().getUserId();
                String checkCode = VerifyProcessor.checkVerifyCode(userId);
                if(checkCode != null && code.compareTo(checkCode)== 0){
                    response.set("success", true);
                    JsonObject message = new JsonObject();
                    message.set("message", "Successfully verified");
                    response.set("response", message);
                }
            } else {
                response.set("success", false);
                JsonObject message = new JsonObject();
                message.set("message", "That email,password or code is incorrect");
                response.set("response", message);
            }
            HandlerUtils.sendJsonResponse(httpExchange, 200, response);
        } catch (Exception e) {
            HandlerUtils.sendServerErrorResponse(httpExchange, e);
        }
    }
}
