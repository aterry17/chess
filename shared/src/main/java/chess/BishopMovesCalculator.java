package chess;

import java.util.Collection;
import java.util.ArrayList;

public class BishopMovesCalculator implements PieceMovesCalculator{ // extends means that this file is a subinterface of PieceMovesCalculator

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ChessPiece piece = board.getPiece(myPosition);
        Collection<ChessMove> list_of_moves = new ArrayList<ChessMove>();
        list_of_moves.add(diag_up_right(board, myPosition));

        return list_of_moves;
    }

    private ChessMove diag_up_right(ChessBoard board, ChessPosition startPosition) { // return the position of the move going diagonally up right
        var end_row = startPosition.getRow();
        var end_col = startPosition.getColumn();
        while (!is_position_filled(board, new ChessPosition(end_row, end_col))){
            if (!is_position_on_board(new ChessPosition(end_row+1, end_col+1))) {
                break;
            }
            end_row += 1;
            end_col += 1;
        }
        ChessPosition next_up_position = new ChessPosition(end_row+1, end_col+1);

        if (board.getPiece(startPosition).getTeamColor() != board.getPiece(next_up_position).getTeamColor()){ // checking for enemy color
            end_row += 1;
            end_col += 1;
        }

        return new ChessMove(startPosition, new ChessPosition(end_row, end_col), null);
    }

    public boolean is_position_on_board(ChessPosition currPosition){
        // find if position coordinates are on the board
        return !(currPosition.getRow() < 1 || currPosition.getRow() > 8 || currPosition.getColumn() < 1 || currPosition.getColumn() > 8);
    }
     public boolean is_position_filled(ChessBoard board, ChessPosition currPosition){
         // find if someone else is in the position
        if (! is_position_on_board(currPosition)) {
            throw new RuntimeException("Position is off board");
         }
        return (board.getPiece(currPosition) != null);
     }

     public ChessGame.TeamColor get_position_color(ChessBoard board, ChessPosition currPosition){
        return board.getPiece(currPosition).getTeamColor();
     }


}
