package edu.fpt.swp391.g2.imageexp.server.handler.picture;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.sun.net.httpserver.HttpExchange;
import edu.fpt.swp391.g2.imageexp.processor.GalleryProcessor;
import edu.fpt.swp391.g2.imageexp.processor.UserProcessor;
import edu.fpt.swp391.g2.imageexp.server.handler.SecuredJsonHandler;
import edu.fpt.swp391.g2.imageexp.utils.HandlerUtils;

import java.io.IOException;
import java.io.InvalidObjectException;

/**
 * Add a picture
 */
public class AddPictureHandler extends SecuredJsonHandler {
    @Override
    public void handleJsonRequest(HttpExchange httpExchange, JsonValue body) throws IOException {
        if (!body.isObject()) {
            HandlerUtils.sendServerErrorResponse(httpExchange, new InvalidObjectException("Only Json Object is allowed"));
            return;
        }
        JsonObject jsonObject = body.asObject();
        int userId = jsonObject.getInt("userId", -1);
        String picture = jsonObject.getString("picture", "");

        JsonObject response = new JsonObject();
        try {
            JsonObject message = new JsonObject();
            if (!UserProcessor.getUserById(userId).isPresent()) {
                response.set("success", false);
                message.set("message", "The user id doesn't exist");
            } else {
                int picId = GalleryProcessor.addPicture(userId, picture);
                response.set("success", true);
                message.set("picId", picId);
            }
            response.set("response", message);
            HandlerUtils.sendJsonResponse(httpExchange, 200, response);
        } catch (Exception e) {
            HandlerUtils.sendServerErrorResponse(httpExchange, e);
        }
    }
}
