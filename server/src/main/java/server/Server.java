package server;

import com.google.gson.Gson;
import dataaccess.*;
import io.javalin.*;
import io.javalin.http.Context;
import service.Service;
import java.util.Map;

public class Server {

    private final Javalin javalin;
    private final Service service;

    public Server() {
        this(new Service(new MemUserDAO(), new MemAuthDAO(), new MemGameDAO()));
    }


    public Server(Service service) {
        this.service = service;

        javalin = Javalin.create(config -> config.staticFiles.add("web"));

        // Register your endpoints and exception handlers here.

        /// register endpoint
        javalin.post("/user", this::register)
                .post("/session", this::login)
                .delete("/session", this::logout)
                .get("/game", this::listGames)
                .post("/game", this::createGame)
                .put("/game", this::joinGame)
                .delete("/db", this::clear)
                .exception(DataAccessException.class, this:: exceptionHandler)
                .error(404, this::notFound);
        ///  exception handlers round 2 :)

        /// exception handlers
        Gson gson = new Gson();
        javalin.exception(BadRequest400Exception.class, (e, context) -> {
            context.status(400).result(gson.toJson(Map.of("message", "Error: bad request" )));
        });
        javalin.exception(Unauthorized401Exception.class, (e, context) -> {
            context.status(401).result(gson.toJson(Map.of("message", "Error: unauthorized")));
        });
        javalin.exception(AlreadyTaken403Exception.class, (e, context) -> {
            context.status(403).result(gson.toJson(Map.of("message", "Error: already taken")));
        });
        javalin.exception(DataAccessException.class, (e, context) -> {
            context.status(500).result();
        });

    }

    private void exceptionHandler(DataAccessException e, Context context){

        var body = new Gson().toJson(Map.of("message", String.format("Error: %s", e.getMessage()), "success", false));
        context.status(500);
        context.json(body);
    }

    private void notFound(Context context){
        String msg = String.format("[%s} %s not found", context.method(), context.path());
        exceptionHandler(new DataAccessException(msg), context);
    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }

    private void register(Context context) throws DataAccessException {
        new Handler().handleRegisterRequest(service, context);
    }

    private void clear(Context context) throws DataAccessException {
        new Handler().handleClearRequest(service, context);
    }

    private void login(Context context) throws DataAccessException{
        new Handler().handleLoginRequest(service, context);
    }

    private void logout(Context context) throws DataAccessException{
        new Handler().handleLogoutRequest(service, context);
    }

    private void listGames(Context context) throws DataAccessException{
        new Handler().handleListGamesRequest(service, context);
    }

    private void createGame(Context context) throws DataAccessException {
        new Handler().handleCreateGameRequest(service, context);
    }

    private void joinGame(Context context) throws DataAccessException{
        new Handler().handleJoinGameRequest(service, context);
    }



}

