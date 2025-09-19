package chess;

import java.util.Collection;
import java.util.ArrayList;

public class QueenMovesCalculator implements PieceMovesCalculator { // extends means that this file is a subinterface of PieceMovesCalculator

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition startPosition) {
        Collection<ChessMove> list_of_moves = new ArrayList<ChessMove>();

        var team_color = board.getPiece(startPosition).getTeamColor();

        // get all of the directions into an array
        int[][] directions_array = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}, {1,0}, {-1,0}, {0,1}, {0,-1}};

        // cycle through the directions
        for (var direction : directions_array) {
            var curr_position = new ChessPosition(startPosition.getRow() + direction[0], startPosition.getColumn() + direction[1]);
            while (is_position_on_board(curr_position) && is_position_empty(board, curr_position)) {
                list_of_moves.add(new ChessMove(startPosition, curr_position, null));
                curr_position = new ChessPosition(curr_position.getRow() + direction[0], curr_position.getColumn() + direction[1]);
            }
            // since the while loop broke, either we're off the board or we ran into another player
            // make the capture enemy move here
            var potential_enemy_position = curr_position;
            // make sure it's actually on the board
            if (!is_position_on_board(potential_enemy_position))
                continue; // continue is unecessary because there's no more code outside of the is / else if / else block -- but as soon as you add code beneath the block, the continue becomes very necessary
                // check the color
            else if (team_color == board.getPiece(potential_enemy_position).getTeamColor()) continue;
                // assuming that everything is doing what I think it's doing, we should know herre that we
            else {
                list_of_moves.add(new ChessMove(startPosition, potential_enemy_position, null));
            }
        }

        return list_of_moves;
    }
}