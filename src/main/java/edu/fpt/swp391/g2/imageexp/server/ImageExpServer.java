package edu.fpt.swp391.g2.imageexp.server;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import edu.fpt.swp391.g2.imageexp.config.MainConfig;
import edu.fpt.swp391.g2.imageexp.server.handler.DefaultHandler;
import edu.fpt.swp391.g2.imageexp.server.handler.category.AddCategoryHandler;
import edu.fpt.swp391.g2.imageexp.server.handler.category.GetAllCategoriesHandler;
import edu.fpt.swp391.g2.imageexp.server.handler.category.GetCategoryByIdHandler;
import edu.fpt.swp391.g2.imageexp.server.handler.misc.ChangeableTextHandler;
import edu.fpt.swp391.g2.imageexp.server.handler.misc.TestBodyHandler;
import edu.fpt.swp391.g2.imageexp.server.handler.misc.TestEmailHandler;
import edu.fpt.swp391.g2.imageexp.server.handler.picture.*;
import edu.fpt.swp391.g2.imageexp.server.handler.post.*;
import edu.fpt.swp391.g2.imageexp.server.handler.user.*;
import lombok.Getter;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * The HTTP server, with {@link HttpHandler} as web services
 */
public class ImageExpServer {
    private final List<HttpContext> contexts = new ArrayList<>();

    /**
     * The instance of the HTTP server
     */
    @Getter
    private HttpServer server;

    /**
     * Start the server and all web services
     *
     * @throws IOException if the server can not be started
     */
    public void init() throws IOException {
        server = HttpServer.create(new InetSocketAddress(MainConfig.SERVER_PORT.getValue()), 0);
        server.setExecutor(Executors.newCachedThreadPool());

        // Default
        registerHandler("/", new DefaultHandler());

        // Misc
        registerHandler("/changeable", new ChangeableTextHandler());
        registerHandler("/testbody", new TestBodyHandler());
        registerHandler("/testemail", new TestEmailHandler());

        // User
        registerHandler("/loginuser", new LoginUserHandler());
        registerHandler("/getuserbyid", new GetUserByIdHandler());
        registerHandler("/registeruser", new RegisterUserHandler());
        registerHandler("/updateuser", new UpdateUserHandler());
        registerHandler("/changeuserpassword", new ChangeUserPasswordHandler());
        registerHandler("/getuserbyemail", new GetUserByEmailHandler());
        registerHandler("/getstatus", new GetUserStatusHandler());
        registerHandler("/changestatus", new ChangeUserStatusHandler());
        registerHandler("/setverifystate", new SetUserVerifyStateHandler());
        registerHandler("/getverifystate", new GetVerifyStateFromEmailHandler());
        registerHandler("/checkverifycode", new CheckUserVerifyCodeHandler());
        registerHandler("/sendverifycode", new SendVerifyCodeHandler());

        // Category
        registerHandler("/addcategory", new AddCategoryHandler());
        registerHandler("/getallcategories", new GetAllCategoriesHandler());
        registerHandler("/getcategorybyid", new GetCategoryByIdHandler());

        // Post
        registerHandler("/getallposts", new GetAllPostsHandler());
        registerHandler("/getpostsbyuserid", new GetPostsByUserIdHandler());
        registerHandler("/getpostsbycategoryid", new GetPostsByCategoryIdHandler());
        registerHandler("/getpostbyid", new GetPostByIdHandler());
        registerHandler("/addpost", new AddPostHandler());
        registerHandler("/updatepost", new UpdatePostHandler());
        registerHandler("/deletepostforuser", new DeletePostForUserHandler());
        registerHandler("/getpostbypicid", new GetPostByPicIdHandler());

        //Picture
        registerHandler("/getallpictures", new GetAllPicturesHandler());
        registerHandler("/getpicturesbyuserid", new GetPicturesByUserIdHandler());
        registerHandler("/getpicturebyid", new GetPictureByIdHandler());
        registerHandler("/addpicture", new AddPictureHandler());
        registerHandler("/addmorepictures", new AddMorePicturesHandler());
        registerHandler("/deletepicture", new DeletePictureHandler());
    }

    /**
     * Enable the server
     */
    public void enable() {
        server.start();
    }

    /**
     * Clear the services and stop the server
     */
    public void disable() {
        for (HttpContext context : contexts) {
            server.removeContext(context);
        }
        contexts.clear();
        server.stop(0);
    }

    /**
     * Register the web handlers
     *
     * @param path        the web path
     * @param httpHandler the web handler
     * @return the instance of the services
     */
    public HttpContext registerHandler(String path, HttpHandler httpHandler) {
        HttpContext context = server.createContext(path);
        context.setHandler(httpExchange -> {
            Headers headers = httpExchange.getResponseHeaders();
            headers.add("Access-Control-Allow-Origin", "*");
            if (httpExchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
                headers.add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
                headers.add("Access-Control-Allow-Headers", "Content-Type");
                httpExchange.sendResponseHeaders(204, -1);
                return;
            }
            httpHandler.handle(httpExchange);
        });
        contexts.add(context);
        return context;
    }
}