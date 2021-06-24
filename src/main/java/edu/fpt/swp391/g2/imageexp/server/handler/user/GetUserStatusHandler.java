package edu.fpt.swp391.g2.imageexp.server.handler.user;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.sun.net.httpserver.HttpExchange;
import edu.fpt.swp391.g2.imageexp.entity.Category;
import edu.fpt.swp391.g2.imageexp.entity.User;
import edu.fpt.swp391.g2.imageexp.processor.UserProcessor;
import edu.fpt.swp391.g2.imageexp.server.handler.SecuredJsonHandler;
import edu.fpt.swp391.g2.imageexp.utils.HandlerUtils;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.util.Optional;

public class GetUserStatusHandler extends SecuredJsonHandler {
    @Override
    public void handleJsonRequest(HttpExchange httpExchange, JsonValue body) throws IOException {
        if (!body.isObject()) {
            HandlerUtils.sendServerErrorResponse(httpExchange, new InvalidObjectException("Only Json Object is allowed"));
            return;
        }
        JsonObject jsonObject = body.asObject();
        String email = jsonObject.getString("email", "");
        String status = jsonObject.getString("status", "");

        JsonObject response = new JsonObject();
        //String status;
        Optional<User> optionalUser ;
        try {
            //status = UserProcessor.getStatus(email);
            optionalUser = UserProcessor.getUserByEmail(email);
            if (optionalUser.isPresent()) {
                response.set("success", true);
                response.set("response", optionalUser.get().toJsonObject());
                //response.set("response", status.get().toJsonObject());
            } else {
                response.set("success", false);
                JsonObject message = new JsonObject();
                message.set("message", "That user id doesn't exist");
                response.set("response", message);
            }
            HandlerUtils.sendJsonResponse(httpExchange, 200, response);
        } catch (Exception e) {
            HandlerUtils.sendServerErrorResponse(httpExchange, e);
        }
    }
}
