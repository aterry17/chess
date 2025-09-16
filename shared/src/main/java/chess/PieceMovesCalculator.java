package chess;

import java.util.Collection;
import java.util.List;

public interface PieceMovesCalculator {

    Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition);
    boolean is_position_on_board(ChessPosition currPosition); // add body maybe
    boolean is_position_filled(ChessBoard board, ChessPosition currPosition);

}
