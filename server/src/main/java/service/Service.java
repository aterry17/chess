package service;

import model.RegisterRequest;
import model.RegisterResult;

public class Service {



    /// example
//    public RegisterResult register(RegisterRequest registerRequest){}
//    public LoginResult login(LoginRequest loginRequest){}
//    public


    public RegisterResult register(RegisterRequest request){

        return new RegisterResult("test_u", "test_a");
    }
}
