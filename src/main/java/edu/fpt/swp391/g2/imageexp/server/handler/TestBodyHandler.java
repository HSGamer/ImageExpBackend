package edu.fpt.swp391.g2.imageexp.server.handler;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.WriterConfig;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;

public class TestBodyHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        Headers headers = httpExchange.getResponseHeaders();
        headers.set("Content-Type", "application/json");

        JsonArray array = new JsonArray();
        InputStream inputStream = httpExchange.getRequestBody();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String s = reader.readLine();
        while (s != null) {
            array.add(s);
            s = reader.readLine();
        }
        JsonObject object = new JsonObject();
        object.set("body", array);
        byte[] bytes = object.toString(WriterConfig.PRETTY_PRINT).getBytes();

        httpExchange.sendResponseHeaders(200, bytes.length);
        OutputStream outputStream = httpExchange.getResponseBody();
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }
}
