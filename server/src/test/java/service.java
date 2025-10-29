import dataaccess.AlreadyTaken403Exception;
import dataaccess.DataAccessException;
import dataaccess.MemUserDAO;
import service.Service;
import model.RegisterRequest;
import model.RegisterResult;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class service {

    @Test
    public void registerPositiveTest(){
        Service service = new Service(new MemUserDAO());
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
        Service service = new Service(new MemUserDAO());
        try {
            service.register(new RegisterRequest("user1", "pass1", "email1")); // register the user the first time
            assertThrows(AlreadyTaken403Exception.class, () -> {
                service.register(new RegisterRequest("user1", "pass1", "email1"));
            });
        } catch (DataAccessException e){
            fail("service.register threw an unexpected DataAccessException");
        }
    }


}
  // not sure why we're getting a merge error?