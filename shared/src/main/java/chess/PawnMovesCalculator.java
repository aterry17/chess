package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMovesCalculator implements PieceMovesCalculator {
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition startPosition) {
        // initialize variables
        var team_color = board.getPiece(startPosition).getTeamColor();
        ChessPosition one_up_position = null;
        ChessPosition two_up_position = null;
        ChessPosition capture_left_position = null;
        ChessPosition capture_right_position = null;
        int team_start_row = 0;
        // White Moves:
        if(team_color == ChessGame.TeamColor.WHITE) {
            one_up_position = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn());
            two_up_position = new ChessPosition(startPosition.getRow() + 2, startPosition.getColumn());
            capture_left_position = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn() - 1);
            capture_right_position = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn() + 1);
            team_start_row = 2;
        } // Black Moves
        else if(team_color == ChessGame.TeamColor.BLACK) {
            one_up_position = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn());
            two_up_position = new ChessPosition(startPosition.getRow() - 2, startPosition.getColumn());
            capture_left_position = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn() - 1);
            capture_right_position = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn() + 1);
            team_start_row = 7;
        } // Throw an exception for wrong color or null
        else {
            throw new RuntimeException("Team Color has not been properly set");
        }
        var list_of_moves = pawnMoves(team_color, board, team_start_row, startPosition, one_up_position, two_up_position, capture_left_position, capture_right_position);
        return list_of_moves;
    }

    public Collection<ChessMove> pawnMoves (ChessGame.TeamColor team_color, ChessBoard board, int team_start_row, ChessPosition startPosition, ChessPosition one_up_position, ChessPosition two_up_position, ChessPosition capture_left_position, ChessPosition capture_right_position){
        Collection<ChessMove> list_of_moves = new ArrayList<ChessMove>();
        if(is_position_on_board(one_up_position) && is_position_empty(board, one_up_position)){
            // move up two for starting row
            if((startPosition.getRow() == team_start_row) && is_position_on_board(two_up_position) && is_position_empty(board, two_up_position)){
                list_of_moves.add(new ChessMove(startPosition, two_up_position, null)); // move up two if at starting row and if on board and empty
            }// check for promotion row
            if(is_row_promotion(team_color, one_up_position.getRow())){
                add_promotion_moves(startPosition, one_up_position, list_of_moves);
            } // move up one (no promotion)
            else{
                list_of_moves.add(new ChessMove(startPosition, one_up_position, null));
            }
        }// capture enemy on left: if on board, not empty, and filled with enemy
        if(is_position_on_board(capture_left_position) && !is_position_empty(board, capture_left_position) && (board.getPiece(capture_left_position).getTeamColor() != team_color)){
            // add in promotion move if it's a promotion row
            if(is_row_promotion(team_color, capture_left_position.getRow())){
                add_promotion_moves(startPosition, capture_left_position, list_of_moves);
            } // capture if no promotion
            else{
                list_of_moves.add(new ChessMove(startPosition, capture_left_position, null));
            }
        }// capture enemy on right: if on board, not empty, and filled with enemy
        if(is_position_on_board(capture_right_position) && !is_position_empty(board, capture_right_position) && (board.getPiece(capture_right_position).getTeamColor() != team_color)){
            // add in promotion move if it's a promotion row
            if(is_row_promotion(team_color, capture_right_position.getRow())){
                add_promotion_moves(startPosition, capture_right_position, list_of_moves);
            } // capture if no promotion
            else{
                list_of_moves.add(new ChessMove(startPosition, capture_right_position, null));
            }
        }return list_of_moves;
    }

    private boolean is_row_promotion(ChessGame.TeamColor team_color, int current_row){
        if((team_color == ChessGame.TeamColor.WHITE) && (current_row == 8)){
            return true;
        }else if((team_color == ChessGame.TeamColor.BLACK) && (current_row == 1)){
            return true;
        }else{
            return false;
        }
    }

    private void add_promotion_moves(ChessPosition startPosition, ChessPosition endPosition, Collection<ChessMove> list_of_moves){
        list_of_moves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.ROOK));
        list_of_moves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.KNIGHT));
        list_of_moves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.BISHOP));
        list_of_moves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.QUEEN));

    }

}
