package edu.fpt.swp391.g2.imageexp.server.handler.like;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.sun.net.httpserver.HttpExchange;
import edu.fpt.swp391.g2.imageexp.processor.LikeProcessor;
import edu.fpt.swp391.g2.imageexp.processor.PostProcessor;
import edu.fpt.swp391.g2.imageexp.processor.UserProcessor;
import edu.fpt.swp391.g2.imageexp.server.handler.SecuredJsonHandler;
import edu.fpt.swp391.g2.imageexp.utils.HandlerUtils;

import java.io.IOException;
import java.io.InvalidObjectException;

/**
 * Check if the user likes the post
 */
public class CheckLikeHandler extends SecuredJsonHandler {
    @Override
    public void handleJsonRequest(HttpExchange httpExchange, JsonValue body) throws IOException {
        if (!body.isObject()) {
            HandlerUtils.sendServerErrorResponse(httpExchange, new InvalidObjectException("Only Json Object is allowed"));
            return;
        }
        JsonObject jsonObject = body.asObject();
        int userId = jsonObject.getInt("userId", -1);
        int postId = jsonObject.getInt("postId", -1);
        JsonObject response = new JsonObject();
        try {
            JsonObject message = new JsonObject();
            if (!UserProcessor.getUserById(userId).isPresent()) {
                response.set("success", false);
                message.set("message", "That user id doesn't exist");
            } else if (!PostProcessor.getPostById(postId).isPresent()) {
                response.set("success", false);
                message.set("message", "That post id doesn't exist");
            } else {
                boolean liked = LikeProcessor.isLiked(userId, postId);
                response.set("success", true);
                message.set("isLiked", liked);
            }
            response.set("response", message);
            HandlerUtils.sendJsonResponse(httpExchange, 200, response);
        } catch (Exception e) {
            HandlerUtils.sendServerErrorResponse(httpExchange, e);
        }
    }
}
