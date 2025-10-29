package server;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import io.javalin.http.Context;
import model.RegisterRequest;
import model.RegisterResult;
import service.Service;

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
        RegisterRequest request = gson.fromJson(context.body(), RegisterRequest.class);
        RegisterResult regResult = service.register(request);
        context.result(gson.toJson(regResult));
    }

}
