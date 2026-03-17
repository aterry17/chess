package dataaccess;

import model.AuthData;

public interface AuthDAO {
    void clear();
    void insertAuth(String username, String authToken);
    void deleteAuth(String username) throws Unauthorized401Exception;
    boolean containsAuth(String authToken);
    String getUsername(String authToken);
}
