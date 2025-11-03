package dataaccess;

import model.GameData;

import java.util.ArrayList;
import java.util.List;

public interface GameDAO {
    void clear();
    void createGame(String gameName, String ID);
    GameData getGame(String ID);
    ArrayList<GameData> listGames();
    void updateGame(String playerColor, String playerUsername, String gameID);
}
