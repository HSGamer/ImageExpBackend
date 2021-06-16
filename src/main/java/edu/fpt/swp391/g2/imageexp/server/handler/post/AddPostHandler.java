package edu.fpt.swp391.g2.imageexp.server.handler.post;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.sun.net.httpserver.HttpExchange;
import edu.fpt.swp391.g2.imageexp.processor.CategoryProcessor;
import edu.fpt.swp391.g2.imageexp.processor.PostProcessor;
import edu.fpt.swp391.g2.imageexp.processor.UserProcessor;
import edu.fpt.swp391.g2.imageexp.server.handler.SecuredJsonHandler;
import edu.fpt.swp391.g2.imageexp.utils.HandlerUtils;

import java.io.IOException;
import java.io.InvalidObjectException;

public class AddPostHandler extends SecuredJsonHandler {
    @Override
    public void handleJsonRequest(HttpExchange httpExchange, JsonValue body) throws IOException {
        if (!body.isObject()) {
            HandlerUtils.sendServerErrorResponse(httpExchange, new InvalidObjectException("Only Json Object is allowed"));
            return;
        }
        JsonObject jsonObject = body.asObject();
        int userId = jsonObject.getInt("userId", -1);
        int picId = jsonObject.getInt("picId", -1);
        int categoryId = jsonObject.getInt("categoryId", -1);
        String keyword = jsonObject.getString("keyword", "");

        JsonObject response = new JsonObject();
        try {
            JsonObject message = new JsonObject();
            if (!UserProcessor.getUserById(userId).isPresent()) {
                response.set("success", false);
                message.set("message", "The user id doesn't exist");
            } else if (PostProcessor.checkPicturePosted(picId)) {
                response.set("success", false);
                message.set("message", "The picture was already posted");
            } else if (!CategoryProcessor.getCategoryById(categoryId).isPresent()) {
                response.set("success", false);
                message.set("message", "The category id doesn't exist");
            } else {
                PostProcessor.postPicture(userId, picId, categoryId, keyword);
                response.set("success", false);
                message.set("message", "Successfully posted");
            }
            response.set("response", message);
            HandlerUtils.sendJsonResponse(httpExchange, 200, response);
        } catch (Exception e) {
            HandlerUtils.sendServerErrorResponse(httpExchange, e);
        }
    }
}
