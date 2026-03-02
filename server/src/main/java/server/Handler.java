package server;

import com.google.gson.Gson;
import dataaccess.BadRequest400Exception;
import dataaccess.DataAccessException;
import io.javalin.http.Context;
import model.*;
import service.Service;


public class Handler {

    public void handleRegisterRequest(Service service, Context context) throws DataAccessException {
        Gson gson = new Gson();
        RegisterRequest request;
        try {
            request = gson.fromJson(context.body(), RegisterRequest.class);
        } catch (RuntimeException e){
            throw new BadRequest400Exception(""); // request is only going to throw an error if the request is bad -- pretty sure
        }
        RegisterResult regResult = service.register(request);
        context.result(gson.toJson(regResult));
    }

    public void handleLoginRequest(Service service, Context context) throws DataAccessException{
        Gson gson = new Gson();
        LoginRequest request;
        try {
            request = gson.fromJson(context.body(), LoginRequest.class);
        } catch (RuntimeException e){
            throw new BadRequest400Exception(""); // request is only going to throw an error if the request is bad -- pretty sure
        }
        LoginResult logResult = service.login(request);
        context.result(gson.toJson(logResult));
    }

    public void handleLogoutRequest(Service service, Context context) throws DataAccessException{
        if (service.authorized(context)) {
            Gson gson = new Gson();
            String authtoken;
            try {
                authtoken = context.header("Authorization");
            } catch (RuntimeException e) {
                throw new BadRequest400Exception(""); // request is only going to throw an error if the request is bad -- pretty sure
            }
            EmptyResult logResult = service.logout(authtoken);
            context.result(gson.toJson(logResult));
        }
    }

    public void handleListGamesRequest(Service service, Context context) throws DataAccessException {
        if (service.authorized(context)) {
            Gson gson = new Gson();
            ListGamesResult listResult = service.listGames();
            context.result(gson.toJson(listResult));
        }
    }

    public void handleCreateGameRequest(Service service, Context context) throws DataAccessException{
        if (service.authorized(context)) {
            Gson gson = new Gson();
            CreateGameRequest request;
            try {
                request = gson.fromJson(context.body(), CreateGameRequest.class);
            } catch (RuntimeException e){
                throw new BadRequest400Exception(""); // request is only going to throw an error if the request is bad -- pretty sure
            }
            CreateGameResult createResult = service.createGame(request);
            context.result(gson.toJson(createResult));
        }
    }

    public void handleJoinGameRequest(Service service, Context context) throws DataAccessException{
        if (service.authorized(context)) {
            Gson gson = new Gson();
            JoinGameRequest request;
            String authtoken;
            try{
                authtoken = context.header("authorization");
                request = gson.fromJson(context.body(), JoinGameRequest.class);
            } catch (RuntimeException e) {
                throw new BadRequest400Exception("");
            }
            EmptyResult joinResult = service.joinGame(request, authtoken);
            context.result(gson.toJson(joinResult));
        }
    }

    public void handleClearRequest(Service service, Context context){
            Gson gson = new Gson();
            EmptyResult clearResult = service.clear();
            context.result(gson.toJson(clearResult));
    }
}
