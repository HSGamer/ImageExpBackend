package edu.fpt.swp391.g2.imageexp.server.handler.picture;

import com.eclipsesource.json.JsonArray;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import edu.fpt.swp391.g2.imageexp.processor.GalleryProcessor;
import edu.fpt.swp391.g2.imageexp.utils.HandlerUtils;

import java.io.IOException;

/**
 * Get all pictures
 */
public class GetAllPicturesHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            JsonArray pictures = new JsonArray();
            GalleryProcessor.getAllPictures().forEach(picture -> pictures.add(picture.toJsonObject(false)));
            HandlerUtils.sendJsonResponse(exchange, 200, pictures);
        } catch (Exception e) {
            HandlerUtils.sendServerErrorResponse(exchange, e);
        }
    }
}
