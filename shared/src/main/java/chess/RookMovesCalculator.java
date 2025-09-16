package chess;

import java.util.Collection;
import java.util.List;

public class RookMovesCalculator implements PieceMovesCalculator{
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return List.of();
    }

    @Override
    public boolean is_position_on_board(ChessPosition currPosition) {
        return false;
    }

    @Override
    public boolean is_position_filled(ChessPosition currPosition) {
        return false;
    }
}
