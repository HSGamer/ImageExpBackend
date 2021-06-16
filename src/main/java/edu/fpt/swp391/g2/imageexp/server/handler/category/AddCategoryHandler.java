package edu.fpt.swp391.g2.imageexp.server.handler.category;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.sun.net.httpserver.HttpExchange;
import edu.fpt.swp391.g2.imageexp.processor.CategoryProcessor;
import edu.fpt.swp391.g2.imageexp.server.handler.SecuredJsonHandler;
import edu.fpt.swp391.g2.imageexp.utils.HandlerUtils;

import java.io.IOException;
import java.io.InvalidObjectException;

public class AddCategoryHandler extends SecuredJsonHandler {
    @Override
    public void handleJsonRequest(HttpExchange httpExchange, JsonValue body) throws IOException {
        if (!body.isObject()) {
            HandlerUtils.sendServerErrorResponse(httpExchange, new InvalidObjectException("Only Json Object is allowed"));
            return;
        }
        JsonObject jsonObject = body.asObject();
        String categoryName = jsonObject.getString("name", "");
        JsonObject response = new JsonObject();
        try {
            JsonObject message = new JsonObject();
            if (categoryName.isEmpty()) {
                response.set("success", false);
                message.set("message", "Invalid format");
            } else if (CategoryProcessor.checkCategoryExists(categoryName)) {
                response.set("success", false);
                message.set("message", "That category already exists");
            } else {
                CategoryProcessor.addCategory(categoryName);
                response.set("success", true);
                message.set("message", "Successfully added");
            }
            response.set("response", message);
            HandlerUtils.sendJsonResponse(httpExchange, 200, response);
        } catch (Exception e) {
            HandlerUtils.sendServerErrorResponse(httpExchange, e);
        }
    }
}
