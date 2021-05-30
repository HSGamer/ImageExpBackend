package edu.fpt.swp391.g2.imageexp.server.handler;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.WriterConfig;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class TestBodyHandler implements SimpleHttpHandler {
    @Override
    public void handlePostRequest(HttpExchange httpExchange, Map<String, String> parameters) throws IOException {
        Headers headers = httpExchange.getResponseHeaders();
        headers.set("Content-Type", "application/json");

        JsonObject body = new JsonObject();
        parameters.forEach(body::set);
        JsonObject object = new JsonObject();
        object.set("body", body);
        byte[] bytes = object.toString(WriterConfig.PRETTY_PRINT).getBytes();

        httpExchange.sendResponseHeaders(200, bytes.length);
        OutputStream outputStream = httpExchange.getResponseBody();
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }
}
