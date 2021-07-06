package edu.fpt.swp391.g2.imageexp.server.handler.category;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.sun.net.httpserver.HttpExchange;
import edu.fpt.swp391.g2.imageexp.entity.Category;
import edu.fpt.swp391.g2.imageexp.processor.CategoryProcessor;
import edu.fpt.swp391.g2.imageexp.server.handler.SecuredJsonHandler;
import edu.fpt.swp391.g2.imageexp.utils.HandlerUtils;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.util.Optional;

/**
 * Get the category by its ID
 */
public class GetCategoryByIdHandler extends SecuredJsonHandler {
    @Override
    public void handleJsonRequest(HttpExchange httpExchange, JsonValue body) throws IOException {
        if (!body.isObject()) {
            HandlerUtils.sendServerErrorResponse(httpExchange, new InvalidObjectException("Only Json Object is allowed"));
            return;
        }
        JsonObject jsonObject = body.asObject();
        int id = jsonObject.getInt("id", -1);

        JsonObject response = new JsonObject();
        Optional<Category> optionalCategory;
        try {
            optionalCategory = CategoryProcessor.getCategoryById(id);
            if (optionalCategory.isPresent()) {
                response.set("success", true);
                response.set("response", optionalCategory.get().toJsonObject());
            } else {
                response.set("success", false);
                JsonObject message = new JsonObject();
                message.set("message", "That category id doesn't exist");
                response.set("response", message);
            }
            HandlerUtils.sendJsonResponse(httpExchange, 200, response);
        } catch (Exception e) {
            HandlerUtils.sendServerErrorResponse(httpExchange, e);
        }
    }
}
