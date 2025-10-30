package dataaccess;

import model.AuthData;
import model.UserData;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class MemAuthDAO implements AuthDAO{
    final private HashSet<AuthData> database= new HashSet<>();


    @Override
    public void insertAuth(String authtoken, String username) {
        database.add(new AuthData(authtoken, username));
    }

    /// do we even need a getAuth?
    @Override
    public AuthData getAuth(String username) {
        return null;
    }

    @Override
    public void deleteAuth(String username) {
        Iterator<AuthData> it = database.iterator();
        while(it.hasNext()) {
            AuthData data = it.next();
            if (data.username().equals(username)){
                it.remove();
            }
        }
    }
}
