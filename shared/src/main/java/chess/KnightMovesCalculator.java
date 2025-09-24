package chess;

import java.util.Collection;
import java.util.ArrayList;

public class KnightMovesCalculator implements PieceMovesCalculator { // extends means that this file is a subinterface of PieceMovesCalculator

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition startPosition) {
        Collection<ChessMove> listOfMoves = new ArrayList<ChessMove>();
        int[][] directionsArray = {{1,2}, {1,-2}, {-1,2}, {-1,-2}, {2,1}, {-2,1}, {2,-1}, {-2,-1}};
        stepMove(board, listOfMoves, directionsArray, startPosition);
        return listOfMoves;
    }
}