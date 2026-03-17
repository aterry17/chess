package dataaccess;

import model.AuthData;
import com.google.gson.Gson;
//import dataaccess.ResponseException;
import java.sql.*;
import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;


public class SqlAuthDAO implements AuthDAO{

    public SqlAuthDAO() throws DataAccessException {
        configureDatabase();
        // how to put a table in it now?
    }
    //our database should just have one table of AuthData(authToken, username); --> probably just list these, no need to map anything


    public void clear(){}


    public void insertAuth(String username, String authToken){};


    public void deleteAuth(String username) throws Unauthorized401Exception{};


    public boolean containsAuth(String authToken){
        return false;
    };


    public String getUsername(String authToken){
        return "";
    };



    // modified from PetShop
    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  AuthData (
              `id` int NOT NULL AUTO_INCREMENT,
              `username` varchar(256) NOT NULL,
              `authToken` varchar(256) NOT NULL,
              `json` TEXT DEFAULT NULL,
              PRIMARY KEY (`id`),
              INDEX(username),
              INDEX(authToken)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };


    private void configureDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (Connection conn = DatabaseManager.getConnection()) {
            for (String statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new ResponseException(ResponseException.Code.ServerError, String.format("Unable to configure database: %s", ex.getMessage()));
        }
    }







}


