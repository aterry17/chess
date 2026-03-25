package dataaccess;

import com.google.gson.Gson;
import model.GameData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class SqlGameDAO implements GameDAO{

    public SqlGameDAO() throws DataAccessException{
        configureDatabase();
    }

    public void clear() throws DataAccessException{
        var statement = "TRUNCATE gameData";
        executeUpdate(statement);
    }

    public void createGame(String gameName, String gameID) throws DataAccessException{
        var newGame = new GameData(gameID, null, null, gameName);
        String json = new Gson().toJson(newGame);
        var statement = "INSERT INTO gameData (gameID, json) VALUES (?, ?)";
        executeUpdate(statement, gameID, json);
    }

    public GameData getGame(String gameID) throws DataAccessException{
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "SELECT gameID, json FROM gameData WHERE gameID=?";
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                ps.setString(1, gameID);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readGame(rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new ResponseException(ResponseException.Code.ServerError, String.format("Unable to read data: %s", e.getMessage()));
        }
        return null;

    }

    public ArrayList<GameData> listGames() throws DataAccessException{
        var result = new ArrayList<GameData>();
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "SELECT gameID, json FROM gameData";
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        result.add(readGame(rs));
                    }
                }
            }
        } catch (Exception e) {
            throw new ResponseException(ResponseException.Code.ServerError, String.format("Unable to read data: %s", e.getMessage()));
        }
        return result;
    }

    public void updateGame(String playerColor, String playerUsername, String gameID) throws DataAccessException{
        GameData game = getGame(gameID);
        String gameName = game.gameName();
        String json = null;
        if (playerColor.equals("WHITE")){
            String blackUsername = game.blackUsername();
            var newGame = new GameData(gameID, playerUsername, blackUsername, gameName);
            json = new Gson().toJson(newGame);

        }
        else if (playerColor.equals("BLACK")){
            String whiteUsername = game.whiteUsername();
            var newGame = new GameData(gameID, whiteUsername, playerUsername, gameName);
            json = new Gson().toJson(newGame);

        }
        var statement = "UPDATE gameData SET json=? WHERE gameID=? VALUES (?, ?)"; // no confidence in this line
        executeUpdate(statement, json, gameID);
    }

    public String generateGameID(){
        // generate an integer 1000-9999
        Random rand = new Random();
        return String.valueOf(rand.nextInt(1000,10000));
    }

    public boolean validGameID(String gameID) throws DataAccessException{
        if (getGame(gameID) == null){
            return false;
        } return true;
    }

    // taken from PetShop
    private GameData readGame(ResultSet rs) throws SQLException{
        var json = rs.getString("json");
        GameData game = new Gson().fromJson(json, GameData.class);
        return game; // PetShop last line is: game.setID(gameID);
    }





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
            CREATE TABLE IF NOT EXISTS  gameData (
              `id` int NOT NULL AUTO_INCREMENT,
              `gameID` varchar(256) NOT NULL,
              `json` TEXT DEFAULT NULL,
              PRIMARY KEY (`id`),
              INDEX(gameID)
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
