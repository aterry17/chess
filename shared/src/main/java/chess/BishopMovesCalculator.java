package chess;

import java.util.Collection;
import java.util.ArrayList;

public class BishopMovesCalculator implements PieceMovesCalculator{ // extends means that this file is a subinterface of PieceMovesCalculator

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        //ChessPiece piece = board.getPiece(myPosition);
        Collection<ChessMove> list_of_moves = new ArrayList<ChessMove>();
        list_of_moves.add(diag_up_right(board, myPosition));

        return list_of_moves;
    }

    // maybe make a new function called capture enemy to call inside of each diag_up_right, diag_up_left, etc.
    private ChessMove diag_up_right(ChessBoard board, ChessPosition startPosition) { // return the position of the move going diagonally up right
        var end_row = startPosition.getRow();
        var end_col = startPosition.getColumn();
        var team_color = board.getPiece(startPosition).getTeamColor();

        while(is_position_on_board(new ChessPosition(end_row+1, end_col+1)) && is_position_empty(board, new ChessPosition(end_row+1, end_col+1))) {
            end_row += 1;
            end_col += 1;
        }
        // the capture enemy move returns the final position even if an enemy is not captured
        var finalPosition = capture_enemy_move(board, team_color, end_row, end_col);
        return new ChessMove(startPosition, finalPosition, null);
    }

    public boolean is_position_on_board(ChessPosition currPosition){
        // find if position coordinates are on the board
        return !(currPosition.getRow() < 1 || currPosition.getRow() > 8 || currPosition.getColumn() < 1 || currPosition.getColumn() > 8);
    }
    public boolean is_position_empty(ChessBoard board, ChessPosition currPosition){
         // find if someone else is in the position
        if (! is_position_on_board(currPosition)) {
            throw new RuntimeException("Position is off board");
         }
        return (board.getPiece(currPosition) == null);
     }

     public ChessPosition capture_enemy_move(ChessBoard board, ChessGame.TeamColor team_color, int curr_row, int curr_col) {
        var final_position = new ChessPosition(curr_row, curr_col);
        var next_up_position = new ChessPosition(curr_row+1, curr_col+1);

        boolean pos_on_board = is_position_on_board(next_up_position);

         boolean piece_in_pos = (board.getPiece(next_up_position) != null);
         boolean enemy_piece = false;

         if (piece_in_pos) { // here to avoid NullPointerException
             enemy_piece = (team_color != board.getPiece(next_up_position).getTeamColor());
         }

         if (pos_on_board && piece_in_pos && enemy_piece){
             return next_up_position;
         }
         else {
             return final_position;
         }

     }


}
