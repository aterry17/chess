package chess;

import java.util.ArrayList;
import java.util.Collection;

public class RookMovesCalculator implements PieceMovesCalculator {
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition startPosition) {
        Collection<ChessMove> listOfMoves = new ArrayList<ChessMove>();
        int[][] directionsArray = {{1,0}, {-1,0}, {0,1}, {0,-1}};
        contMove(board, listOfMoves, directionsArray, startPosition);
        return listOfMoves;}
}

