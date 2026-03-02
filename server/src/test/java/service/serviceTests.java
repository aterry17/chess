package service;

import dataaccess.*;
import model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class serviceTests {

    @Test
    public void registerPositiveTest(){
        Service service = new Service(new MemUserDAO(), new MemAuthDAO(), new MemGameDAO());
        // check to see that the register result properly sent with the correct username
        try {
            var result = service.register(new RegisterRequest("user1", "pass1", "email1"));
//            assertEquals(result.username(), "user1"); -- apparently incorrect parameter order
            assertEquals("user1", result.username());

        } catch (DataAccessException e){
            fail("service.register threw an unexpected DataAccessException");
        }
    }

    @Test
    public void registerNegativeTest(){
        Service service = new Service(new MemUserDAO(), new MemAuthDAO(), new MemGameDAO());
        try {
            service.register(new RegisterRequest("user1", "pass1", "email1")); // register the user the first time
            assertThrows(AlreadyTaken403Exception.class, () -> {
                service.register(new RegisterRequest("user1", "pass1", "email1"));
            });
        } catch (DataAccessException e){
            fail("service.register threw an unexpected DataAccessException");
        }
    }

    @Test
    public void loginPositiveTest(){
        Service service = new Service(new MemUserDAO(), new MemAuthDAO(), new MemGameDAO());
        try {
            service.register(new RegisterRequest("user1", "pass1", "email1"));
            LoginResult result = service.login(new LoginRequest("user1", "pass1"));
            assertEquals("user1", result.username());
        } catch (DataAccessException e){
            fail("service.register threw an unexpected DataAccessException");
        }
    }

    @Test
    public void loginNegativeTest(){
        Service service = new Service(new MemUserDAO(), new MemAuthDAO(), new MemGameDAO());
        try {
            service.register(new RegisterRequest("user1", "pass1", "email1"));
            assertThrows(Unauthorized401Exception.class, () -> {
                    service.login(new LoginRequest("user1", "pass_wrong"));
        });
        } catch (DataAccessException e){
            fail("service.register threw an unexpected DataAccessException");
        }
    }

    @Test
    public void logoutPositiveTest(){
        Service service = new Service(new MemUserDAO(), new MemAuthDAO(), new MemGameDAO());
        try {
            service.register(new RegisterRequest("user1", "pass1", "email1"));
            var loginresult = service.login(new LoginRequest("user1", "pass1"));
            String authtoken = loginresult.authToken();
            EmptyResult result =  service.logout(authtoken);
            assertEquals(new EmptyResult(), result);
        } catch (DataAccessException e){
            fail("service.register threw an unexpected DataAccessException");
        }
    }


    /// FIX THIS
    @Test
    public void logoutNegativeTest(){
        Service service = new Service(new MemUserDAO(), new MemAuthDAO(), new MemGameDAO());
        UserData user = new UserData("user1", "pass1", "email1");
        try {
            service.register(new RegisterRequest("user1", "pass1", "email1"));
            service.login(new LoginRequest("user1", "pass1"));
//
//            a new idea:
//            service.register(new RegisterRequest(user.username(), user.password(), user.email()));
//            service.login(new LoginRequest(user.username(), user.password()));
//            String userauthtoken =

//            assertThrows(Unauthorized401Exception.class, () -> {
//                service.login(new LoginRequest("user1", "pass_wrong"));

            assertThrows(Unauthorized401Exception.class, () -> {
                service.logout("bad"); // need the authtoken
            });
        } catch (DataAccessException e){
            fail("service.register threw an unexpected DataAccessException");
        }
    }

    @Test
    public void createGamePositiveTest(){
        Service service = new Service(new MemUserDAO(), new MemAuthDAO(), new MemGameDAO());
        try {
            service.register(new RegisterRequest("user1", "pass1", "email1"));
            service.login(new LoginRequest("user1", "pass1"));
            CreateGameResult result = service.createGame(new CreateGameRequest("game9000"));

            assertNotNull(result.gameID(), "we couldn't find the game");
//            System.out.println(result.gameID()); //put this in to check--there is a valid gammeID
            assertNotNull(service.listGames(), "there isn't a list");
//            System.out.println(service.listGames()); This is what the print output looks like: ListGamesResult[chessGamesList=[GameData[gameID=3169, whiteUsername=null, blackUsername=null, gameName=game9000]]]
        } catch (DataAccessException e){
            fail("service.register threw an unexpected DataAccessException");
        }
    }

    @Test
    public void createGameNegativeTest(){

    }
    @Test
    public void listGamesPositiveTest(){

    }

    @Test
    public void listGamesNegativeTest(){

    }

    @Test
    public void joinGamePositiveTest(){

    }

    @Test
    public void joinGameNegativeTest(){

    }

    @Test
    public void clearTest(){

    }


}
