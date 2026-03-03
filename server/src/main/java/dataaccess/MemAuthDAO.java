package dataaccess;

import model.AuthData;

import java.util.HashSet;
import java.util.Iterator;

public class MemAuthDAO implements AuthDAO {
    final private HashSet<AuthData> database = new HashSet<>();

    public void clear(){
        database.clear();
    }


    @Override
    public void insertAuth(String authtoken, String username) {
        database.add(new AuthData(authtoken, username));
    }

    @Override
    public AuthData getAuth(String username) {
        return null;
    }

    @Override
    public void deleteAuth(String authtoken) throws Unauthorized401Exception {
        Iterator<AuthData> it = database.iterator();
        while (it.hasNext()) {
            AuthData data = it.next();
            if (data.authToken().equals(authtoken)) {
                it.remove();
                return;
            }
        }
        throw new Unauthorized401Exception("authtoken not found in database");
    }

    public boolean containsAuth(String authToken) {
        Iterator<AuthData> it = database.iterator();
        while (it.hasNext()) {
            var data = it.next();
            if (data.authToken().equals(authToken)) {
                return true;
            }
        }
        return false;
    }

    public String getUsername(String authtoken) {
        Iterator<AuthData> it = database.iterator();
        while (it.hasNext()) {
            var data = it.next();
            if (data.authToken().equals(authtoken)) {
                return data.username();
            }
        }
        return null;
    }
}
