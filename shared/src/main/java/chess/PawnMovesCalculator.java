package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMovesCalculator implements PieceMovesCalculator {
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition startPos) {
        // initialize variables
        var teamColor = board.getPiece(startPos).getTeamColor();
        ChessPosition oneUpPos;
        ChessPosition twoUpPos;
        ChessPosition captLeftPos;
        ChessPosition captRightPos;
        int startRow;
        // White Moves:
        if(teamColor == ChessGame.TeamColor.WHITE) {
            oneUpPos = new ChessPosition(startPos.getRow() + 1, startPos.getColumn());
            twoUpPos = new ChessPosition(startPos.getRow() + 2, startPos.getColumn());
            captLeftPos = new ChessPosition(startPos.getRow() + 1, startPos.getColumn() - 1);
            captRightPos = new ChessPosition(startPos.getRow() + 1, startPos.getColumn() + 1);
            startRow = 2;
        } // Black Moves
        else if(teamColor == ChessGame.TeamColor.BLACK) {
            oneUpPos = new ChessPosition(startPos.getRow() - 1, startPos.getColumn());
            twoUpPos = new ChessPosition(startPos.getRow() - 2, startPos.getColumn());
            captLeftPos = new ChessPosition(startPos.getRow() - 1, startPos.getColumn() - 1);
            captRightPos = new ChessPosition(startPos.getRow() - 1, startPos.getColumn() + 1);
            startRow = 7;
        } // Throw an exception for wrong color or null
        else {
            throw new RuntimeException("Team Color has not been properly set");
        }
        var listOfMoves = pawnMoves(teamColor, board, startRow, startPos, oneUpPos, twoUpPos, captLeftPos, captRightPos);
        return listOfMoves;
    }

    public Collection<ChessMove> pawnMoves (ChessGame.TeamColor tC, ChessBoard b, int sR, ChessPosition sP, ChessPosition p1, ChessPosition p2, ChessPosition pL, ChessPosition pR){
        Collection<ChessMove> listOfMoves = new ArrayList<ChessMove>();
        if(isPosOnBoard(p1) && isPosEmpty(b, p1)){
            // move up two for starting row
            if((sP.getRow() == sR) && isPosOnBoard(p2) && isPosEmpty(b, p2)){
                listOfMoves.add(new ChessMove(sP, p2, null)); // move up two if at starting row and if on board and empty
            }// check for promotion row
            if(isRowPromotion(tC, p1.getRow())){
                addPromotionMoves(sP, p1, listOfMoves);
            } // move up one (no promotion)
            else{
                listOfMoves.add(new ChessMove(sP, p1, null));
            }
        }// capture enemy on left: if on board, not empty, and filled with enemy
        if(isPosOnBoard(pL) && !isPosEmpty(b, pL) && (b.getPiece(pL).getTeamColor() != tC)){
            // add in promotion move if it's a promotion row
            if(isRowPromotion(tC, pL.getRow())){
                addPromotionMoves(sP, pL, listOfMoves);
            } // capture if no promotion
            else{
                listOfMoves.add(new ChessMove(sP, pL, null));
            }
        }// capture enemy on right: if on board, not empty, and filled with enemy
        if(isPosOnBoard(pR) && !isPosEmpty(b, pR) && (b.getPiece(pR).getTeamColor() != tC)){
            // add in promotion move if it's a promotion row
            if(isRowPromotion(tC, pR.getRow())){
                addPromotionMoves(sP, pR, listOfMoves);
            } // capture if no promotion
            else{
                listOfMoves.add(new ChessMove(sP, pR, null));
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
