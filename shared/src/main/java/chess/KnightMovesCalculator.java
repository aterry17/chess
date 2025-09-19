package chess;

import java.util.Collection;
import java.util.ArrayList;

public class KnightMovesCalculator implements PieceMovesCalculator { // extends means that this file is a subinterface of PieceMovesCalculator

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition startPosition) {
        Collection<ChessMove> list_of_moves = new ArrayList<ChessMove>();

        var team_color = board.getPiece(startPosition).getTeamColor();

        // get all of the directions into an array
        int[][] directions_array = {{1,2}, {1,-2}, {-1,2}, {-1,-2}, {2,1}, {-2,1}, {2,-1}, {-2,-1}};

        // cycle through the directions
        for (var direction : directions_array) {
            var curr_position = new ChessPosition(startPosition.getRow() + direction[0], startPosition.getColumn() + direction[1]);

            // move to an empty space
            if(is_position_on_board(curr_position) && is_position_empty(board, curr_position)) {
                list_of_moves.add(new ChessMove(startPosition, curr_position, null));
                curr_position = new ChessPosition(curr_position.getRow() + direction[0], curr_position.getColumn() + direction[1]);
            }

            // move & capture enemy
            if(is_position_on_board(curr_position) && !is_position_empty(board, curr_position) && (team_color != board.getPiece(curr_position).getTeamColor())){
                list_of_moves.add(new ChessMove(startPosition, curr_position, null));

            }
        }
        return list_of_moves;
    }
}