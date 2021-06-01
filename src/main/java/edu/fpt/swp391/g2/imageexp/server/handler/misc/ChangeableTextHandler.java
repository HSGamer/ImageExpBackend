package edu.fpt.swp391.g2.imageexp.server.handler.misc;

import com.eclipsesource.json.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import edu.fpt.swp391.g2.imageexp.utils.HandlerUtils;
import lombok.Setter;

import java.io.IOException;

public class ChangeableTextHandler implements HttpHandler {
    @Setter
    private static String text = "This is a text";

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("text", text);
        HandlerUtils.sendJsonResponse(httpExchange, 200, jsonObject);
    }
}
