package edu.fpt.swp391.g2.imageexp.server.handler.comment;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.sun.net.httpserver.HttpExchange;
import edu.fpt.swp391.g2.imageexp.processor.CommentProcessor;
import edu.fpt.swp391.g2.imageexp.processor.UserProcessor;
import edu.fpt.swp391.g2.imageexp.server.handler.SecuredJsonHandler;
import edu.fpt.swp391.g2.imageexp.utils.HandlerUtils;

import java.io.IOException;
import java.io.InvalidObjectException;

/**
 * Get the comments by the user
 */
public class GetCommentsByUserIdHandler extends SecuredJsonHandler {
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
            if (UserProcessor.getUserById(id).isPresent()) {
                response.set("success", true);
                JsonArray jsonArray = new JsonArray();
                CommentProcessor.getCommentsByUserId(id).forEach(comment -> jsonArray.add(comment.toJsonObject()));
                response.set("response", jsonArray);
            } else {
                response.set("success", false);
                JsonObject message = new JsonObject();
                message.set("message", "That user id doesn't exist");
                response.set("response", message);
            }
            HandlerUtils.sendJsonResponse(httpExchange, 200, response);
        } catch (Exception e) {
            HandlerUtils.sendServerErrorResponse(httpExchange, e);
        }
    }
}
