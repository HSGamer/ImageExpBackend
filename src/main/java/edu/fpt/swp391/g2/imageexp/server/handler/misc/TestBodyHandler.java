package edu.fpt.swp391.g2.imageexp.server.handler.misc;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.WriterConfig;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import edu.fpt.swp391.g2.imageexp.server.handler.SimpleHttpHandler;
import edu.fpt.swp391.g2.imageexp.utils.WebUtils;

import java.io.IOException;
import java.io.OutputStream;

public class TestBodyHandler implements SimpleHttpHandler {
    @Override
    public void handlePostRequest(HttpExchange httpExchange, String parameters) throws IOException {
        Headers headers = httpExchange.getResponseHeaders();
        headers.set("Content-Type", "application/json");

        JsonObject body = new JsonObject();
        WebUtils.formatParameters(parameters).forEach(body::set);
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
