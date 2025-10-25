package service;

import dataaccess.DataAccessException;
import dataaccess.MemUserDAO;
import io.javalin.http.Context;
import model.RegisterRequest;
import model.RegisterResult;
import model.UserData;
import org.eclipse.jetty.server.Authentication;

public class Service {



    /// example
//    public RegisterResult register(RegisterRequest registerRequest){}
//    public LoginResult login(LoginRequest loginRequest){}
//    public


    public RegisterResult register(RegisterRequest request) throws DataAccessException {
        // to-add: check memory for username & throw exception if username already exists

        // assuming that username is free:

        // RegisterRequest has same format as UserData
        MemUserDAO mem = new MemUserDAO();
        var user = new UserData(request.username(), request.password(), request.email());
        mem.createUser(user);
        return new RegisterResult("test_u", "test_a");
    }
}

