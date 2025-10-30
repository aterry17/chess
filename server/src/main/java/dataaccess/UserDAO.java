package dataaccess;

import model.UserData;

public interface UserDAO {

    void clear();

    void createUser(UserData u);

    String getUsername(UserData u);
}