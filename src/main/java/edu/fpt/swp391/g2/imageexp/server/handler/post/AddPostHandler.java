package edu.fpt.swp391.g2.imageexp.server.handler.post;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.sun.net.httpserver.HttpExchange;
import edu.fpt.swp391.g2.imageexp.processor.CategoryProcessor;
import edu.fpt.swp391.g2.imageexp.processor.PostProcessor;
import edu.fpt.swp391.g2.imageexp.processor.UserProcessor;
import edu.fpt.swp391.g2.imageexp.server.handler.SecuredJsonHandler;
import edu.fpt.swp391.g2.imageexp.utils.HandlerUtils;
import edu.fpt.swp391.g2.imageexp.utils.Utils;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.util.List;
import java.util.Optional;

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
        List<Integer> categoryIds = Utils.getIntListFromJsonValue(Optional.ofNullable(jsonObject.get("categoryId")).orElse(new JsonArray()));
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
            } else if (!CategoryProcessor.checkAllCategoriesExists(categoryIds)) {
                response.set("success", false);
                message.set("message", "The category id doesn't exist");
            } else {
                PostProcessor.postPicture(userId, picId, categoryIds, keyword);
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
