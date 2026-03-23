package dataaccess;

import model.UserData;

import javax.xml.crypto.Data;

public interface UserDAO {

    void clear() throws DataAccessException;

    void createUser(UserData u) throws DataAccessException;

    String getUsername(UserData u) throws DataAccessException;

    UserData getUser(String username) throws DataAccessException;

    boolean correctPassword(String username, String providedClearTextPass) throws DataAccessException;

}