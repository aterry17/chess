package chess;

import java.util.Collection;
import java.util.List;

public interface BishopMovesCalculator extends PieceMovesCalculator{ // extends means that this file is a subinterface of PieceMovesCalculator

    default Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ChessPiece piece = board.getPiece(myPosition);
        Collection<ChessMove> list_of_moves = new Collection<ChessMove>();
        list_of_moves.add(diag_up_right(myPosition));

        return list_of_moves;
    }

    default ChessMove diag_up_right(ChessPosition currPosition) { // return the position of the move going diagonally up right
        var end_row = currPosition.getRow();
        var end_col = currPosition.getColumn();
        while (!is_position_filled(new ChessPosition(end_row, end_col))){
            if (!is_position_on_board(new ChessPosition(end_row+1, end_col+1)) {
                break;
            }
            end_row += 1;
            end_col += 1;
        }

        return new ChessMove(currPosition, new ChessPosition(end_row, end_col), null);
    }

    default boolean is_position_on_board(ChessPosition currPosition){
        // find if position coordinates are on the board
        return !(currPosition.getRow() < 1 || currPosition.getRow() > 8 || currPosition.getColumn() < 1 || currPosition.getColumn() > 8);
    }
     default boolean is_position_filled(ChessPosition currPosition){
         // find if someone else is in the position
        boolean b = is_position_on_board(currPosition);
        if (b == false) {
            throw new RuntimeException("Position is off board");
         }
        return (ChessBoard.squares != null);
     }

     default ChessGame.TeamColor get_position_color(ChessPosition currPosition){
        ChessPiece currPiece = ChessBoard.squares[currPosition.getRow()][currPosition.getColumn()];
        return currPiece.getTeamColor();
     }


}
