package chess;

import java.util.Collection;
import java.util.List;

public interface PieceMovesCalculator {

    public default Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ChessPiece piece = board.getPiece(myPosition);

        return List.of();
    }

}
