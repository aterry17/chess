package dataaccess;

import model.GameData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MemGameDAO implements GameDAO{
    // ID:GameData
    private final HashMap<String, GameData> database = new HashMap<>();
    @Override
    public void clear() {
        database.clear();
    }

    @Override
    public void createGame(String gameName, String gameID) {
        database.put(gameID, new GameData(gameID, null, null, gameName));
    }

    @Override
    public GameData getGame(String gameID) {
        return database.get(gameID);
    }

    @Override
    public ArrayList<GameData> listGames() {
        var list = new ArrayList<GameData>();
        for (String gameID: database.keySet()) {
            list.add(getGame(gameID));
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
//    public String generateGameID(){
//        // generate an integer 1000-9999
//        Random rand = new Random();
//        return String.valueOf(rand.nextInt(1000,10000));
//    }

    public boolean validGameID(String gameID) {
        for (String gameName : database.keySet()) {
            if (database.get(gameName).gameID().equals(gameID)) {
                return true;
            }
        }
        return false;
    }
}
