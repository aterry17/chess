package chess;

import java.util.Collection;

public interface PieceMovesCalculator {

    Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition);
    default boolean isPosOnBoard(ChessPosition currPos){
        return ((currPos.getRow() >= 1) && (currPos.getRow() <= 8) && (currPos.getColumn() >=1) && (currPos.getColumn() <= 8));
    }
    default boolean isPosEmpty(ChessBoard board, ChessPosition currPos){
        if (!isPosOnBoard(currPos)) {
            throw new RuntimeException("Position is off board");
        }
        return (board.getPiece(currPos) == null);
    }

    default void contMove(ChessBoard board, Collection<ChessMove> listOfMoves, int[][] dirArr, ChessPosition startPos) {
        for (var dir : dirArr) {
            var teamColor = board.getPiece(startPos).getTeamColor();
            var currPos = new ChessPosition(startPos.getRow() + dir[0], startPos.getColumn() + dir[1]);
            while (onBoardEmpty(board, currPos)) {
                listOfMoves.add(new ChessMove(startPos, currPos, null));
                currPos = new ChessPosition(currPos.getRow() + dir[0], currPos.getColumn() + dir[1]);
            }
            if (isPosOnBoard(currPos) && (teamColor != board.getPiece(currPos).getTeamColor())) {
                listOfMoves.add(new ChessMove(startPos, currPos, null));
            }
        }
    }

    default void stepMove(ChessBoard board, Collection<ChessMove> listOfMoves, int[][] dirArr, ChessPosition startPos){
        var teamColor = board.getPiece(startPos).getTeamColor();
        for (var dir : dirArr) {
            var currPos = new ChessPosition(startPos.getRow() + dir[0], startPos.getColumn() + dir[1]);
            // move to an empty space
            if(onBoardEmpty(board, currPos)) {
                listOfMoves.add(new ChessMove(startPos, currPos, null));
                currPos = new ChessPosition(currPos.getRow() + dir[0], currPos.getColumn() + dir[1]);
            }
            // move & capture enemy
            if(isPosOnBoard(currPos) && !isPosEmpty(board, currPos) && (teamColor != board.getPiece(currPos).getTeamColor())){
                listOfMoves.add(new ChessMove(startPos, currPos, null));

            }
        }
    }

    default boolean onBoardEmpty(ChessBoard board, ChessPosition currPosition){
        return (isPosOnBoard(currPosition) && isPosEmpty(board, currPosition));
    }
}
