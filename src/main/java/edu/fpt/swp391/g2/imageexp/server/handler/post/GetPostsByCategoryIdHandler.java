package edu.fpt.swp391.g2.imageexp.server.handler.post;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.sun.net.httpserver.HttpExchange;
import edu.fpt.swp391.g2.imageexp.processor.CategoryProcessor;
import edu.fpt.swp391.g2.imageexp.processor.PostProcessor;
import edu.fpt.swp391.g2.imageexp.server.handler.SecuredJsonHandler;
import edu.fpt.swp391.g2.imageexp.utils.HandlerUtils;

import java.io.IOException;
import java.io.InvalidObjectException;

/**
 * Get the posts by the category
 */
public class GetPostsByCategoryIdHandler extends SecuredJsonHandler {
    @Override
    public void handleJsonRequest(HttpExchange httpExchange, JsonValue body) throws IOException {
        if (!body.isObject()) {
            HandlerUtils.sendServerErrorResponse(httpExchange, new InvalidObjectException("Only Json Object is allowed"));
            return;
        }
        JsonObject jsonObject = body.asObject();
        int id = jsonObject.getInt("id", -1);

        JsonObject response = new JsonObject();
        try {
            if (CategoryProcessor.getCategoryById(id).isPresent()) {
                response.set("success", true);
                JsonArray jsonArray = new JsonArray();
                PostProcessor.getPostsByCategoryId(id).forEach(post -> jsonArray.add(post.toJsonObject()));
                response.set("response", jsonArray);
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
