package edu.fpt.swp391.g2.imageexp.server.handler;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.Setter;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;

public class ChangeableTextHandler implements HttpHandler {
    @Setter
    private static String text = "This is a text";

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        Headers headers = httpExchange.getResponseHeaders();
        headers.set("content-type", "application/json");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("text", text);
        byte[] bytes = jsonObject.toString().getBytes();

        httpExchange.sendResponseHeaders(200, bytes.length);
        OutputStream outputStream = httpExchange.getResponseBody();
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }
}
