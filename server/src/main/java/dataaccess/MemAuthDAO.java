package dataaccess;

import model.AuthData;
import model.UserData;

import java.util.HashMap;

public class MemAuthDAO implements AuthDAO{
    final private HashMap<String, AuthData> database= new HashMap<>();


    @Override
    public void insertAuth(String username, String authtoken) {
        database.put(username, new AuthData(username, authtoken));
    }

    @Override
    public AuthData getAuth(String username) {
        return database.get(username);
    }

    @Override
    public void deleteAuth() {

    }
}
