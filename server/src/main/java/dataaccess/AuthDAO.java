package dataaccess;

import model.AuthData;

public interface AuthDAO {
    void clear() throws DataAccessException;
    void insertAuth(String username, String authToken) throws DataAccessException;
    void deleteAuth(String authToken) throws DataAccessException;
    boolean containsAuth(String authToken) throws DataAccessException;
    String getUsername(String authToken);
}
