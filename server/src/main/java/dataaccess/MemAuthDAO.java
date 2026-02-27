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

    /// do we even need a getAuth?
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
                it.remove(); // is this going to lead to a concurrent modification error? since we're deleting something and then continuing to traverse the set?
                 // should we put in a break here so we're not messing with a concurrent modification error (see textbook pg.254)
                return;
            }
        }
        throw new Unauthorized401Exception("authtoken not found in database"); // this should only throw if we make it through the set and we haven't
    }

    public boolean containsAuth(String authToken) {
        Iterator<AuthData> it = database.iterator();
        while (it.hasNext()) {
            var data = it.next();
            if (data.authToken().equals(authToken)) { // this is likely the problem line -- it's not finding some authtokens
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
