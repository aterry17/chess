package dataaccess;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class DatabaseUnitTests {

    /// SqlAuthDAO tests:

    @Test
    public void clearTest(){

    }


    @Test
    public void insertAuthPositiveTest(){
        try {
            var sqlDataBase = new SqlAuthDAO();
            sqlDataBase.clear();
            sqlDataBase.insertAuth("user1", "auth1");
            assertTrue(sqlDataBase.containsAuth("auth1"));
            assertEquals("user1", sqlDataBase.getUsername("auth1"));
        } catch (DataAccessException e){
            fail("insertAuth threw an unexpected DataAccessException");
        }
    }

    @Test
    public void insertAuthNegativeTest(){
        try {
            var sqlDataBase = new SqlAuthDAO();
            sqlDataBase.clear();
            sqlDataBase.insertAuth("user1", "auth1");
            // Look for an incorrect authToken
            assertFalse(sqlDataBase.containsAuth("badauth")); // we'll have to figure out a way to test contains

//            // Bad parameters -- I actually don't know how to test bad parameters here
//            assertThrows(DataAccessException.class, () -> {
//                sqlDataBase.insertAuth("","badParams");
//            });
        } catch (DataAccessException e){
            fail("insertAuth threw an unexpected DataAccessException");
        }
    }

    @Test
    public void deleteAuthPositiveTest(){
        try {
            var sqlDataBase = new SqlAuthDAO();
            sqlDataBase.clear();
            sqlDataBase.insertAuth("user1", "auth1");
            sqlDataBase.insertAuth("user2", "auth2");
            sqlDataBase.deleteAuth("auth1");
            assertEquals(null, sqlDataBase.getUsername("auth1"));

        } catch (DataAccessException e){
            fail("deleteAuth threw an unexpected DataAccessException");
        }
    }

    @Test
    public void deleteAuthNegativeTest(){}

    @Test
    public void containsAuthPositiveTest(){
        try {
            var sqlDataBase = new SqlAuthDAO();
            sqlDataBase.clear();
            sqlDataBase.insertAuth("user1", "auth1");
            assertTrue(sqlDataBase.containsAuth("auth1"));
        } catch (DataAccessException e){
            fail("containsAuth threw an unexpected DataAccessException");
        }

    }

    @Test
    public void containsAuthNegativeTest(){
        try {
            var sqlDataBase = new SqlAuthDAO();
            sqlDataBase.clear();
            assertFalse(sqlDataBase.containsAuth("auth1")); // call contains on empty database
            sqlDataBase.insertAuth("user1", "auth1");
            assertFalse(sqlDataBase.containsAuth("auth2")); // call contains with bad parameter on non-empty database
        } catch (DataAccessException e){
            fail("containsAuth threw an unexpected DataAccessException");
        }
    }

    @Test
    public void getUsernamePositiveTest(){
        try {
            var sqlDataBase = new SqlAuthDAO();
            sqlDataBase.clear();
            sqlDataBase.insertAuth("user1", "auth1");
            assertEquals("user1", sqlDataBase.getUsername("auth1"));
//            System.out.println(sqlDataBase.getUsername("auth1"));
        } catch (DataAccessException e){
            fail("getUsername threw an unexpected DataAccessException");
        }

    }

    @Test
    public void getUsernameNegativeTest(){}




}
