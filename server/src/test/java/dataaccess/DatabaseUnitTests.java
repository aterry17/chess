package dataaccess;

import model.UserData;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class DatabaseUnitTests {


    ///  SqlUserDAO tests:

    @Test
    public void clearTestUser(){
        try {
            var sqlDataBase = new SqlUserDAO();
            sqlDataBase.clear();
            sqlDataBase.createUser(new UserData("user1", "pass1", "email1"));
            sqlDataBase.clear();
            assertNull(sqlDataBase.getUser("user1"));
        } catch (DataAccessException e){
            fail("clear threw an unexpected DataAccessException");
        }
    }
    @Test
    public void createUserPositiveTest(){}
    @Test
    public void createUserNegativeTest(){}
    @Test
    public void getUsernameUserPositiveTest(){}
    @Test
    public void getUsernameUserNegativeTest(){}
    @Test
    public void getUserPositiveTest(){}
    @Test
    public void getUserNegativeTest(){}
    @Test
    public void correctPasswordPositiveTest(){}
    @Test
    public void correctPasswordNegativeTest(){}







    /// SqlAuthDAO tests:

    @Test
    public void clearTestAuth(){
        try {
            var sqlDataBase = new SqlAuthDAO();
            sqlDataBase.clear();
            sqlDataBase.insertAuth("user1", "auth1");
            sqlDataBase.clear();
            assertFalse(sqlDataBase.containsAuth("auth1"));
        } catch (DataAccessException e){
            fail("clear threw an unexpected DataAccessException");
        }

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
    public void deleteAuthNegativeTest(){
        try {
            var sqlDataBase = new SqlAuthDAO();
            sqlDataBase.clear();
            assertThrows(Unauthorized401Exception.class, () -> {
                sqlDataBase.deleteAuth("auth1");
            });

        } catch (DataAccessException e){
            fail("deleteAuth threw an unexpected DataAccessException");
        }
    }

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
    public void getUsernameAuthPositiveTest(){
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
    public void getUsernameAuthNegativeTest(){
        try {
            var sqlDataBase = new SqlAuthDAO();
            sqlDataBase.clear();
            assertNull(sqlDataBase.getUsername("non-existent"));
        } catch (DataAccessException e){
            fail("getUsername threw an unexpected DataAccessException");
        }

    }




}
