package dataaccess;

import model.GameData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MemGameDAO implements GameDAO{
    // ID:GameData
    private final HashMap<String, GameData> database = new HashMap<>();
    @Override
    public void clear() {

    }

    @Override
    public void createGame(String gameName, String ID) {
        database.put(ID, new GameData(ID, null, null, gameName));
    }

    @Override
    public void getGame() {

    }

    @Override
    public ArrayList<GameData> listGames() {
        var list = new ArrayList<GameData>();
        for (String game: database.keySet()) {
            list.add(database.get(game));
        }
        return list;
    }

    @Override
    public void updateGame(String playerColor, String playerUsername, String gameID) {
        String gameName = database.get(gameID).gameName();
        if (playerColor.equals("WHITE")){
            String blackUsername = database.get(gameID).blackUsername();
            database.put(gameID, new GameData(gameID, playerUsername, blackUsername, gameName));
        }
        else if (playerColor.equals("BLACK")){
            String whiteUsername = database.get(gameID).whiteUsername();
            database.put(gameID, new GameData(gameID, whiteUsername, playerUsername, gameName));
        }

    }
    public String generateGameID(){
        // generate an integer 1000-9999
        Random rand = new Random();
        return String.valueOf(rand.nextInt(1000,10000));
    }
}
