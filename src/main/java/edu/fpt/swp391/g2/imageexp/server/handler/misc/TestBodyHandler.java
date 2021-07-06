package edu.fpt.swp391.g2.imageexp.server.handler.misc;

import com.eclipsesource.json.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import edu.fpt.swp391.g2.imageexp.server.handler.SimpleHttpHandler;
import edu.fpt.swp391.g2.imageexp.utils.HandlerUtils;
import edu.fpt.swp391.g2.imageexp.utils.WebUtils;

import java.io.IOException;

/**
 * A test handler for testing the request body
 */
public class TestBodyHandler implements SimpleHttpHandler {
    @Override
    public void handlePostRequest(HttpExchange httpExchange, String parameters) throws IOException {
        JsonObject body = new JsonObject();
        WebUtils.formatParameters(parameters).forEach(body::set);
        JsonObject object = new JsonObject();
        object.set("body", body);
        HandlerUtils.sendJsonResponse(httpExchange, 200, body);
    }
}
