package dataaccess;

import model.GameData;
import model.UserData;
import org.eclipse.jetty.server.Authentication;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
public class DatabaseUnitTests {

    /// SqlGameDAO tests:

    @Test
    public void clearTestGame(){}
    @Test
    public void createGamePositiveTest(){}
    @Test
    public void createGameNegativeTest(){}
    @Test
    public void getGamePositiveTest(){}
    @Test
    public void getGameNegativeTest(){}
    @Test
    public void listGamesPositiveTest(){}
    @Test
    public void listGamesNegativeTest(){}
    @Test
    public void updateGamePositiveTest(){}
    @Test
    public void updateGameNegativeTest(){}
    @Test
    public void validateGameIDPositiveTest(){}
    @Test
    public void validateGameIDNegativeTest(){}












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
    public void createUserPositiveTest(){
        try {
            var sqlDataBase = new SqlUserDAO();
            sqlDataBase.clear();
            var user = new UserData("user1", "pass1", "email1");
            sqlDataBase.createUser(user);
            assertNotNull(sqlDataBase.getUser("user1"));
            assertEquals("user1", sqlDataBase.getUsername(user));
        } catch (DataAccessException e){
            fail("threw an unexpected DataAccessException");
        }
    }
    @Test
    public void createUserNegativeTest(){
        try {
            var sqlDataBase = new SqlUserDAO();
            sqlDataBase.clear();
            assertThrows(DataAccessException.class, () -> {
                sqlDataBase.createUser(new UserData(null, null, null));
            });

        } catch (DataAccessException e){
            fail("threw an unexpected DataAccessException");
        }
    }
    @Test
    public void getUsernameUserPositiveTest(){
        try {
            var sqlDataBase = new SqlUserDAO();
            sqlDataBase.clear();
            var user1 = new UserData("user1", "pass1", "email1");
            var user2 = new UserData("user2", "pass2", "email2");
            sqlDataBase.createUser(user1);
            sqlDataBase.createUser(user2);
            assertEquals("user1", sqlDataBase.getUsername(user1));
            assertEquals("user2", sqlDataBase.getUsername(user2));
            assertNull(sqlDataBase.getUsername(new UserData("bad", "bad", "bad")));

        } catch (DataAccessException e){
            fail("threw an unexpected DataAccessException");
        }
    }
    @Test
    public void getUsernameUserNegativeTest(){
        try {
            var sqlDataBase = new SqlUserDAO();
            sqlDataBase.clear();
            assertNull(sqlDataBase.getUsername(new UserData(null, null, null)));

        } catch (DataAccessException e){
            fail("threw an unexpected DataAccessException");
        }
    }
    @Test
    public void getUserPositiveTest(){
        try {
            var sqlDataBase = new SqlUserDAO();
            sqlDataBase.clear();
            var user1 = new UserData("user1", "pass1", "email1");
            var user2 = new UserData("user2", "pass2", "email2");
            sqlDataBase.createUser(user1);
            sqlDataBase.createUser(user2);
            assertEquals("email1", sqlDataBase.getUser("user1").email());
            assertEquals("email2", sqlDataBase.getUser("user2").email());
            assertNull(sqlDataBase.getUser("badUser"));

        } catch (DataAccessException e){
            fail("threw an unexpected DataAccessException");
        }
    }
    @Test
    public void getUserNegativeTest(){
        try {
            var sqlDataBase = new SqlUserDAO();
            sqlDataBase.clear();
            assertNull(sqlDataBase.getUser(null));

        } catch (DataAccessException e){
            fail("threw an unexpected DataAccessException");
        }
    }
    @Test
    public void correctPasswordPositiveTest(){
        try {
            var sqlDataBase = new SqlUserDAO();
            sqlDataBase.clear();
            var user1 = new UserData("user1", "pass1", "email1");
            var user2 = new UserData("user2", "pass2", "email2");
            sqlDataBase.createUser(user1);
            sqlDataBase.createUser(user2);
            assertTrue(sqlDataBase.correctPassword("user1", "pass1"));
            assertTrue(sqlDataBase.correctPassword("user2", "pass2"));

        } catch (DataAccessException e){
            fail("threw an unexpected DataAccessException");
        }
    }
    @Test
    public void correctPasswordNegativeTest(){
        try {
            var sqlDataBase = new SqlUserDAO();
            sqlDataBase.clear();
            var user1 = new UserData("user1", "pass1", "email1");
            var user2 = new UserData("user2", "pass2", "email2");
            sqlDataBase.createUser(user1);
            sqlDataBase.createUser(user2);
            assertFalse(sqlDataBase.correctPassword("user1", "badPass"));
            assertThrows(Exception.class, () -> {
                sqlDataBase.correctPassword(null, null);
            });

        } catch (DataAccessException e){
            fail("threw an unexpected DataAccessException");
        }
    }







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
