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
            var curr_position = new ChessPosition(startPosition.getRow() + direction[0], startPosition.getColumn() + direction[1]);

            // move to an empty space
            if(isPositionOnBoard(curr_position) && isPositionEmpty(board, curr_position)) {
                listOfMoves.add(new ChessMove(startPosition, curr_position, null));
                curr_position = new ChessPosition(curr_position.getRow() + direction[0], curr_position.getColumn() + direction[1]);
            }

            // move & capture enemy
            if(isPositionOnBoard(curr_position) && !isPositionEmpty(board, curr_position) && (teamColor != board.getPiece(curr_position).getTeamColor())){
                listOfMoves.add(new ChessMove(startPosition, curr_position, null));

            }
        }
        return listOfMoves;
    }
}