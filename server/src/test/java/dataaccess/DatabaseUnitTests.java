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
    public void clearTestGame(){
        try {
            var sqlDataBase = new SqlGameDAO();
            sqlDataBase.clear();
            sqlDataBase.createGame("game1", "1111");
            sqlDataBase.clear();
            assertNull(sqlDataBase.getGame("1111"));
        } catch (DataAccessException e){
            fail("clear threw an unexpected DataAccessException");
        }
    }
    @Test
    public void createGamePositiveTest(){
        try {
            var sqlDataBase = new SqlGameDAO();
            sqlDataBase.clear();
            var game = new GameData("1111", null, null, "game1");
            sqlDataBase.createGame("game1", "1111");

            assertNotNull(sqlDataBase.getGame("1111"));
            assertEquals(game, sqlDataBase.getGame("1111"));
        } catch (DataAccessException e){
            fail("threw an unexpected DataAccessException");
        }
    }
    @Test
    public void createGameNegativeTest(){
        try {
            var sqlDataBase = new SqlGameDAO();
            sqlDataBase.clear();
            assertThrows(DataAccessException.class, () -> {
                sqlDataBase.createGame(null, null);
            });

        } catch (DataAccessException e){
            fail("threw an unexpected DataAccessException");
        }
    }
    @Test
    public void getGamePositiveTest(){
        try {
            var sqlDataBase = new SqlGameDAO();
            sqlDataBase.clear();
            var game1 = new GameData("1111", null, null, "game1");
            var game2= new GameData("2222", null, null, "game2");
            sqlDataBase.createGame("game1", "1111");
            sqlDataBase.createGame("game2", "2222");
            assertEquals(game1, sqlDataBase.getGame("1111"));
            assertEquals(game2, sqlDataBase.getGame("2222"));
            assertNull(sqlDataBase.getGame("3333"));

        } catch (DataAccessException e){
            fail("threw an unexpected DataAccessException");
        }
    }
    @Test
    public void getGameNegativeTest(){
        try {
            var sqlDataBase = new SqlGameDAO();
            sqlDataBase.clear();
            sqlDataBase.createGame("game1", "1111");
            assertNull(sqlDataBase.getGame(null));
            assertNull(sqlDataBase.getGame("badID"));

        } catch (DataAccessException e){
            fail("threw an unexpected DataAccessException");
        }
    }
    @Test
    public void listGamesPositiveTest(){
        try {
            var sqlDataBase = new SqlGameDAO();
            sqlDataBase.clear();
            var game1 = new GameData("1111", null, null, "game1");
            var game2 = new GameData("2222", null, null, "game2");
            var game3 = new GameData("3333", null, null, "game3");

            sqlDataBase.createGame("game1", "1111");
            sqlDataBase.createGame("game2", "2222");
            sqlDataBase.createGame("game3", "3333");
            var expectedResultList = new ArrayList<GameData>();
            expectedResultList.add(game1);
            expectedResultList.add(game2);
            expectedResultList.add(game3);
            assertEquals(expectedResultList, sqlDataBase.listGames());

        } catch (DataAccessException e){
            fail("threw an unexpected DataAccessException");
        }
    }
    @Test
    public void listGamesNegativeTest(){
        try {
            var sqlDataBase = new SqlGameDAO();
            sqlDataBase.clear();
            var game1 = new GameData("1111", null, null, "game1");
            var game2 = new GameData("2222", null, null, "game2");
            var game3 = new GameData("3333", null, null, "game3");
            var expectedResultList = new ArrayList<GameData>();
            expectedResultList.add(game1);
            expectedResultList.add(game2);
            expectedResultList.add(game3);
            assertNotEquals(expectedResultList, sqlDataBase.listGames());
            assertTrue(sqlDataBase.listGames().isEmpty());

        } catch (DataAccessException e){
            fail("threw an unexpected DataAccessException");
        }
    }
    @Test
    public void updateGamePositiveTest(){
        try {
            var sqlDataBase = new SqlGameDAO();
            sqlDataBase.clear();
            var game1 = new GameData("1111", "userW", null, "game1");
            var game2 = new GameData("2222", "userW", "userB", "game2");
            var game3 = new GameData("3333", null, "userB", "game3");

            sqlDataBase.createGame("game1", "1111");
            sqlDataBase.createGame("game2", "2222");
            sqlDataBase.createGame("game3", "3333");

            sqlDataBase.updateGame("WHITE", "userW", "1111");
            sqlDataBase.updateGame("WHITE", "userW", "2222");
            sqlDataBase.updateGame("BLACK", "userB", "2222");
            sqlDataBase.updateGame("BLACK", "userB", "3333");

            assertEquals(game1, sqlDataBase.getGame("1111"));
            assertEquals(game2, sqlDataBase.getGame("2222"));
            assertEquals(game3, sqlDataBase.getGame("3333"));

        } catch (DataAccessException e){
            fail("threw an unexpected DataAccessException");
        }
    }
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
