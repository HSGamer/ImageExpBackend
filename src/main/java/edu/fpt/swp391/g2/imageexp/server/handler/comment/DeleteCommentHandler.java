package edu.fpt.swp391.g2.imageexp.server.handler.comment;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.sun.net.httpserver.HttpExchange;
import edu.fpt.swp391.g2.imageexp.entity.Comment;
import edu.fpt.swp391.g2.imageexp.processor.CommentProcessor;
import edu.fpt.swp391.g2.imageexp.processor.PostProcessor;
import edu.fpt.swp391.g2.imageexp.processor.UserProcessor;
import edu.fpt.swp391.g2.imageexp.server.handler.SecuredJsonHandler;
import edu.fpt.swp391.g2.imageexp.utils.HandlerUtils;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.util.Optional;

public class DeleteCommentHandler extends SecuredJsonHandler {
    @Override
    public void handleJsonRequest(HttpExchange httpExchange, JsonValue body) throws IOException {
        if (!body.isObject()) {
            HandlerUtils.sendServerErrorResponse(httpExchange, new InvalidObjectException("Only Json Object is allowed"));
            return;
        }
        JsonObject jsonObject = body.asObject();
        int commentId = jsonObject.getInt("commentId", -1);
        int postId = jsonObject.getInt("postId", -1);
        int userId = jsonObject.getInt("userId", -1);


        JsonObject response = new JsonObject();
        try {
            Optional<Comment> optionalComment = CommentProcessor.getCommentById(commentId);
            JsonObject message = new JsonObject();
            if (!optionalComment.isPresent()) {
                response.set("success", false);
                message.set("message", "The comment id doesn't exist");
            } else if (!PostProcessor.getPostById(postId).isPresent()) {
                response.set("success", false);
                message.set("message", "The post id doesn't exist");
            } else if (!UserProcessor.getUserById(userId).isPresent()) {
                response.set("success", false);
                message.set("message", "The user id doesn't exist");
            } else {
                Comment comment = optionalComment.get();
                if (userId != comment.getUserId()) {
                    response.set("success", false);
                    message.set("message", "The user is not the owner of the comment");
                } else {
                    response.set("success", true);
                    message.set("message", "Successfully deleted");
                }
            }
        response.set("response", message);
        HandlerUtils.sendJsonResponse(httpExchange, 200, response);
    } catch(
    Exception e)

    {
        HandlerUtils.sendServerErrorResponse(httpExchange, e);
    }
}
}