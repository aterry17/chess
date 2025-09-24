package chess;

import java.util.Collection;

public interface PieceMovesCalculator {

    Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition);
    default boolean isPositionOnBoard(ChessPosition currPosition){
        return ((currPosition.getRow() >= 1) && (currPosition.getRow() <= 8) && (currPosition.getColumn() >=1) && (currPosition.getColumn() <= 8));
    }
    default boolean isPositionEmpty(ChessBoard board, ChessPosition currPosition){
        if (!isPositionOnBoard(currPosition)) {
            throw new RuntimeException("Position is off board");
        }
        return (board.getPiece(currPosition) == null);
    }

}
