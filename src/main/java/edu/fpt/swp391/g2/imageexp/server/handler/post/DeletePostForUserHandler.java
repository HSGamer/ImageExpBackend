package edu.fpt.swp391.g2.imageexp.server.handler.post;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.sun.net.httpserver.HttpExchange;
import edu.fpt.swp391.g2.imageexp.entity.Post;
import edu.fpt.swp391.g2.imageexp.entity.User;
import edu.fpt.swp391.g2.imageexp.processor.PostProcessor;
import edu.fpt.swp391.g2.imageexp.processor.UserProcessor;
import edu.fpt.swp391.g2.imageexp.server.handler.SecuredJsonHandler;
import edu.fpt.swp391.g2.imageexp.utils.HandlerUtils;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.util.Optional;

/**
 * Delete the post for the user
 */
public class DeletePostForUserHandler extends SecuredJsonHandler {
    @Override
    public void handleJsonRequest(HttpExchange httpExchange, JsonValue body) throws IOException {
        if (!body.isObject()) {
            HandlerUtils.sendServerErrorResponse(httpExchange, new InvalidObjectException("Only Json Object is allowed"));
            return;
        }
        JsonObject jsonObject = body.asObject();
        int postId = jsonObject.getInt("postId", -1);
        int userId = jsonObject.getInt("userId", -1);

        JsonObject response = new JsonObject();
        try {
            JsonObject message = new JsonObject();
            Optional<Post> optionalPost = PostProcessor.getPostById(postId);
            Optional<User> optionalUser = UserProcessor.getUserById(userId);
            if (!optionalPost.isPresent()) {
                response.set("success", false);
                message.set("message", "The post id doesn't exist");
            } else if (!optionalUser.isPresent()) {
                response.set("success", false);
                message.set("message", "The user id doesn't exist");
            } else {
                Post post = optionalPost.get();
                User user = optionalUser.get();
                if (post.getUserId() != user.getUserId()) {
                    response.set("success", false);
                    message.set("message", "The user is not the owner of the post");
                } else {
                    PostProcessor.deletePost(post.getId());
                    response.set("success", true);
                    message.set("message", "Successfully deleted");
                }
            }
            response.set("response", message);
            HandlerUtils.sendJsonResponse(httpExchange, 200, response);
        } catch (Exception e) {
            HandlerUtils.sendServerErrorResponse(httpExchange, e);
        }
    }
}
