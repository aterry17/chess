package dataaccess;

import model.AuthData;

public interface AuthDAO {
    void insertAuth(String username, String authtoken);
    AuthData getAuth(String username);
    void deleteAuth(String username);
}
