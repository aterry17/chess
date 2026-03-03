package service;

import dataaccess.*;
import model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ServiceTests {

    @Test
    public void registerPositiveTest(){
        Service service = new Service(new MemUserDAO(), new MemAuthDAO(), new MemGameDAO());
        // check to see that the register result properly sent with the correct username
        try {
            var result = service.register(new RegisterRequest("user1", "pass1", "email1"));
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
            fail("threw an unexpected DataAccessException");
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
            fail("threw an unexpected DataAccessException");
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
            fail("threw an unexpected DataAccessException");
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
            assertThrows(Unauthorized401Exception.class, () -> {
                service.logout("bad"); // need the authtoken
            });
        } catch (DataAccessException e){
            fail("threw an unexpected DataAccessException");
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
            assertNotNull(service.listGames(), "there isn't a list");
        } catch (DataAccessException e){
            fail("threw an unexpected DataAccessException");
        }
    }

    @Test
    public void createGameNegativeTest(){
// test that gameName = null throws a BadRequest Exception
        Service service = new Service(new MemUserDAO(), new MemAuthDAO(), new MemGameDAO());
        try {
            service.register(new RegisterRequest("user1", "pass1", "email1"));
            service.login(new LoginRequest("user1", "pass1"));
            assertThrows(BadRequest400Exception.class, () -> {
                service.createGame(new CreateGameRequest(null)); // no game name
            });
        } catch (DataAccessException e){
            fail("threw an unexpected DataAccessException");
        }
    }
    @Test
    public void listGamesPositiveTest(){
        Service service = new Service(new MemUserDAO(), new MemAuthDAO(), new MemGameDAO());
        try {
            service.register(new RegisterRequest("user1", "pass1", "email1"));
            service.createGame(new CreateGameRequest("game1"));
            service.createGame(new CreateGameRequest("game2"));
            service.createGame(new CreateGameRequest("game3"));
            service.createGame(new CreateGameRequest("game4"));
            assertNotNull(service.listGames(), "there is no list");
        } catch (DataAccessException e){
            fail("threw an unexpected DataAccessException");
        }
    }

    @Test
    public void listGamesNegativeTest(){
        // list should be empty
        Service service = new Service(new MemUserDAO(), new MemAuthDAO(), new MemGameDAO());
        try {
            RegisterResult res1 = service.register(new RegisterRequest("user1", "pass1", "email1"));
            ListGamesResult res2 = service.listGames();
            assertEquals(res2.games().size(), 0, "list should have been empty but was not");
        } catch (DataAccessException e){
            fail("threw an unexpected DataAccessException");
        }
    }

    @Test
    public void joinGamePositiveTest(){
        Service service = new Service(new MemUserDAO(), new MemAuthDAO(), new MemGameDAO());
        try {
            service.register(new RegisterRequest("user1", "pass1", "email1"));
            LoginResult res1 = service.login(new LoginRequest("user1", "pass1"));
            String authtoken = res1.authToken();
            String gameID = service.createGame(new CreateGameRequest("game9000")).gameID();
            EmptyResult res2 = service.joinGame(new JoinGameRequest("WHITE", gameID), authtoken);
            // should get a clean empty result
            assertEquals(new EmptyResult(), res2);
            // there should only be one game
            assertEquals(1, service.listGames().games().size());
            String gameID2 = service.createGame(new CreateGameRequest("game9002")).gameID();
            service.joinGame(new JoinGameRequest("WHITE", gameID2), authtoken);
            //there should be two games now
            assertEquals(2, service.listGames().games().size());
        } catch (DataAccessException e){
            fail("threw an unexpected DataAccessException");
        }

    }

    @Test
    public void joinGameNegativeTest(){
        Service service = new Service(new MemUserDAO(), new MemAuthDAO(), new MemGameDAO());
        try {
            service.register(new RegisterRequest("user1", "pass1", "email1"));
            LoginResult res1 = service.login(new LoginRequest("user1", "pass1"));
            String authtoken = res1.authToken();
            String gameID = service.createGame(new CreateGameRequest("game9000")).gameID();
            // no player color
            assertThrows(BadRequest400Exception.class, () -> {
                service.joinGame(new JoinGameRequest(null, gameID), authtoken);
            });
            // no gameID
            assertThrows(BadRequest400Exception.class, () -> {
                service.joinGame(new JoinGameRequest("BLACK", null), authtoken);
            });

        } catch (DataAccessException e){
            fail("threw an unexpected DataAccessException");
        }
    }

    @Test
    public void clearTest(){
        Service service = new Service(new MemUserDAO(), new MemAuthDAO(), new MemGameDAO());
        try {
            service.register(new RegisterRequest("user1", "pass1", "email1"));
            service.register(new RegisterRequest("user2", "pass2", "email2"));
            service.register(new RegisterRequest("user3", "pass3", "email3"));
            service.createGame(new CreateGameRequest("game9001"));
            service.createGame(new CreateGameRequest("game9002"));
            service.createGame(new CreateGameRequest("game9003"));
            assertEquals(new EmptyResult(), service.clear());
            assertEquals(0, service.listGames().games().size());
            // after clear no one should be able to login
            assertThrows(Unauthorized401Exception.class, () -> {
                service.login(new LoginRequest("user2", "pass2"));
            });

        } catch (DataAccessException e){
            fail("threw an unexpected DataAccessException");
        }

    }


}
