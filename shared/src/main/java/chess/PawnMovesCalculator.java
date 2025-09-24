package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMovesCalculator implements PieceMovesCalculator {
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition startPosition) {
        // initialize variables
        var teamColor = board.getPiece(startPosition).getTeamColor();
        ChessPosition oneUpPosition;
        ChessPosition twoUpPosition;
        ChessPosition captureLeftPosition;
        ChessPosition captureRightPosition;
        int teamStartRow;
        // White Moves:
        if(teamColor == ChessGame.TeamColor.WHITE) {
            oneUpPosition = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn());
            twoUpPosition = new ChessPosition(startPosition.getRow() + 2, startPosition.getColumn());
            captureLeftPosition = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn() - 1);
            captureRightPosition = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn() + 1);
            teamStartRow = 2;
        } // Black Moves
        else if(teamColor == ChessGame.TeamColor.BLACK) {
            oneUpPosition = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn());
            twoUpPosition = new ChessPosition(startPosition.getRow() - 2, startPosition.getColumn());
            captureLeftPosition = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn() - 1);
            captureRightPosition = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn() + 1);
            teamStartRow = 7;
        } // Throw an exception for wrong color or null
        else {
            throw new RuntimeException("Team Color has not been properly set");
        }
        var listOfMoves = pawnMoves(teamColor, board, teamStartRow, startPosition, oneUpPosition, twoUpPosition, captureLeftPosition, captureRightPosition);
        return listOfMoves;
    }

    public Collection<ChessMove> pawnMoves (ChessGame.TeamColor teamColor, ChessBoard board, int teamStartRow, ChessPosition startPosition, ChessPosition oneUpPosition, ChessPosition twoUpPosition, ChessPosition captureLeftPosition, ChessPosition captureRightPosition){
        Collection<ChessMove> listOfMoves = new ArrayList<ChessMove>();
        if(isPositionOnBoard(oneUpPosition) && isPositionEmpty(board, oneUpPosition)){
            // move up two for starting row
            if((startPosition.getRow() == teamStartRow) && isPositionOnBoard(twoUpPosition) && isPositionEmpty(board, twoUpPosition)){
                listOfMoves.add(new ChessMove(startPosition, twoUpPosition, null)); // move up two if at starting row and if on board and empty
            }// check for promotion row
            if(isRowPromotion(teamColor, oneUpPosition.getRow())){
                addPromotionMoves(startPosition, oneUpPosition, listOfMoves);
            } // move up one (no promotion)
            else{
                listOfMoves.add(new ChessMove(startPosition, oneUpPosition, null));
            }
        }// capture enemy on left: if on board, not empty, and filled with enemy
        if(isPositionOnBoard(captureLeftPosition) && !isPositionEmpty(board, captureLeftPosition) && (board.getPiece(captureLeftPosition).getTeamColor() != teamColor)){
            // add in promotion move if it's a promotion row
            if(isRowPromotion(teamColor, captureLeftPosition.getRow())){
                addPromotionMoves(startPosition, captureLeftPosition, listOfMoves);
            } // capture if no promotion
            else{
                listOfMoves.add(new ChessMove(startPosition, captureLeftPosition, null));
            }
        }// capture enemy on right: if on board, not empty, and filled with enemy
        if(isPositionOnBoard(captureRightPosition) && !isPositionEmpty(board, captureRightPosition) && (board.getPiece(captureRightPosition).getTeamColor() != teamColor)){
            // add in promotion move if it's a promotion row
            if(isRowPromotion(teamColor, captureRightPosition.getRow())){
                addPromotionMoves(startPosition, captureRightPosition, listOfMoves);
            } // capture if no promotion
            else{
                listOfMoves.add(new ChessMove(startPosition, captureRightPosition, null));
            }
        }return listOfMoves;
    }

    private boolean isRowPromotion(ChessGame.TeamColor teamColor, int currentRow){
        if((teamColor == ChessGame.TeamColor.WHITE) && (currentRow == 8)){
            return true;
        }else if((teamColor == ChessGame.TeamColor.BLACK) && (currentRow == 1)){
            return true;
        }else{
            return false;
        }
    }

    private void addPromotionMoves(ChessPosition startPosition, ChessPosition endPosition, Collection<ChessMove> listOfMoves){
        listOfMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.ROOK));
        listOfMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.KNIGHT));
        listOfMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.BISHOP));
        listOfMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.QUEEN));

    }

}
