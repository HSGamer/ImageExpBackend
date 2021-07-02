package edu.fpt.swp391.g2.imageexp.utils;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

/**
 * The utility class for web handlers
 */
public class HandlerUtils {
    /**
     * Send the Json value to the http exchange.
     *
     * @param httpExchange the http exchange
     * @param statusCode   the status code
     * @param jsonValue    the json value
     * @throws IOException if there is an I/O error
     */
    public static void sendJsonResponse(HttpExchange httpExchange, int statusCode, JsonValue jsonValue) throws IOException {
        Headers headers = httpExchange.getResponseHeaders();
        headers.set("Content-Type", "application/json");
        byte[] bytes = jsonValue.toString().getBytes();
        httpExchange.sendResponseHeaders(statusCode, bytes.length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(bytes);
        os.flush();
        os.close();
    }

    /**
     * Send the "404 Not Found" to the http exchange.
     *
     * @param httpExchange the http exchange
     * @throws IOException if there is an I/O error
     */
    public static void sendNotFoundResponse(HttpExchange httpExchange) throws IOException {
        JsonObject jsonObject = new JsonObject();
        jsonObject.set("message", "Not Found");
        sendJsonResponse(httpExchange, 401, jsonObject);
    }

    /**
     * Send the "500 Internal Server Error" to the http exchange.
     *
     * @param httpExchange the http exchange
     * @param throwable    the throwable
     * @throws IOException if there is an I/O error
     */
    public static void sendServerErrorResponse(HttpExchange httpExchange, Throwable throwable) throws IOException {
        JsonObject jsonObject = new JsonObject();
        jsonObject.set("message", throwable.getMessage());
        jsonObject.set("error-type", throwable.getClass().getSimpleName());
        JsonArray jsonArray = new JsonArray();
        for (StackTraceElement traceElement : throwable.getStackTrace()) {
            jsonArray.add(traceElement.getClassName() + ": " + traceElement.getLineNumber());
        }
        jsonObject.set("stacktrace", jsonArray);
        sendJsonResponse(httpExchange, 500, jsonObject);
    }

    /**
     * Send the "401 Not Authorized" to the http exchange.
     *
     * @param httpExchange the http exchange
     * @throws IOException if there is an I/O error
     */
    public static void sendNotAuthorizedResponse(HttpExchange httpExchange) throws IOException {
        JsonObject jsonObject = new JsonObject();
        jsonObject.set("message", "Not authorized");
        sendJsonResponse(httpExchange, 401, jsonObject);
    }
}
