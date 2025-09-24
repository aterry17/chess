package chess;

import java.util.Collection;

public interface PieceMovesCalculator {

    Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition);
    default boolean isPositionOnBoard(ChessPosition currPosition){
        return ((currPosition.getRow() >= 1) && (currPosition.getRow() <= 8) && (currPosition.getColumn() >=1) && (currPosition.getColumn() <= 8));
    }
    default boolean isPositionEmpty(ChessBoard board, ChessPosition currPosition){
        if (!isPositionOnBoard(currPosition)) {
            throw new RuntimeException("Position is off board");
        }
        return (board.getPiece(currPosition) == null);
    }

    default void continuousMove(ChessBoard board, Collection<ChessMove> listOfMoves, int[][] directionsArray, ChessPosition startPosition) {
        for (var direction : directionsArray) {
            var teamColor = board.getPiece(startPosition).getTeamColor();
            var currPosition = new ChessPosition(startPosition.getRow() + direction[0], startPosition.getColumn() + direction[1]);
            while (isPositionOnBoard(currPosition) && isPositionEmpty(board, currPosition)) {
                listOfMoves.add(new ChessMove(startPosition, currPosition, null));
                currPosition = new ChessPosition(currPosition.getRow() + direction[0], currPosition.getColumn() + direction[1]);
            }
            var potentialEnemyPosition = currPosition;
            if (isPositionOnBoard(potentialEnemyPosition) && (teamColor != board.getPiece(potentialEnemyPosition).getTeamColor())) {
                listOfMoves.add(new ChessMove(startPosition, potentialEnemyPosition, null));
            }
        }
    }

    default void stepMove(ChessBoard board, Collection<ChessMove> listOfMoves, int[][] directionsArray, ChessPosition startPosition){
        var teamColor = board.getPiece(startPosition).getTeamColor();
        for (var direction : directionsArray) {
            var currPosition = new ChessPosition(startPosition.getRow() + direction[0], startPosition.getColumn() + direction[1]);
            // move to an empty space
            if(isPositionOnBoard(currPosition) && isPositionEmpty(board, currPosition)) {
                listOfMoves.add(new ChessMove(startPosition, currPosition, null));
                currPosition = new ChessPosition(currPosition.getRow() + direction[0], currPosition.getColumn() + direction[1]);
            }
            // move & capture enemy
            if(isPositionOnBoard(currPosition) && !isPositionEmpty(board, currPosition) && (teamColor != board.getPiece(currPosition).getTeamColor())){
                listOfMoves.add(new ChessMove(startPosition, currPosition, null));

            }
        }
    }
}
