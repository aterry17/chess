package chess;

import java.util.ArrayList;
import java.util.Collection;

public class RookMovesCalculator implements PieceMovesCalculator {
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition startPosition) {
        Collection<ChessMove> listOfMoves = new ArrayList<ChessMove>();

        var teamColor = board.getPiece(startPosition).getTeamColor();

        // get all of the directions into an array
        int[][] directionsArray = {{1,0}, {-1,0}, {0,1}, {0,-1}};

        // trying one set of nested loops for all of the directions instead of diag_up_right & diag_up_left & etc.
        for(var direction: directionsArray){
            var currPosition = new ChessPosition(startPosition.getRow() + direction[0], startPosition.getColumn() + direction[1]);
            while(isPositionOnBoard(currPosition) && isPositionEmpty(board, currPosition)){
                listOfMoves.add(new ChessMove(startPosition, currPosition, null));
                currPosition = new ChessPosition(currPosition.getRow() + direction[0], currPosition.getColumn() + direction[1]);
            }
            // since the while loop broke, either we're off the board or we ran into another player
            // make the capture enemy move here
            var potentialEnemyPosition = currPosition;
            // make sure it's actually on the board
            if (!isPositionOnBoard(potentialEnemyPosition)) {continue;} // continue is unecessary because there's no more code outside of the is / else if / else block -- but as soon as you add code beneath the block, the continue becomes very necessary
                // check the color
            else if(teamColor == board.getPiece(potentialEnemyPosition).getTeamColor()) {continue;}
                // assuming that everything is doing what I think it's doing, we should know herre that we
            else {
                listOfMoves.add(new ChessMove(startPosition, potentialEnemyPosition, null));
            }
        }
        return listOfMoves;}
}

