package edu.fpt.swp391.g2.imageexp.server.handler.picture;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.sun.net.httpserver.HttpExchange;
import edu.fpt.swp391.g2.imageexp.entity.Picture;
import edu.fpt.swp391.g2.imageexp.processor.GalleryProcessor;
import edu.fpt.swp391.g2.imageexp.server.handler.SecuredJsonHandler;
import edu.fpt.swp391.g2.imageexp.utils.HandlerUtils;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.util.Optional;

public class GetPictureByIdHandler extends SecuredJsonHandler {
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
            Optional<Picture> optionalPicture = GalleryProcessor.getPictureById(id);
            if (optionalPicture.isPresent()) {
                response.set("success", true);
                response.set("response", optionalPicture.get().toJsonObject());
            } else {
                response.set("success", false);
                JsonObject message = new JsonObject();
                message.set("message", "The picture id doesn't exist");
                response.set("response", message);
            }
            HandlerUtils.sendJsonResponse(httpExchange, 200, response);
        } catch (Exception e) {
            HandlerUtils.sendServerErrorResponse(httpExchange, e);
        }
    }
}