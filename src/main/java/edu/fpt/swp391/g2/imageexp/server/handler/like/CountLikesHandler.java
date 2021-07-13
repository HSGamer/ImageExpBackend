package edu.fpt.swp391.g2.imageexp.server.handler.like;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.sun.net.httpserver.HttpExchange;
import edu.fpt.swp391.g2.imageexp.processor.LikeProcessor;
import edu.fpt.swp391.g2.imageexp.processor.PostProcessor;
import edu.fpt.swp391.g2.imageexp.server.handler.SecuredJsonHandler;
import edu.fpt.swp391.g2.imageexp.utils.HandlerUtils;

import java.io.IOException;
import java.io.InvalidObjectException;

/**
 * Count the likes of the post
 */
public class CountLikesHandler extends SecuredJsonHandler {
    @Override
    public void handleJsonRequest(HttpExchange httpExchange, JsonValue body) throws IOException {
        if (!body.isObject()) {
            HandlerUtils.sendServerErrorResponse(httpExchange, new InvalidObjectException("Only Json Object is allowed"));
            return;
        }
        JsonObject jsonObject = body.asObject();
        int postId = jsonObject.getInt("postId", -1);
        JsonObject response = new JsonObject();
        try {
            if (!PostProcessor.getPostById(postId).isPresent()) {
                response.set("success", false);
                JsonObject message = new JsonObject();
                message.set("message", "That post id doesn't exist");
                response.set("response", message);
            } else {
                response.set("success", true);
                response.set("response", LikeProcessor.countLikes(postId));
            }
            HandlerUtils.sendJsonResponse(httpExchange, 200, response);
        } catch (Exception e) {
            HandlerUtils.sendServerErrorResponse(httpExchange, e);
        }
    }
}
