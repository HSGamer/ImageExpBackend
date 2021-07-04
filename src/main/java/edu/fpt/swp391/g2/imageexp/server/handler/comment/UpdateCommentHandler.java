package edu.fpt.swp391.g2.imageexp.server.handler.comment;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.sun.net.httpserver.HttpExchange;
import edu.fpt.swp391.g2.imageexp.processor.CommentProcessor;
import edu.fpt.swp391.g2.imageexp.server.handler.SecuredJsonHandler;
import edu.fpt.swp391.g2.imageexp.utils.HandlerUtils;

import java.io.IOException;
import java.io.InvalidObjectException;

public class UpdateCommentHandler extends SecuredJsonHandler {
    @Override
    public void handleJsonRequest(HttpExchange httpExchange, JsonValue body) throws IOException {
        if (!body.isObject()) {
            HandlerUtils.sendServerErrorResponse(httpExchange, new InvalidObjectException("Only Json Object is allowed"));
            return;
        }
        JsonObject jsonObject = body.asObject();
        int commentId = jsonObject.getInt("commentId", -1);
        String comment = jsonObject.getString("comment", "");

        JsonObject response = new JsonObject();
        try {
            JsonObject message = new JsonObject();
            if (!CommentProcessor.getCommentById(commentId).isPresent()) {
                response.set("success", false);
                message.set("message", "The comment id doesn't exist");
            }  else if (comment.isEmpty()) {
                response.set("success", false);
                message.set("message", "Invalid format");
            }else {
                CommentProcessor.updateComment(commentId , comment);
                response.set("success", true);
                message.set("message", "Successfully updated");
            }
            response.set("response", message);
            HandlerUtils.sendJsonResponse(httpExchange, 200, response);
        } catch (Exception e) {
            HandlerUtils.sendServerErrorResponse(httpExchange, e);
        }
    }
}
