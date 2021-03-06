package edu.fpt.swp391.g2.imageexp.server.handler.post;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.sun.net.httpserver.HttpExchange;
import edu.fpt.swp391.g2.imageexp.processor.PostProcessor;
import edu.fpt.swp391.g2.imageexp.server.handler.SecuredJsonHandler;
import edu.fpt.swp391.g2.imageexp.utils.HandlerUtils;

import java.io.IOException;
import java.io.InvalidObjectException;

/**
 * Get list post by search key
 */
public class GetPostsBySearchKeyHandler extends SecuredJsonHandler {
    public void handleJsonRequest(HttpExchange httpExchange, JsonValue body) throws IOException {
        if (!body.isObject()) {
            HandlerUtils.sendServerErrorResponse(httpExchange, new InvalidObjectException("Only Json Object is allowed"));
            return;
        }
        JsonObject jsonObject = body.asObject();
        String searchKey = jsonObject.getString("searchKey", "");

        JsonObject response = new JsonObject();
        try {
            if (!searchKey.isEmpty()) {
                response.set("success", true);
                JsonArray jsonArray = new JsonArray();
                PostProcessor.getPostsBySearchKey(searchKey).forEach(post -> jsonArray.add(post.toJsonObject()));
                response.set("response", jsonArray);
            } else {
                response.set("success", false);
                JsonObject message = new JsonObject();
                message.set("message", "Cannot find any post related to the search key");
                response.set("response", message);
            }
            HandlerUtils.sendJsonResponse(httpExchange, 200, response);
        } catch (Exception e) {
            HandlerUtils.sendServerErrorResponse(httpExchange, e);
        }
    }
}
