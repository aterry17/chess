package dataaccess;

import model.UserData;

import java.util.HashMap;

public interface UserDAO {

    void clear();

    void createUser(UserData u);

    String getUser(UserData u);
}