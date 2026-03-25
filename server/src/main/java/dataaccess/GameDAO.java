package dataaccess;

import model.GameData;

import java.util.ArrayList;

public interface GameDAO {
    void clear() throws DataAccessException;
    void createGame(String gameName, String gameID) throws DataAccessException;
    GameData getGame(String gameID) throws DataAccessException;
    ArrayList<GameData> listGames() throws DataAccessException;
    void updateGame(String playerColor, String playerUsername, String gameID) throws DataAccessException;
//    String generateGameID();
    boolean validGameID(String gameID) throws DataAccessException;
}
