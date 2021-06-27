package edu.fpt.swp391.g2.imageexp.server.handler;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import edu.fpt.swp391.g2.imageexp.config.MainConfig;
import edu.fpt.swp391.g2.imageexp.utils.HandlerUtils;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.util.Collections;
import java.util.List;

public abstract class SecuredJsonHandler implements SimpleHttpHandler {
    @Override
    public void handlePostRequest(HttpExchange httpExchange, String parameters) throws IOException {
        Headers headers = httpExchange.getRequestHeaders();
        List<String> contentTypes = headers.getOrDefault("Content-Type", Collections.emptyList());
        if (contentTypes.stream().noneMatch(s -> s.contains("application/json"))) {
            HandlerUtils.sendServerErrorResponse(httpExchange, new InvalidObjectException("Only Json is allowed"));
            return;
        }
        JsonObject jsonObject;
        try {
            jsonObject = Json.parse(parameters).asObject();
        } catch (Exception e) {
            HandlerUtils.sendServerErrorResponse(httpExchange, e);
            return;
        }
        String secretKey = jsonObject.getString("secret-key", "");
        JsonValue body = jsonObject.get("body");
        if (!MainConfig.SERVER_SECRET_KEY.getValue().equals(secretKey)) {
            HandlerUtils.sendNotAuthorizedResponse(httpExchange);
            return;
        }
        if (body == null) {
            HandlerUtils.sendServerErrorResponse(httpExchange, new IllegalArgumentException("Cannot send empty body"));
            return;
        }
        handleJsonRequest(httpExchange, body);
    }

    public abstract void handleJsonRequest(HttpExchange httpExchange, JsonValue body) throws IOException;
}
