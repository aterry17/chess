package dataaccess;

import model.RegisterRequest;
import org.junit.jupiter.api.Test;
import service.Service;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseUnitTests {

    /// SqlAuthDAO tests:

    @Test
    public void clearTest(){}


    @Test
    public void insertAuthPositiveTest(){
        try {
            var sqlDataBase = new SqlAuthDAO();
            sqlDataBase.insertAuth("user1", "auth1");
            assertTrue(sqlDataBase.containsAuth("auth1")); // we'll have to figure out a way to test contains
            assertEquals("user1", sqlDataBase.getUsername("auth1")); // and a way to test this
        } catch (DataAccessException e){
            fail("insertAuth threw an unexpected DataAccessException");
        }
    }

    @Test
    public void insertAuthNegativeTest(){}

    @Test
    public void deleteAuthPositiveTest(){}

    @Test
    public void deleteAuthNegativeTest(){}

    @Test
    public void containsAuthPositiveTest(){}

    @Test
    public void containsAuthNegativeTest(){}

    @Test
    public void getUsernamePositiveTest(){

    }

    @Test
    public void getUsernameNegativeTest(){}




}
