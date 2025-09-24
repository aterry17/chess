package chess;

import java.util.Collection;
import java.util.ArrayList;

public class QueenMovesCalculator implements PieceMovesCalculator { // extends means that this file is a subinterface of PieceMovesCalculator
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition startPosition) {
        Collection<ChessMove> listOfMoves = new ArrayList<ChessMove>();
        int[][] directionsArray = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}, {1,0}, {-1,0}, {0,1}, {0,-1}};
        contMove(board, listOfMoves, directionsArray, startPosition);
        return listOfMoves;
    }
}