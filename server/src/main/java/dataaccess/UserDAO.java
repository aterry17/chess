package dataaccess;

import model.UserData;

import java.util.HashMap;

public interface UserDAO {

    void clear();
    void createUser(UserData u);
    void getUser();
    void insertUser(UserData u) throws DataAccessException;
}
