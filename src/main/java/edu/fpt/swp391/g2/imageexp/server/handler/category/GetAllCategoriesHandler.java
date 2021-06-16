package edu.fpt.swp391.g2.imageexp.server.handler.category;

import com.eclipsesource.json.JsonArray;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import edu.fpt.swp391.g2.imageexp.processor.CategoryProcessor;
import edu.fpt.swp391.g2.imageexp.utils.HandlerUtils;

import java.io.IOException;

public class GetAllCategoriesHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            JsonArray categories = new JsonArray();
            CategoryProcessor.getAllCategories().forEach(category -> categories.add(category.toJsonObject()));
            HandlerUtils.sendJsonResponse(exchange, 200, categories);
        } catch (Exception e) {
            HandlerUtils.sendServerErrorResponse(exchange, e);
        }
    }
}
