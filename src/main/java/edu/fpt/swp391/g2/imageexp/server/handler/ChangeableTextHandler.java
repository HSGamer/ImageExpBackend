package edu.fpt.swp391.g2.imageexp.server.handler;

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
        httpExchange.sendResponseHeaders(200, text.length());
        OutputStream outputStream = httpExchange.getResponseBody();
        outputStream.write(text.getBytes());
        outputStream.flush();
        outputStream.close();
    }
}
