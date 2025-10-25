package dataaccess;

import model.UserData;
import java.util.HashMap;

public class MemUserDAO implements UserDAO{

    private HashMap<String, UserData> database= new HashMap<>();

    @Override
    public void clear() {

    }

    @Override
    public void createUser(UserData u) {

    }

    @Override
    public void getUser() {

    }

    @Override
    public void insertUser(UserData u) throws DataAccessException {
        database.put(u.username(), u);

    }
}
