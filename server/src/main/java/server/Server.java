package server;

import dataaccess.DataAccessException;
import io.javalin.*;
import io.javalin.http.Context;

import java.util.UUID;

public class Server {

    private final Javalin javalin;

    public Server() {
        javalin = Javalin.create(config -> config.staticFiles.add("web"));

        // Register your endpoints and exception handlers here.

        /// Register
        javalin.post("/user", this::register);
    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }

    // method to generate authToken
    private static String generateToken() {
        return UUID.randomUUID().toString();
    }

    private void register(Context context) throws DataAccessException {
        new Handler().handleRequest(context);
    }
}

