package service;

//import com.google.gson.Gson;
import dataaccess.*;
import io.javalin.http.Context;
import model.*;

//import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class Service {

    private final UserDAO userDatabase;
    private final AuthDAO authDatabase;
    private final GameDAO gameDatabase;


    public Service(UserDAO userDatabase, AuthDAO authDatabase, GameDAO gameDatabase) {
        this.userDatabase = userDatabase;
        this.authDatabase= authDatabase;
        this.gameDatabase = gameDatabase;
    }

    private static String generateToken() {
        return UUID.randomUUID().toString();
    }
    private String generateGameID(){
        // generate an integer 1000-9999
        Random rand = new Random();
        return String.valueOf(rand.nextInt(1000,10000));
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
        if(userDatabase.getUsername(user) != null){
            throw new AlreadyTaken403Exception("");
        }
        userDatabase.createUser(user);
        String authtoken = generateToken();
        authDatabase.insertAuth(authtoken, user.username());
        return new RegisterResult(user.username(), authtoken);
    }

    public LoginResult login(LoginRequest request) throws DataAccessException {
        // request w/o username
        if (request.username() == null){
            throw new BadRequest400Exception("");
        }// request w/o password
        if (request.password() == null){
            throw new BadRequest400Exception("");
        }
        var user = userDatabase.getUser(request.username());
        // user is not in database
        if (user == null){
            throw new Unauthorized401Exception("");
        }// username and password don't match
        if (!userDatabase.correctPassword(user.username(), request.password())){
            throw new Unauthorized401Exception("");
        }
        var authtoken = generateToken();
        // put the AuthData(authToken, username) into the MemAuthDAO
        authDatabase.insertAuth(authtoken, user.username());
        return new LoginResult(user.username(), authtoken);
    }

    public EmptyResult logout(String authtoken) throws DataAccessException {
        // assuming that the handler took care of unauthorized, just remove the AuthData
        authDatabase.deleteAuth(authtoken);
        return new EmptyResult();
    }

    public ListGamesResult listGames() throws DataAccessException {
        return new ListGamesResult(gameDatabase.listGames());
    }

    public CreateGameResult createGame(CreateGameRequest request) throws DataAccessException {
        //game name null
        if (request.gameName() == null){
            throw new BadRequest400Exception(""); // changing this line from 401 to 400
        }
        String gameID = generateGameID();
        // throw data access exception if game name already exists
        gameDatabase.createGame(request.gameName(), gameID);
        return new CreateGameResult(gameID);
    }

    public EmptyResult joinGame(JoinGameRequest request, String authtoken) throws DataAccessException {
        //request w/o ID
        if (request.gameID() == null){
            throw new BadRequest400Exception("gameID was null");
        }
        //request w/o player color
        if (request.playerColor() == null){
            throw new BadRequest400Exception("playerColor was null");
        }

        String username = authDatabase.getUsername(authtoken);
        if (!gameDatabase.validGameID(request.gameID())){
            throw new BadRequest400Exception("");
        }

        if (request.playerColor().equals("WHITE")){
            if (gameDatabase.getGame(request.gameID()).whiteUsername() != null){
                throw new AlreadyTaken403Exception("");
            }
            gameDatabase.updateGame(request.playerColor(), username, request.gameID());
            return new EmptyResult();
        } else if (request.playerColor().equals("BLACK")){
            if (gameDatabase.getGame(request.gameID()).blackUsername() != null){
                throw new AlreadyTaken403Exception("");
            }
            gameDatabase.updateGame(request.playerColor(), username, request.gameID());
            return new EmptyResult();
        } else {
            throw new BadRequest400Exception("");
        }
    }

    public EmptyResult clear() throws DataAccessException{
        gameDatabase.clear();
        authDatabase.clear();
        userDatabase.clear();
        return new EmptyResult();
    }

    /// from the web-api.md in instruction/web-api/
    public boolean authorized(Context context) throws DataAccessException {
        String authToken = context.header("authorization");

        /// new way -- helped a few more tests pass
        // we're still getting 401s thrown more often than they should be
        if (authToken == null){
            throw new BadRequest400Exception("");
        } if (!authDatabase.containsAuth(authToken)){
            throw new Unauthorized401Exception("");
        } return true;
    }



}

