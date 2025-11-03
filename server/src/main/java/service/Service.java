package service;

import com.google.gson.Gson;
import dataaccess.*;
import io.javalin.http.Context;
import model.*;

import java.util.Map;
import java.util.UUID;

public class Service {

    private final MemUserDAO memUser;
    private final MemAuthDAO memAuth;
    private final MemGameDAO memGame;
    public Service(MemUserDAO memUser, MemAuthDAO memAuth, MemGameDAO memGame) {
        this.memUser = memUser;
        this.memAuth = memAuth;
        this.memGame = memGame;
    }
//    private MemUserDAO mem = new MemUserDAO();

    private static String generateToken() {
        return UUID.randomUUID().toString();
    }

    public RegisterResult register(RegisterRequest request) throws DataAccessException {
        //username is null
        if (request.username() == null){
            throw new BadRequest400Exception("");
        }//password is null
        if (request.password() == null){
            throw new BadRequest400Exception("");
        }//email is null
        if (request.email() == null){
            throw new BadRequest400Exception("");
        }
        var user = new UserData(request.username(), request.password(), request.email());
        // check to see if username already exists
        if(memUser.getUsername(user) != null){
            throw new AlreadyTaken403Exception("");
        }
        memUser.createUser(user);
        return new RegisterResult(user.username(), generateToken());
    }

    public LoginResult login(LoginRequest request) throws DataAccessException {
        // request w/o username
        if (request.username() == null){
            throw new BadRequest400Exception("");
        }// request w/o password
        if (request.password() == null){
            throw new BadRequest400Exception("");
        }
        var user = memUser.getUser(request.username());
        // user is not in database
        if (user == null){
            throw new Unauthorized401Exception("");
        }// username and password don't match
        if (!user.password().equals(request.password())){
            throw new Unauthorized401Exception("");
        }
        var authtoken = generateToken();
        // put the AuthData(authToken, username) into the MemAuthDAO
        memAuth.insertAuth(authtoken, user.username());
        return new LoginResult(user.username(), authtoken);
    }

    public EmptyResult logout(String authtoken) throws DataAccessException {
        // assuming that the handler took care of unauthorized, just remove the AuthData
        memAuth.deleteAuth(authtoken);
        return new EmptyResult();
    }

    public ListGamesResult listGames() throws DataAccessException {
        return new ListGamesResult(memGame.listGames());
    }

    public CreateGameResult createGame(CreateGameRequest request) throws DataAccessException {
        //game name null
        if (request.gameName() == null){
            throw new BadRequest400Exception("");
        }
        String ID = memGame.generateGameID();
        // throw data access exception if game name already exists
        memGame.createGame(request.gameName(), ID);
        return new CreateGameResult(ID);
    }

    public EmptyResult joinGame(JoinGameRequest request, String authtoken) throws DataAccessException {
        //request w/o ID
        if (request.gameID() == null){
            throw new BadRequest400Exception("");
        }
        String username = memAuth.getUsername(authtoken);
        if (!memGame.validGameID(request.gameID())){
            throw new BadRequest400Exception("");
        }

        if (request.playerColor().equals("WHITE")){
            if (memGame.getGame(request.gameID()).whiteUsername() != null){
                throw new AlreadyTaken403Exception("");
            }
            memGame.updateGame(request.playerColor(), username, request.gameID());
            return new EmptyResult();
        } else if (request.playerColor().equals("BLACK")){
            if (memGame.getGame(request.gameID()).blackUsername() != null){
                throw new AlreadyTaken403Exception("");
            }
            memGame.updateGame(request.playerColor(), username, request.gameID());
            return new EmptyResult();
        } else {
            throw new BadRequest400Exception("");
        }
    }

    public EmptyResult clear(){
        memGame.clear();
        memAuth.clear();
        memUser.clear();
        return new EmptyResult();
    }

    /// from the web-api.md in instruction/web-api/
    public boolean authorized(Context context) throws DataAccessException {
        String authToken = context.header("authorization");
        /// OG way
//        if(!memAuth.containsAuth(authToken)){
//            context.contentType("application/json");
//            context.status(401);
//            context.result(new Gson().toJson(Map.of("msg", "invalid authorization")));
//            return false;
//        }
//        return true;

        /// new way -- helped a few more tests pass
        // we're still getting 401s thrown more often than they should be
        if (authToken == null){
            throw new BadRequest400Exception("");
        } if (!memAuth.containsAuth(authToken)){
            throw new Unauthorized401Exception("");
        } return true;
    }



}

