package dataaccess;

import model.UserData;
import org.mindrot.jbcrypt.BCrypt;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;
public class SqlUserDAO implements UserDAO{


    // userdata has username, password, and email

    public SqlUserDAO() throws DataAccessException {
        configureDatabase();
    }

    @Override
    public void clear() throws DataAccessException {
        var statement = "TRUNCATE userData";
        executeUpdate(statement);
    }


    @Override
    // put username, password, email in the database
    public void createUser(UserData u) throws DataAccessException {
        String username = u.username();
        String clearTextPass = u.password();
        String email = u.email();
        String hashedPass = BCrypt.hashpw(clearTextPass, BCrypt.gensalt());

        var statement = "INSERT INTO userData (username, password, email) VALUES (?, ?, ?)";
        executeUpdate(statement, username, hashedPass, email);

    }

    @Override
    //returns string username
    public String getUsername(UserData u) throws DataAccessException {
        // if username is in the database, return u.username();
        // else return null;
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username FROM userData WHERE email=?";
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                ps.setString(1, u.email()); // not sure about this line
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) { // this should be false if the set is empty, but may cause issues later?
                        return rs.getString("username");
                    }
                }
            }
        } catch (Exception e) {
            throw new ResponseException(ResponseException.Code.ServerError, String.format("Unable to read data: %s", e.getMessage()));
        }
        return null;

    }

    // returns new UserData(username, hashedPass, email);
    public UserData getUser(String username) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username, password, email FROM userData WHERE username=?";
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                ps.setString(1, username); // not sure about this line
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) { // this should be false if the set is empty, but may cause issues later?
                        return new UserData(rs.getString("username"), rs.getString("password"), rs.getString("email"));
                    }
                }
            }
        } catch (Exception e) {
            throw new ResponseException(ResponseException.Code.ServerError, String.format("Unable to read data: %s", e.getMessage()));
        }
        return null;
    }

    public boolean correctPassword(String username, String providedClearTextPass) throws DataAccessException {
        var hashedPass = getUser(username).password();
        return BCrypt.checkpw(providedClearTextPass, hashedPass);
    }




    /// password hashing -- from project specs -----------------------------------------
//    void storeUserPassword(String username, String clearTextPassword) {
//        String hashedPassword = BCrypt.hashpw(clearTextPassword, BCrypt.gensalt());
//
//        // write the hashed password in database along with the user's other information
//        writeHashedPasswordToDatabase(username, hashedPassword);
//    }

    /// password de-hashing -- from project specs
//    boolean verifyUser(String username, String providedClearTextPassword) {
//        // read the previously hashed password from the database
//        var hashedPassword = readHashedPasswordFromDatabase(username);
//
//        return BCrypt.checkpw(providedClearTextPassword, hashedPassword);
//    }

    ///  ---------------------------------------------------------------------------------



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
           // should email be NOT NULL?
            """
            CREATE TABLE IF NOT EXISTS  userData (
              `id` int NOT NULL AUTO_INCREMENT,
              `username` varchar(256) NOT NULL,
              `password` varchar(256) NOT NULL,
              `email` varchar(256) NOT NULL,
              `json` TEXT DEFAULT NULL,
              PRIMARY KEY (`id`),
              INDEX(username)
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
//        catch (DataAccessException ex) {
//            throw new ResponseException(ResponseException.Code.ServerError, String.format("configureDatabase threw unexpected DataAccessException"));
//        }
    }



}




