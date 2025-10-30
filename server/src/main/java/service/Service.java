package service;

import dataaccess.*;
import model.*;

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
        return new LoginResult(user.username(), generateToken());
    }

    public EmptyResult logout(LogoutRequest request) throws DataAccessException {

    }

}

