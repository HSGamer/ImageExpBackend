package edu.fpt.swp391.g2.imageexp.server.handler.post;

import com.eclipsesource.json.JsonArray;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import edu.fpt.swp391.g2.imageexp.processor.PostProcessor;
import edu.fpt.swp391.g2.imageexp.utils.HandlerUtils;

import java.io.IOException;

/**
 * Get all posts
 */
public class GetAllPostsHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            JsonArray posts = new JsonArray();
            PostProcessor.getAllPosts().forEach(post -> posts.add(post.toJsonObject()));
            HandlerUtils.sendJsonResponse(exchange, 200, posts);
        } catch (Exception e) {
            HandlerUtils.sendServerErrorResponse(exchange, e);
        }
    }
}
