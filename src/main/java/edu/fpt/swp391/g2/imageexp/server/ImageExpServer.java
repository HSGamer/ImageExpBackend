package edu.fpt.swp391.g2.imageexp.server;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import edu.fpt.swp391.g2.imageexp.server.handler.ChangeableTextHandler;
import lombok.Getter;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class ImageExpServer {
    private final List<HttpContext> contexts = new ArrayList<>();
    @Getter
    private HttpServer server;

    public void init() throws IOException {
        server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.setExecutor(Executors.newFixedThreadPool(10));

        registerHandler("/", new ChangeableTextHandler());
    }

    public void enable() {
        server.start();
    }

    public void disable() {
        for (HttpContext context : contexts) {
            server.removeContext(context);
        }
        server.stop(0);
    }

    public HttpContext registerHandler(String path, HttpHandler httpHandler) {
        HttpContext context = server.createContext(path, httpHandler);
        contexts.add(context);
        return context;
    }
}
