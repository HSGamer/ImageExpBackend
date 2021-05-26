package edu.fpt.swp391.g2.imageexp.server.handler;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.WriterConfig;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.Setter;

import java.io.IOException;
import java.io.OutputStream;

public class ChangeableTextHandler implements HttpHandler {
    @Setter
    private static String text = "This is a text";

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        Headers headers = httpExchange.getResponseHeaders();
        headers.set("Content-Type", "application/json");

        JsonObject jsonObject = new JsonObject();
        jsonObject.add("text", text);
        String string = jsonObject.toString(WriterConfig.PRETTY_PRINT);

        httpExchange.sendResponseHeaders(200, string.length());
        OutputStream outputStream = httpExchange.getResponseBody();
        outputStream.write(string.getBytes());
        outputStream.flush();
        outputStream.close();
    }
}
