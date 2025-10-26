package server;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import io.javalin.*;
import io.javalin.http.Context;

import java.util.Map;
import java.util.UUID;

public class Server {

    private final Javalin javalin;

    public Server() {
        javalin = Javalin.create(config -> config.staticFiles.add("web"));

        // Register your endpoints and exception handlers here.

        /// register endpoint
        javalin.post("/user", this::register);

        /// exception handlers
        Gson gson = new Gson();
        javalin.exception(DataAccessException.class, (e, context) -> {
            context.status(400).result(gson.toJson(Map.of("message", "Error: bad request" )));
        });
        javalin.exception(DataAccessException.class, (e, context) -> {
            context.status(401).result(gson.toJson(Map.of("message", "Error: unauthorized")));
        });
        javalin.exception(DataAccessException.class, (e, context) -> {
            context.status(403).result(gson.toJson(Map.of("message", "Error: already taken")));
        });
        javalin.exception(DataAccessException.class, (e, context) -> {
            context.status(500).result();
        });

    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }

    // method to generate authToken
//    private static String generateToken() {
//        return UUID.randomUUID().toString();
//    }

    private void register(Context context) throws DataAccessException {
        new Handler().handleRequest(context);
    }
}

