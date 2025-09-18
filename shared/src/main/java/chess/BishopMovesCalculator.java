package chess;

import java.util.Collection;
import java.util.ArrayList;

public class BishopMovesCalculator implements PieceMovesCalculator{ // extends means that this file is a subinterface of PieceMovesCalculator

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition startPosition) {
        //ChessPiece piece = board.getPiece(myPosition);
        Collection<ChessMove> list_of_moves = new ArrayList<ChessMove>();
        // list_of_moves.add(diag_up_right(board, myPosition));

        var team_color = board.getPiece(startPosition).getTeamColor();

        // get all of the directions into an array
        int[][] directions_array = {{1,1}, {1,-1}, {-1,1}, {-1,-1}};

        // trying one set of nested loops for all of the directions instead of diag_up_right & diag_up_left & etc.
        for(var direction: directions_array){
            var curr_position = new ChessPosition(startPosition.getRow() + direction[0], startPosition.getColumn() + direction[1]);
            while(is_position_on_board(curr_position) && is_position_empty(board, curr_position)){
                list_of_moves.add(new ChessMove(startPosition, curr_position, null));
                curr_position = new ChessPosition(curr_position.getRow() + direction[0], curr_position.getColumn() + direction[1]);
            }
            // since the while loop broke, either we're off the board or we ran into another player
            // make the capture enemy move here
            var potential_enemy_position = curr_position;
            // make sure it's actually on the board
            if (!is_position_on_board(potential_enemy_position)) continue; // continue is unecessary because there's no more code outside of the is / else if / else block -- but as soon as you add code beneath the block, the continue becomes very necessary
            // check the color
            else if(team_color == board.getPiece(potential_enemy_position).getTeamColor()) continue;
            // assuming that everything is doing what I think it's doing, we should know herre that we
            else {
                list_of_moves.add(new ChessMove(startPosition, potential_enemy_position, null));
            }
        }

        return list_of_moves;
    }


/// Trying a new thing above, doing all of the moves at once instead of writing a new function for each
    // maybe make a new function called capture enemy to call inside of each diag_up_right, diag_up_left, etc.
//    private ChessMove diag_up_right(ChessBoard board, ChessPosition startPosition) { // return the position of the move going diagonally up right
//        var end_row = startPosition.getRow();
//        var end_col = startPosition.getColumn();
//        var team_color = board.getPiece(startPosition).getTeamColor();
//
//        while(is_position_on_board(new ChessPosition(end_row+1, end_col+1)) && is_position_empty(board, new ChessPosition(end_row+1, end_col+1))) {
//            end_row += 1;
//            end_col += 1;
//        }
//        // the capture enemy move returns the final position even if an enemy is not captured
//        var finalPosition = capture_enemy_move(board, team_color, end_row, end_col);
//        return new ChessMove(startPosition, finalPosition, null);
//    }

///  commenting these out to see if it works to just have them implemented in piecemovescalculator --> it seems to be working so far

//    public boolean is_position_on_board(ChessPosition currPosition){
//        // find if position coordinates are on the board
//        return ((currPosition.getRow() >= 1) && (currPosition.getRow() <= 8) && (currPosition.getColumn() >=1) && (currPosition.getColumn() <= 8));
//    }
//    public boolean is_position_empty(ChessBoard board, ChessPosition currPosition){
//         // find if someone else is in the position
//        if (!is_position_on_board(currPosition)) {
//            throw new RuntimeException("Position is off board");
//         }
//        return (board.getPiece(currPosition) == null);
//     }

    //________________________________________________________________________________

//
//     public ChessPosition capture_enemy_move(ChessBoard board, ChessGame.TeamColor team_color, int curr_row, int curr_col) { // maybe put this in piecemovescalc (but implement it here)
//        var final_position = new ChessPosition(curr_row, curr_col);
//        var next_up_position = new ChessPosition(curr_row+1, curr_col+1);
//
//        boolean pos_on_board = is_position_on_board(next_up_position);
//
//         boolean piece_in_pos = (board.getPiece(next_up_position) != null);
//         boolean enemy_piece = false;
//
//         if (piece_in_pos) { // here to avoid NullPointerException
//             enemy_piece = (team_color != board.getPiece(next_up_position).getTeamColor());
//         }
//
//         if (pos_on_board && piece_in_pos && enemy_piece){
//             return next_up_position;
//         }
//         else {
//             return final_position;
//         }
//
//     }


}
