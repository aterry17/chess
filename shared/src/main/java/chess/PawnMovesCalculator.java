package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMovesCalculator implements PieceMovesCalculator {
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition startPosition) {
//        Collection<ChessMove> list_of_moves = new ArrayList<ChessMove>();

        var team_color = board.getPiece(startPosition).getTeamColor();
        ChessPosition one_up_position = null;
        ChessPosition two_up_position = null;
        ChessPosition capture_left_position = null;
        ChessPosition capture_right_position = null;
        int team_start_row = 0;

        // starting fresh here --> trying to mimic the format from the KingMovesCalculator --> okay actually that's not what we're doing

        // White Moves:
        if(team_color == ChessGame.TeamColor.WHITE) {
            one_up_position = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn());
            two_up_position = new ChessPosition(startPosition.getRow() + 2, startPosition.getColumn());
            capture_left_position = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn() - 1);
            capture_right_position = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn() + 1);
            team_start_row = 2;
        }
        else if(team_color == ChessGame.TeamColor.BLACK) {
            one_up_position = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn());
            two_up_position = new ChessPosition(startPosition.getRow() - 2, startPosition.getColumn());
            capture_left_position = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn() - 1);
            capture_right_position = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn() + 1);
            team_start_row = 7;
        }

        var list_of_moves = pawnMoves(team_color, board, team_start_row, startPosition, one_up_position, two_up_position, capture_left_position, capture_right_position);

//            if(is_position_on_board(one_up_position) && is_position_empty(board, one_up_position)){ // move up one if on board and empty
//                list_of_moves.add(new ChessMove(startPosition, one_up_position, null));
//                if((startPosition.getRow() == 2) && is_position_on_board(two_up_position) && is_position_empty(board, two_up_position)){
//                    list_of_moves.add(new ChessMove(startPosition, two_up_position, null)); // move up two if at starting row and if on board and empty
//                }
//            }
//            // capture enemy on left: if on board, not empty, and filled with enemy
//            if(is_position_on_board(capture_left_position) && !is_position_empty(board, capture_left_position) && (board.getPiece(capture_left_position).getTeamColor() != team_color)){
//                list_of_moves.add(new ChessMove(startPosition, capture_left_position, null));
//            }
//            // capture enemy on right: if on board, not empty, and filled with enemy
//            if(is_position_on_board(capture_right_position) && !is_position_empty(board, capture_right_position) && (board.getPiece(capture_right_position).getTeamColor() != team_color)){
//                list_of_moves.add(new ChessMove(startPosition, capture_right_position, null));
//            }

        return list_of_moves;

        }


    public Collection<ChessMove> pawnMoves (ChessGame.TeamColor team_color, ChessBoard board, int team_start_row, ChessPosition startPosition, ChessPosition one_up_position, ChessPosition two_up_position, ChessPosition capture_left_position, ChessPosition capture_right_position){
        Collection<ChessMove> list_of_moves = new ArrayList<ChessMove>();
        if(is_position_on_board(one_up_position) && is_position_empty(board, one_up_position)){ // move up one if on board and empty
            list_of_moves.add(new ChessMove(startPosition, one_up_position, null));
            if((startPosition.getRow() == team_start_row) && is_position_on_board(two_up_position) && is_position_empty(board, two_up_position)){
                list_of_moves.add(new ChessMove(startPosition, two_up_position, null)); // move up two if at starting row and if on board and empty
            }
        }
        // capture enemy on left: if on board, not empty, and filled with enemy
        if(is_position_on_board(capture_left_position) && !is_position_empty(board, capture_left_position) && (board.getPiece(capture_left_position).getTeamColor() != team_color)){
            list_of_moves.add(new ChessMove(startPosition, capture_left_position, null));
        }
        // capture enemy on right: if on board, not empty, and filled with enemy
        if(is_position_on_board(capture_right_position) && !is_position_empty(board, capture_right_position) && (board.getPiece(capture_right_position).getTeamColor() != team_color)){
            list_of_moves.add(new ChessMove(startPosition, capture_right_position, null));
        }

        return list_of_moves;
    }
}
