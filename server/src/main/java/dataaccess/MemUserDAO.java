package dataaccess;

import model.UserData;
import java.util.HashMap;

public class MemUserDAO implements UserDAO{

    private HashMap<String, UserData> database= new HashMap<>();

    @Override
    public void clear() {
        database = new HashMap<>();
    }

    @Override
    public String getUser(UserData u) { // right now only returning username
        return u.username();
    }

    @Override
    public void createUser(UserData u) {
        database.put(u.username(), u);

    }
}
