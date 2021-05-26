package edu.fpt.swp391.g2.imageexp.server.handler;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;

public class DefaultHandler implements HttpHandler {
    private final String version = getClass().getPackage().getImplementationVersion();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        Headers headers = httpExchange.getResponseHeaders();
        headers.set("Content-Type", "application/json");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("version", version);
        JSONObject info = new JSONObject();
        jsonObject.put("info", info);
        info.put("address", httpExchange.getRemoteAddress().toString());
        info.put("protocol", httpExchange.getProtocol());
        info.put("method", httpExchange.getRequestMethod());
        info.put("headers", httpExchange.getRequestHeaders());
        JSONObject user = new JSONObject();
        jsonObject.put("user", user);
        user.put("name", httpExchange.getPrincipal().getName());
        user.put("realm", httpExchange.getPrincipal().getRealm());
        byte[] bytes = jsonObject.toString().getBytes();

        httpExchange.sendResponseHeaders(200, bytes.length);
        OutputStream outputStream = httpExchange.getResponseBody();
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }
}
