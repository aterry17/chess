package chess;

import java.util.Collection;
import java.util.ArrayList;

public class BishopMovesCalculator implements PieceMovesCalculator{ // extends means that this file is a subinterface of PieceMovesCalculator
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition startPosition) {
        Collection<ChessMove> listOfMoves = new ArrayList<ChessMove>();

        var teamColor = board.getPiece(startPosition).getTeamColor();

        // get all of the directions into an array
        int[][] directionsArray = {{1,1}, {1,-1}, {-1,1}, {-1,-1}};

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
            if ((isPositionOnBoard(potentialEnemyPosition) && (teamColor != board.getPiece(potentialEnemyPosition).getTeamColor()))){
                listOfMoves.add(new ChessMove(startPosition, potentialEnemyPosition, null));
            }
        }
        return listOfMoves;
    }
}
