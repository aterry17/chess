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
        var user = new UserData(request.username(), request.password(), request.email());
        // check to see if username already exists
        if(memUser.getUsername(user) != null){
            throw new AlreadyTaken403Exception("");
        }
        memUser.createUser(user);
        return new RegisterResult(user.username(), generateToken());
    }

    public LoginResult login(LoginRequest request) throws DataAccessException {
        var user = memUser.getUser(request.username());
        if (user == null){
            throw new BadRequest400Exception("");
        }
        else if (!user.password().equals(request.password())){
            throw new Unauthorized401Exception("");
        }
        var authtoken = generateToken();
        // put the AuthData(authtoken, username) into the MemAuthDAO
        memAuth.insertAuth(authtoken, user.username());
        return new LoginResult(user.username(), authtoken);
    }

    public EmptyResult logout(LogoutRequest request) throws DataAccessException {
        // assuming that the handler took care of unauthorized, just remove the AuthData
        memAuth.deleteAuth(request.authtoken());
        return new EmptyResult();
    }

    /// from the web-api.md in instruction/web-api/
    public boolean authorized(Context context) {
        String authtoken = context.header("authorization");
        if(!memAuth.containsAuth(authtoken)){
            context.contentType("application/json");
            context.status(401);
            context.result(new Gson().toJson(Map.of("msg", "invalid authorization")));
            return false;
        }
        return true;
    }



}

