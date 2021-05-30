package edu.fpt.swp391.g2.imageexp.server.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import edu.fpt.swp391.g2.imageexp.utils.WebUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * A simple web handler with support for formatting query parameters
 */
public interface SimpleHttpHandler extends HttpHandler {
    /**
     * Send the "404 Not Found" to the http exchange.
     *
     * @param httpExchange the http exchange
     * @throws IOException if there is an I/O error
     */
    static void sendNotFoundResponse(HttpExchange httpExchange) throws IOException {
        String response = "404 (Not Found)";
        httpExchange.sendResponseHeaders(404, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    @Override
    default void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        if ("GET".equalsIgnoreCase(method)) {
            Map<String, String> params = WebUtils.formatParameters(httpExchange.getRequestURI().getQuery());
            handleGetRequest(httpExchange, params);
        } else if ("POST".equalsIgnoreCase(method)) {
            StringBuilder sb = new StringBuilder();
            InputStream ios = httpExchange.getRequestBody();
            int i;
            while ((i = ios.read()) != -1) {
                sb.append((char) i);
            }
            Map<String, String> params = WebUtils.formatParameters(sb.toString());
            handlePostRequest(httpExchange, params);
        } else {
            handleDefaultRequest(method, httpExchange);
        }
    }

    /**
     * Handle the GET request.
     * Override this method if you want to take care of the GET method.
     *
     * @param httpExchange the http exchange.
     * @param parameters   the query parameters
     * @throws IOException if there is an I/O error
     */
    default void handleGetRequest(HttpExchange httpExchange, Map<String, String> parameters) throws IOException {
        sendNotFoundResponse(httpExchange);
    }

    /**
     * Handle the POST request.
     * Override this method if you want to take care of the POST method.
     *
     * @param httpExchange the http exchange.
     * @param parameters   the query parameters
     * @throws IOException if there is an I/O error
     */
    default void handlePostRequest(HttpExchange httpExchange, Map<String, String> parameters) throws IOException {
        sendNotFoundResponse(httpExchange);
    }

    /**
     * Handle other methods of request (Not GET and POST method).
     * Override this method if you want to take care of other methods.
     *
     * @param method       the request method
     * @param httpExchange the http exchange
     * @throws IOException if there is an I/O error
     */
    default void handleDefaultRequest(String method, HttpExchange httpExchange) throws IOException {
        sendNotFoundResponse(httpExchange);
    }
}
