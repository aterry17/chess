package server;

public class Handler {

    /// example from class slides:
//    LoginRequest request = (LoginRequest)gson.fromJson(reqData, LoginRequest.class);
//    LoginService service = new LoginService();
//    LoginResult result = service.login(request);
//    return gson.toJson(result);
//


    /// example of what a handler needs to do:
    // validate the auth token
    // deserialize JSON request to java request object
    // call service class & pass to it the java request object
    // receive response object from service class
    // serialize java response object to JSON
    // send http response back to client


}
