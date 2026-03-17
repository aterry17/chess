package dataaccess;

import model.AuthData;

public interface AuthDAO {
    void clear() throws DataAccessException;
    void insertAuth(String username, String authToken) throws DataAccessException;
    void deleteAuth(String username) throws Unauthorized401Exception;
    boolean containsAuth(String authToken);
    String getUsername(String authToken);
}
