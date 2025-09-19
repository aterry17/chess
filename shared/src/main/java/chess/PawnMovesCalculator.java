package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMovesCalculator implements PieceMovesCalculator {
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition startPosition) {
        Collection<ChessMove> list_of_moves = new ArrayList<ChessMove>();

        var team_color = board.getPiece(startPosition).getTeamColor();

        int[][] white_enemy_capture_directions = {{1, 1}, {1, -1}};
        int[][] black_enemy_capture_directions = {{-1, -1}, {-1, 1}};

        // starting fresh here









        // _________________________________________________________________________________________________________________________________________________________

//        var next_up_position_white = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn());
//        var two_up_position_white = new ChessPosition(4, startPosition.getColumn());
//        var next_up_position_black = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn());
//        var two_up_position_black = new ChessPosition(5, startPosition.getColumn());
//
//        if ((team_color == ChessGame.TeamColor.WHITE) && is_position_on_board(next_up_position_white) && is_position_empty(board, next_up_position_white)) {
//            list_of_moves.add(new ChessMove(startPosition, next_up_position_white, null));
//            if (is_position_on_board(two_up_position_white) && is_position_empty(board, next_up_position_white) && is_position_empty(board, two_up_position_white)) {
//                list_of_moves.add(new ChessMove(startPosition, two_up_position_white, null));
//            }
//
//            // enemy capture
//            for (var dir : white_enemy_capture_directions) {
//                var potential_enemy_pos = new ChessPosition(startPosition.getRow() + dir[0], startPosition.getColumn() + dir[1]);
//                if (is_position_on_board(potential_enemy_pos) && !is_position_empty(board, potential_enemy_pos) && (team_color != board.getPiece(potential_enemy_pos).getTeamColor())) {
//                    list_of_moves.add(new ChessMove(startPosition, potential_enemy_pos, null));
//                }
//            }
//        } else if ((team_color == ChessGame.TeamColor.BLACK) && is_position_on_board(next_up_position_black) && is_position_empty(board, next_up_position_black)) {
//            list_of_moves.add(new ChessMove(startPosition, next_up_position_black, null));
//            if (is_position_on_board(two_up_position_black) && is_position_empty(board, next_up_position_black) && is_position_empty(board, two_up_position_black)) {
//                list_of_moves.add(new ChessMove(startPosition, two_up_position_black, null));
//            }
//
//            // add in enemy capture
//            for (var dir : black_enemy_capture_directions) {
//                var potential_enemy_pos = new ChessPosition(startPosition.getRow() + dir[0], startPosition.getColumn() + dir[1]);
//                if (is_position_on_board(potential_enemy_pos) && !is_position_empty(board, potential_enemy_pos) && (team_color != board.getPiece(potential_enemy_pos).getTeamColor())) {
//                    list_of_moves.add(new ChessMove(startPosition, potential_enemy_pos, null));
//                }
//            }
//        }

        // _________________________________________________________________________________________________________________________________________

        // capture enemy moves if you haven't moved yet but can still capture an enemy:
        // white capture enemy
//        for (var dir : white_enemy_capture_directions) {
//            var potential_enemy_pos = new ChessPosition(startPosition.getRow() + dir[0], startPosition.getColumn() + dir[1]);
//            if (is_position_on_board(potential_enemy_pos) && !is_position_empty(board, potential_enemy_pos) && (team_color != board.getPiece(potential_enemy_pos).getTeamColor())) {
//                list_of_moves.add(new ChessMove(startPosition, potential_enemy_pos, null));
//            }
//
//            // black capture enemy
//            for (var dir1 : black_enemy_capture_directions) {
//                var potential_enemy_pos = new ChessPosition(startPosition.getRow() + dir1[0], startPosition.getColumn() + dir1[1]);
//                if (is_position_on_board(potential_enemy_pos) && !is_position_empty(board, potential_enemy_pos) && (team_color != board.getPiece(potential_enemy_pos).getTeamColor())) {
//                    list_of_moves.add(new ChessMove(startPosition, potential_enemy_pos, null));
//                }
//
//            }

        return list_of_moves;
    }
}
