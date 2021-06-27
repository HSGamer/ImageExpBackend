package edu.fpt.swp391.g2.imageexp.server.handler.misc;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.sun.net.httpserver.HttpExchange;
import edu.fpt.swp391.g2.imageexp.email.EmailHandler;
import edu.fpt.swp391.g2.imageexp.server.handler.SecuredJsonHandler;
import edu.fpt.swp391.g2.imageexp.utils.HandlerUtils;

import java.io.IOException;
import java.io.InvalidObjectException;

public class TestEmailHandler extends SecuredJsonHandler {
    @Override
    public void handleJsonRequest(HttpExchange httpExchange, JsonValue body) throws IOException {
        if (!body.isObject()) {
            HandlerUtils.sendServerErrorResponse(httpExchange, new InvalidObjectException("Only Json Object is allowed"));
            return;
        }
        JsonObject jsonObject = body.asObject();
        String email = jsonObject.getString("email", "");

        JsonObject response = new JsonObject();
        JsonObject message = new JsonObject();
        if (email.isEmpty()) {
            response.set("success", false);
            message.set("message", "Invalid format");
        } else {
            EmailHandler.sendEmailAsync(email, "Test Email", "<h1>This is a test email through the handler</h1>");
            response.set("success", true);
            message.set("message", "Sent test email");
        }
    }
}
