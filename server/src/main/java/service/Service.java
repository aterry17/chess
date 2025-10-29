package service;

import dataaccess.AlreadyTaken403Exception;
import dataaccess.DataAccessException;
import dataaccess.MemUserDAO;
import io.javalin.http.Context;
import model.RegisterRequest;
import model.RegisterResult;
import model.UserData;

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


        // assuming that username is free:
        // RegisterRequest has same format as UserData
//        MemUserDAO mem = new MemUserDAO(); // putting this up top

        var user = new UserData(request.username(), request.password(), request.email());
        // check to see if username already exists
        if(mem.getUser(user) != null){
            throw new AlreadyTaken403Exception("Username " + user.username() + " already exists");
        }

        mem.createUser(user);
        return new RegisterResult(user.username(), generateToken());
    }
}

