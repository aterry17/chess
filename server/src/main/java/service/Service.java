package service;

import dataaccess.*;
import model.*;

import java.util.UUID;

public class Service {

    private final MemUserDAO mem;
    public Service(MemUserDAO mem) {
        this.mem = mem;
    }
//    private MemUserDAO mem = new MemUserDAO();

    private static String generateToken() {
        return UUID.randomUUID().toString();
    }

    public RegisterResult register(RegisterRequest request) throws DataAccessException {
        var user = new UserData(request.username(), request.password(), request.email());
        // check to see if username already exists
        if(mem.getUsername(user) != null){
            throw new AlreadyTaken403Exception("");
        }
        mem.createUser(user);
        return new RegisterResult(user.username(), generateToken());
    }

    public LoginResult login(LoginRequest request) throws DataAccessException {
        var user = mem.getUser(request.username());
        if (user == null){
            throw new BadRequest400Exception("");
        }
        else if (!user.password().equals(request.password())){
            throw new Unauthorized401Exception("");
        }
        return new LoginResult(user.username(), generateToken());
    }
}

