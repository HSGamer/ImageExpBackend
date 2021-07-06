package edu.fpt.swp391.g2.imageexp.server.handler;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.WriterConfig;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

/**
 * The default web handler
 */
public class DefaultHandler implements HttpHandler {
    private final String version = getClass().getPackage().getImplementationVersion();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        Headers headers = httpExchange.getResponseHeaders();
        headers.set("Content-Type", "application/json");

        JsonObject object = new JsonObject();
        object.add("version", version);
        JsonObject info = new JsonObject();
        object.add("info", info);
        info.add("address", httpExchange.getRemoteAddress().toString());
        info.add("protocol", httpExchange.getProtocol());
        info.add("method", httpExchange.getRequestMethod());
        JsonObject headersObj = new JsonObject();
        info.add("headers", headersObj);
        httpExchange.getRequestHeaders().forEach((s, l) -> headersObj.add(s, l.toString()));
        byte[] bytes = object.toString(WriterConfig.PRETTY_PRINT).getBytes();

        httpExchange.sendResponseHeaders(200, bytes.length);
        OutputStream outputStream = httpExchange.getResponseBody();
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }
}
