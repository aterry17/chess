package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMovesCalculator implements PieceMovesCalculator {
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition startPosition) {
        Collection<ChessMove> list_of_moves = new ArrayList<ChessMove>();

        var team_color = board.getPiece(startPosition).getTeamColor();

        // if it is the first time it is being moved, may move forward two
        if ((team_color == ChessGame.TeamColor.WHITE) && (startPosition.getRow() == 2)) {
            list_of_moves.add(new ChessMove(startPosition, new ChessPosition(4, startPosition.getColumn()), null)); // is the promotionPiece parameter supposed to be null here?
        }
        if ((team_color == ChessGame.TeamColor.BLACK) && (startPosition.getRow() == 7)) {
            list_of_moves.add(new ChessMove(startPosition, new ChessPosition(5, startPosition.getColumn()), null));
        }
        // move forward 1 square (just forward not to the other side)
        if (team_color == ChessGame.TeamColor.WHITE) {
            list_of_moves.add(new ChessMove(startPosition, new ChessPosition(3, startPosition.getColumn()), null));
        }
        if (team_color == ChessGame.TeamColor.BLACK) {
            list_of_moves.add(new ChessMove(startPosition, new ChessPosition(6, startPosition.getColumn()), null));
        }
        // enemy capture: diag_up_right or diag_up_left
        var diag_up_right_pos = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn() + 1);
        var diag_up_left_pos = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn() - 1);
        if (team_color != board.getPiece(diag_up_right_pos).getTeamColor()) {
            list_of_moves.add(new ChessMove(startPosition, diag_up_right_pos, null));
        }
        if (team_color != board.getPiece(diag_up_left_pos).getTeamColor()) {
            list_of_moves.add(new ChessMove(startPosition, diag_up_left_pos, null));
        }
        // promotion: when white pawn reaches row 8 or black pawn reaches row 1, replaced by player's choice of rook, knight, bishop, queen
        // don't know how to do this one yet, so I'll come back here later to fix it


        return list_of_moves;
    }
}
