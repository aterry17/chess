package dataaccess;

import model.GameData;

import java.util.ArrayList;
import java.util.List;

public interface GameDAO {
    void clear();
    void createGame(String gameName, String ID);
    void getGame();
    ArrayList<GameData> listGames();
    void updateGame(String playerColor, String playerUsername, String gameID);
}
