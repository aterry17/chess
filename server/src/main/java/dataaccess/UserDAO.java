package dataaccess;

import model.UserData;

public interface UserDAO {

    void clear();
    void creatUser();
    void getUser();
    void insertUser(UserData u) throws DataAccessException;
}
