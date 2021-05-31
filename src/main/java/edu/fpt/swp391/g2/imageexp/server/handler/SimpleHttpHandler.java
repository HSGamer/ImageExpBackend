package edu.fpt.swp391.g2.imageexp.server.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import edu.fpt.swp391.g2.imageexp.utils.HandlerUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * A simple web handler with support for formatting query parameters
 */
public interface SimpleHttpHandler extends HttpHandler {
    @Override
    default void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        if ("GET".equalsIgnoreCase(method)) {
            String query = httpExchange.getRequestURI().getQuery();
            handleGetRequest(httpExchange, query != null ? query : "");
        } else if ("POST".equalsIgnoreCase(method)) {
            StringBuilder sb = new StringBuilder();
            InputStream ios = httpExchange.getRequestBody();
            int i;
            while ((i = ios.read()) != -1) {
                sb.append((char) i);
            }
            handlePostRequest(httpExchange, sb.toString());
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
    default void handleGetRequest(HttpExchange httpExchange, String parameters) throws IOException {
        HandlerUtils.sendNotFoundResponse(httpExchange);
    }

    /**
     * Handle the POST request.
     * Override this method if you want to take care of the POST method.
     *
     * @param httpExchange the http exchange.
     * @param parameters   the query parameters
     * @throws IOException if there is an I/O error
     */
    default void handlePostRequest(HttpExchange httpExchange, String parameters) throws IOException {
        HandlerUtils.sendNotFoundResponse(httpExchange);
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
        HandlerUtils.sendNotFoundResponse(httpExchange);
    }
}
