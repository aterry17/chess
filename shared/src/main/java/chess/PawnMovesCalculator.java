package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMovesCalculator implements PieceMovesCalculator {
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition startPosition) {
        Collection<ChessMove> list_of_moves = new ArrayList<ChessMove>();

        var team_color = board.getPiece(startPosition).getTeamColor();

        int[][] white_enemy_capture_directions = {{1,1}, {1,-1}};
        int[][] black_enemy_capture_directions = {{-1, -1}, {-1,1}};

        var next_up_position_white = new ChessPosition(startPosition.getRow()+1, startPosition.getColumn());
        var two_up_position_white = new ChessPosition(4, startPosition.getColumn());
        var next_up_position_black = new ChessPosition(startPosition.getRow()-1, startPosition.getColumn());
        var two_up_position_black = new ChessPosition(5, startPosition.getColumn());

        if ((team_color == ChessGame.TeamColor.WHITE) && is_position_on_board(next_up_position_white) && is_position_empty(board, next_up_position_white)) {
            list_of_moves.add(new ChessMove(startPosition, next_up_position_white, null));
            if(is_position_on_board(two_up_position_white) && is_position_empty(board, next_up_position_white) && is_position_empty(board, two_up_position_white)){
                list_of_moves.add(new ChessMove(startPosition, two_up_position_white, null));
            }

            // add in enemy capture
        }

        else if ((team_color == ChessGame.TeamColor.BLACK) && is_position_on_board(next_up_position_black) && is_position_empty(board, next_up_position_black)) {
            list_of_moves.add(new ChessMove(startPosition, next_up_position_black, null));
            if(is_position_on_board(two_up_position_black) && is_position_empty(board, next_up_position_black) && is_position_empty(board, two_up_position_black)){
                list_of_moves.add(new ChessMove(startPosition, two_up_position_black, null));
            }

            // add in enemy capture
        }




        // enemy capture: diag_up_right or diag_up_left
        // need to check if diag_up_ positions have something in them before trying to use .getPiece --> right now it is returning null and that throws an exception

//        var diag_up_right_pos = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn() + 1);
//        var diag_up_left_pos = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn() - 1);
//        if ((board.getPiece(diag_up_right_pos) != null) && (team_color != board.getPiece(diag_up_right_pos).getTeamColor())) {
//            list_of_moves.add(new ChessMove(startPosition, diag_up_right_pos, null));
//        }
//        if ((board.getPiece(diag_up_left_pos) != null) && (team_color != board.getPiece(diag_up_left_pos).getTeamColor())) {
//            list_of_moves.add(new ChessMove(startPosition, diag_up_left_pos, null));
//        }
//        // promotion: when white pawn reaches row 8 or black pawn reaches row 1, replaced by player's choice of rook, knight, bishop, queen
//        // don't know how to do this one yet, so I'll come back here later to fix it
//        // probably add the move, but inside the move the promotionPiece shouldn't be null -- it should be the piece type to put in -- how do we take into account the player's choice of piece?

        return list_of_moves;
    }

}
