package edu.fpt.swp391.g2.imageexp.server.handler.like;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.sun.net.httpserver.HttpExchange;
import edu.fpt.swp391.g2.imageexp.processor.LikeProcessor;
import edu.fpt.swp391.g2.imageexp.processor.UserProcessor;
import edu.fpt.swp391.g2.imageexp.server.handler.SecuredJsonHandler;
import edu.fpt.swp391.g2.imageexp.utils.HandlerUtils;

import java.io.IOException;
import java.io.InvalidObjectException;

/**
 * Get the post ids that the user liked
 */
public class GetLikedPostIdsHandler extends SecuredJsonHandler {
    @Override
    public void handleJsonRequest(HttpExchange httpExchange, JsonValue body) throws IOException {
        if (!body.isObject()) {
            HandlerUtils.sendServerErrorResponse(httpExchange, new InvalidObjectException("Only Json Object is allowed"));
            return;
        }
        JsonObject jsonObject = body.asObject();
        int userId = jsonObject.getInt("userId", -1);
        JsonObject response = new JsonObject();
        try {
            if (!UserProcessor.getUserById(userId).isPresent()) {
                response.set("success", false);
                JsonObject message = new JsonObject();
                message.set("message", "That user id doesn't exist");
                response.set("response", message);
            } else {
                JsonArray jsonArray = new JsonArray();
                LikeProcessor.getLikedPostIds(userId).forEach(jsonArray::add);
                response.set("success", true);
                response.set("response", jsonArray);
            }
            HandlerUtils.sendJsonResponse(httpExchange, 200, response);
        } catch (Exception e) {
            HandlerUtils.sendServerErrorResponse(httpExchange, e);
        }
    }
}
