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
        // table name is authData
    }
    //our database should just have one table of AuthData(authToken, username); --> probably just list these, no need to map anything

    public void clear() throws DataAccessException{
        // assuming we clear the table but don't delete it?
        var statement = "TRUNCATE authData";
        executeUpdate(statement);
    }

    @Override
    public void insertAuth(String username, String authToken) throws DataAccessException {
        var statement = "INSERT INTO AuthData (username, authToken, json) VALUES (?, ?, ?)";
        // I don't think we actually need to store the actual AuthData object
//        String json = new Gson().toJson(new AuthData(authToken, username));
        executeUpdate(statement, username, authToken);

    };


    public void deleteAuth(String authToken) throws DataAccessException{
        // call containsAuth to throw 401
        if (containsAuth(authToken) == false){
            throw new Unauthorized401Exception("authToken not found in database");
        }
        var statement = "DELETE FROM pet WHERE authToken=?";
        executeUpdate(statement, authToken);
    };


    public boolean containsAuth(String authToken) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "SELECT EXISTS (SELECT 1 FROM authData WHERE authToken=?)";
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                ps.setString(1, authToken); // not sure about this line
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next() == false) { // this should be false if the set is empty, but may cause issues later?
                        return false;
                    }
                }
            }
        } catch (Exception e) {
            throw new ResponseException(ResponseException.Code.ServerError, String.format("Unable to read data: %s", e.getMessage()));
        }
        return true;
    };


    public String getUsername(String authToken){
        return "";
    };



    // modified from PetShop
    private int executeUpdate(String statement, Object... params) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
                for (int i = 0; i < params.length; i++) {
                    Object param = params[i];
                    if (param instanceof String p) ps.setString(i + 1, p);
                    else if (param instanceof Integer p) ps.setInt(i + 1, p);
                    else if (param == null) ps.setNull(i + 1, NULL);
                }
                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }

                return 0;
            }
        } catch (SQLException e) {
            throw new ResponseException(ResponseException.Code.ServerError, String.format("unable to update database: %s, %s", statement, e.getMessage()));
        }
    }



    // modified from PetShop
    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  authData (
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


