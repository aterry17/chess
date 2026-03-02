package dataaccess;

import model.GameData;

import java.util.ArrayList;

public interface GameDAO {
    void clear();
    void createGame(String gameName, String gameID);
    GameData getGame(String gameID);
    ArrayList<GameData> listGames();
    void updateGame(String playerColor, String playerUsername, String gameID);
}
