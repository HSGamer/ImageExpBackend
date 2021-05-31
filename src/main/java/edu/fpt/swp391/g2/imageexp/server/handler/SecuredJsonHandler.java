package edu.fpt.swp391.g2.imageexp.server.handler;

import com.eclipsesource.json.*;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import edu.fpt.swp391.g2.imageexp.config.MainConfig;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;

public abstract class SecuredJsonHandler implements SimpleHttpHandler {
    @Override
    public void handlePostRequest(HttpExchange httpExchange, String parameters) throws IOException {
        Headers headers = httpExchange.getRequestHeaders();
        List<String> contentTypes = headers.getOrDefault("Content-Type", Collections.emptyList());
        if (!contentTypes.contains("application/json")) {
            sendServerErrorResponse(httpExchange, new InvalidObjectException("Only Json is allowed"));
            return;
        }
        JsonObject jsonObject;
        try {
            jsonObject = Json.parse(parameters).asObject();
        } catch (Exception e) {
            sendServerErrorResponse(httpExchange, e);
            return;
        }
        String secretKey = jsonObject.getString("secret-key", "");
        JsonValue body = jsonObject.get("body");
        if (!secretKey.equals(MainConfig.SERVER_SECRET_KEY.getValue())) {
            sendNotAuthorizedResponse(httpExchange);
            return;
        }
        if (body == null) {
            sendServerErrorResponse(httpExchange, new IllegalArgumentException("Cannot send empty body"));
            return;
        }
        handleJsonRequest(httpExchange, body);
    }

    public void sendServerErrorResponse(HttpExchange httpExchange, Throwable throwable) throws IOException {
        JsonObject jsonObject = new JsonObject();
        jsonObject.set("message", throwable.getMessage());
        jsonObject.set("error-type", throwable.getClass().getSimpleName());
        JsonArray jsonArray = new JsonArray();
        for (StackTraceElement traceElement : throwable.getStackTrace()) {
            jsonArray.add(traceElement.getClassName() + ": " + traceElement.getLineNumber());
        }
        jsonObject.set("stacktrace", jsonArray);
        byte[] bytes = jsonObject.toString(WriterConfig.PRETTY_PRINT).getBytes();

        httpExchange.sendResponseHeaders(500, bytes.length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(bytes);
        os.flush();
        os.close();
    }

    public void sendNotAuthorizedResponse(HttpExchange httpExchange) throws IOException {
        JsonObject jsonObject = new JsonObject();
        jsonObject.set("message", "Not authorized");
        byte[] bytes = jsonObject.toString(WriterConfig.PRETTY_PRINT).getBytes();

        httpExchange.sendResponseHeaders(401, bytes.length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(bytes);
        os.flush();
        os.close();
    }

    public abstract void handleJsonRequest(HttpExchange httpExchange, JsonValue body) throws IOException;
}
