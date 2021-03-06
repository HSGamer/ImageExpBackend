package edu.fpt.swp391.g2.imageexp.server.handler.comment;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.sun.net.httpserver.HttpExchange;
import edu.fpt.swp391.g2.imageexp.processor.CommentProcessor;
import edu.fpt.swp391.g2.imageexp.processor.PostProcessor;
import edu.fpt.swp391.g2.imageexp.processor.UserProcessor;
import edu.fpt.swp391.g2.imageexp.server.handler.SecuredJsonHandler;
import edu.fpt.swp391.g2.imageexp.utils.HandlerUtils;

import java.io.IOException;
import java.io.InvalidObjectException;

/**
 * Get the comments in the post that the user commented
 */
public class GetCommentsByUserIdAndPostIdHandler extends SecuredJsonHandler {
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
            if (UserProcessor.getUserById(userId).isPresent() && PostProcessor.getPostById(postId).isPresent()) {
                response.set("success", true);
                JsonArray jsonArray = new JsonArray();
                CommentProcessor.getCommentByUserIdAndPostId(userId, postId).forEach(comment -> jsonArray.add(comment.toJsonObject()));
                response.set("response", jsonArray);
            } else {
                response.set("success", false);
                JsonObject message = new JsonObject();
                message.set("message", "That user id or post id doesn't exist");
                response.set("response", message);
            }
            HandlerUtils.sendJsonResponse(httpExchange, 200, response);
        } catch (Exception e) {
            HandlerUtils.sendServerErrorResponse(httpExchange, e);
        }
    }
}
