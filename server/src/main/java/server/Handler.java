package server;

import com.google.gson.Gson;
import dataaccess.BadRequest400Exception;
import dataaccess.DataAccessException;
import io.javalin.http.Context;
import model.*;
import service.Service;

import java.util.HashSet;
import java.util.Map;

public class Handler {

    /// example from class slides:
//    LoginRequest request = (LoginRequest)gson.fromJson(reqData, LoginRequest.class);
//    LoginService service = new LoginService();
//    LoginResult result = service.login(request);
//    return gson.toJson(result);

    /// example of what a handler needs to do:
    // validate the auth token
    // deserialize JSON request to java request object
    // call service class & pass to it the java request object
    // receive response object from service class
    // serialize java response object to JSON
    // send http response back to client

    //

    public void handleRequest(Service service, Context context) throws DataAccessException {
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
            LogoutRequest request;
            try {
                request = gson.fromJson(context.body(), LogoutRequest.class);
            } catch (RuntimeException e) {
                throw new BadRequest400Exception(""); // request is only going to throw an error if the request is bad -- pretty sure
            }
            EmptyResult logResult = service.logout(request);
            context.result(gson.toJson(logResult));
        }
    }



}
