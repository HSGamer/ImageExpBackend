package edu.fpt.swp391.g2.imageexp.server.handler.picture;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.sun.net.httpserver.HttpExchange;
import edu.fpt.swp391.g2.imageexp.entity.Picture;
import edu.fpt.swp391.g2.imageexp.entity.User;
import edu.fpt.swp391.g2.imageexp.processor.GalleryProcessor;
import edu.fpt.swp391.g2.imageexp.processor.UserProcessor;
import edu.fpt.swp391.g2.imageexp.server.handler.SecuredJsonHandler;
import edu.fpt.swp391.g2.imageexp.utils.HandlerUtils;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.util.Optional;

public class DeletePictureHandler extends SecuredJsonHandler {
    @Override
    public void handleJsonRequest(HttpExchange httpExchange, JsonValue body) throws IOException {
        if (!body.isObject()) {
            HandlerUtils.sendServerErrorResponse(httpExchange, new InvalidObjectException("Only Json Object is allowed"));
            return;
        }
        JsonObject jsonObject = body.asObject();
        int picId = jsonObject.getInt("pic_id", -1);
        int userId = jsonObject.getInt("user_id", -1);

        JsonObject response = new JsonObject();
        try {
            JsonObject message = new JsonObject();
            Optional<Picture> optionalPicture = GalleryProcessor.getPictureById(picId);
            Optional<User> optionalUser = UserProcessor.getUserById(userId);
            if (!optionalPicture.isPresent()) {
                response.set("success", false);
                message.set("message", "The picture id doesn't exist");
            } else if (!optionalUser.isPresent()) {
                response.set("success", false);
                message.set("message", "The user id doesn't exist");
            } else {
                Picture picture = optionalPicture.get();
                User user = optionalUser.get();
                if (picture.getUserId() != user.getUserId()) {
                    response.set("success", false);
                    message.set("message", "The user is not the owner of the picture");
                } else {
                    GalleryProcessor.deletePicture(picture.getId());
                    response.set("success", true);
                    message.set("message", "Successfully deleted");
                }
            }
            response.set("response", message);
        } catch (Exception e) {
            HandlerUtils.sendServerErrorResponse(httpExchange, e);
        }
    }
}
