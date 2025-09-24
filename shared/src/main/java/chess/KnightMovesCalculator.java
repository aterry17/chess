package chess;

import java.util.Collection;
import java.util.ArrayList;

public class KnightMovesCalculator implements PieceMovesCalculator { // extends means that this file is a subinterface of PieceMovesCalculator

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition startPosition) {
        Collection<ChessMove> listOfMoves = new ArrayList<ChessMove>();

        var teamColor = board.getPiece(startPosition).getTeamColor();

        // get all of the directions into an array
        int[][] directionsArray = {{1,2}, {1,-2}, {-1,2}, {-1,-2}, {2,1}, {-2,1}, {2,-1}, {-2,-1}};

        // cycle through the directions
        for (var direction : directionsArray) {
            var currPosition = new ChessPosition(startPosition.getRow() + direction[0], startPosition.getColumn() + direction[1]);

            // move to an empty space
            if(isPositionOnBoard(currPosition) && isPositionEmpty(board, currPosition)) {
                listOfMoves.add(new ChessMove(startPosition, currPosition, null));
                currPosition = new ChessPosition(currPosition.getRow() + direction[0], currPosition.getColumn() + direction[1]);
            }

            // move & capture enemy
            if(isPositionOnBoard(currPosition) && !isPositionEmpty(board, currPosition) && (teamColor != board.getPiece(currPosition).getTeamColor())){
                listOfMoves.add(new ChessMove(startPosition, currPosition, null));

            }
        }
        return listOfMoves;
    }
}