package dataaccess;

import model.UserData;
import java.util.HashMap;

public class MemUserDAO implements UserDAO{

   final private HashMap<String, UserData> database= new HashMap<>();

    @Override
    public void clear() {
        database.clear();
    }

    @Override
    public String getUsername(UserData u) { // right now only returning username
        if (database.get(u.username()) != null){
            return u.username();
        }
        else {
            return null;
        }
    }

    @Override
    public void createUser(UserData u) {
        database.put(u.username(), u);
    }

    public UserData getUser(String username){
        return database.get(username);
    }

    public void deleteUser(UserData u){
        database.remove(u.username());
    }
}
