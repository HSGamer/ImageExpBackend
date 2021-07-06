package edu.fpt.swp391.g2.imageexp.server.handler.user;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.sun.net.httpserver.HttpExchange;
import edu.fpt.swp391.g2.imageexp.processor.UserProcessor;
import edu.fpt.swp391.g2.imageexp.server.handler.SecuredJsonHandler;
import edu.fpt.swp391.g2.imageexp.utils.HandlerUtils;

import java.io.IOException;
import java.io.InvalidObjectException;

/**
 * Change the user status
 */
public class ChangeUserStatusHandler extends SecuredJsonHandler {

    @Override
    public void handleJsonRequest(HttpExchange httpExchange, JsonValue body) throws IOException {
        if (!body.isObject()) {
            HandlerUtils.sendServerErrorResponse(httpExchange, new InvalidObjectException("Only Json Object is allowed"));
        }
        JsonObject jsonObject = body.asObject();
        String email = jsonObject.getString("email", "");
        String status = jsonObject.getString("status", "");

        JsonObject response = new JsonObject();
        try {
            JsonObject message = new JsonObject();
            if (email.isEmpty() || status.isEmpty()) {
                response.set("success", false);
                message.set("message", "Invalid format");
            } else if (!UserProcessor.checkEmailExists(email)) {
                response.set("success", false);
                message.set("message", "That email doesn't exist");
            } else {
                UserProcessor.changeStatus(email, status);
                response.set("success", true);
                message.set("message", "Successfully updated");
            }
            response.set("response", message);
            HandlerUtils.sendJsonResponse(httpExchange, 200, response);
        } catch (Exception e) {
            HandlerUtils.sendServerErrorResponse(httpExchange, e);
        }
    }
}
