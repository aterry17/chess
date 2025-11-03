import dataaccess.*;
import model.*;
import service.Service;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class service {

    @Test
    public void registerPositiveTest(){
        Service service = new Service(new MemUserDAO(), new MemAuthDAO(), new MemGameDAO());
        // check to see that the register result properly sent with the correct username
        try {
            var result = service.register(new RegisterRequest("user1", "pass1", "email1"));
            assertEquals(result.username(), "user1");
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
            assertEquals(result.username(), "user1");
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
            assertEquals(result, new EmptyResult());
        } catch (DataAccessException e){
            fail("service.register threw an unexpected DataAccessException");
        }
    }


    /// FIX THIS
    @Test
    public void logoutNegativeTest(){
//        Service service = new Service(new MemUserDAO(), new MemAuthDAO(), new MemGameDAO());
//        try {
//            service.register(new RegisterRequest("user1", "pass1", "email1"));
//            service.login(new LoginRequest("user1", "pass1"));
//            assertThrows(Exception.class, () -> {
//                service.logout(); // can't call logout w/ empty parameters?
//            });
//        } catch (DataAccessException e){
//            fail("service.register threw an unexpected DataAccessException");
//        }
    }

    @Test
    public void createGamePositiveTest(){
//        Service service = new Service(new MemUserDAO(), new MemAuthDAO(), new MemGameDAO());
//        try {
//            service.register(new RegisterRequest("user1", "pass1", "email1"));
//            service.login(new LoginRequest("user1", "pass1"));
//            CreateGameResult result = service.createGame(new CreateGameRequest("game9000"));
//
//            assertEquals(result.gameID(), );
//        } catch (DataAccessException e){
//            fail("service.register threw an unexpected DataAccessException");
//        }
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
