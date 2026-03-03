//package chess;
//
//import java.util.ArrayList;
//import java.util.Collection;
//
//public class PawnMovesCalculator implements PieceMovesCalculator {
//    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition startPos) {
//        // initialize variables
//        var teamColor = board.getPiece(startPos).getTeamColor();
//        ChessPosition oneUpPos;
//        ChessPosition twoUpPos;
//        ChessPosition captLeftPos;
//        ChessPosition captRightPos;
//        int startRow;
//        int promotionRow;
//        // White Moves:
//        if(teamColor == ChessGame.TeamColor.WHITE) {
//            oneUpPos = new ChessPosition(startPos.getRow() + 1, startPos.getColumn());
//            twoUpPos = new ChessPosition(startPos.getRow() + 2, startPos.getColumn());
//            captLeftPos = new ChessPosition(startPos.getRow() + 1, startPos.getColumn() - 1);
//            captRightPos = new ChessPosition(startPos.getRow() + 1, startPos.getColumn() + 1);
//            startRow = 2;
//            promotionRow = 8;
//        } // Black Moves
//        else if(teamColor == ChessGame.TeamColor.BLACK) {
//            oneUpPos = new ChessPosition(startPos.getRow() - 1, startPos.getColumn());
//            twoUpPos = new ChessPosition(startPos.getRow() - 2, startPos.getColumn());
//            captLeftPos = new ChessPosition(startPos.getRow() - 1, startPos.getColumn() - 1);
//            captRightPos = new ChessPosition(startPos.getRow() - 1, startPos.getColumn() + 1);
//            startRow = 7;
//            promotionRow = 1;
//        } // Throw an exception for wrong color or null
//        else {
//            throw new RuntimeException("Team Color has not been properly set");
//        }
//        var listOfMoves = pM(teamColor, board, startRow, startPos, oneUpPos, twoUpPos, captLeftPos, captRightPos, promotionRow);
//        return listOfMoves;
//    }
//
//    public Collection<ChessMove> pM(ChessGame.TeamColor tC, ChessBoard b, int sR, ChessPosition sP, ChessPosition p1, ChessPosition p2, ChessPosition pL, ChessPosition pR, int pro){
//        Collection<ChessMove> listOfMoves = new ArrayList<ChessMove>();
//        if(isPosOnBoard(p1) && isPosEmpty(b, p1)){
//            // move up two for starting row
//            if((sP.getRow() == sR) && isPosOnBoard(p2) && isPosEmpty(b, p2)){
//                listOfMoves.add(new ChessMove(sP, p2, null)); // move up two if at starting row and if on board and empty
//            }// check for promotion row
//            if(p1.getRow() == pro){
//                addPromotionMoves(sP, p1, listOfMoves);
//            } // move up one (no promotion)
//            else{
//                listOfMoves.add(new ChessMove(sP, p1, null));
//            }
//        }// capture enemy on left: if on board, not empty, and filled with enemy
//        if(isPosOnBoard(pL) && !isPosEmpty(b, pL) && (b.getPiece(pL).getTeamColor() != tC)){
//            // add in promotion move if it's a promotion row
//            if(pL.getRow() == pro){
//                addPromotionMoves(sP, pL, listOfMoves);
//            } // capture if no promotion
//            else{
//                listOfMoves.add(new ChessMove(sP, pL, null));
//            }
//        }// capture enemy on right: if on board, not empty, and filled with enemy
//        if(isPosOnBoard(pR) && !isPosEmpty(b, pR) && (b.getPiece(pR).getTeamColor() != tC)){
//            // add in promotion move if it's a promotion row
//            if(pR.getRow() == pro){
//                addPromotionMoves(sP, pR, listOfMoves);
//            } // capture if no promotion
//            else{
//                listOfMoves.add(new ChessMove(sP, pR, null));
//            }
//        }return listOfMoves;
//    }
//
//    private void addPromotionMoves(ChessPosition startPosition, ChessPosition endPosition, Collection<ChessMove> listOfMoves){
//        listOfMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.ROOK));
//        listOfMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.KNIGHT));
//        listOfMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.BISHOP));
//        listOfMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.QUEEN));
//
//    }
//
//}


package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMovesCalculator implements PieceMovesCalculator{
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition pos){
        var teamColor = board.getPiece(pos).getTeamColor();
        Collection<ChessMove> list = new ArrayList<>();

        ChessPosition upOne = null;
        ChessPosition upTwo = null;
        ChessPosition captLeft = null;
        ChessPosition captRight = null;
        int startRow = 0;
        int promoRow = 0;

        if(board.getPiece(pos).getTeamColor() == ChessGame.TeamColor.WHITE){
            upOne = new ChessPosition(pos.getRow()+1, pos.getColumn());
            upTwo = new ChessPosition(pos.getRow()+2, pos.getColumn());
            captLeft = new ChessPosition(pos.getRow()+1, pos.getColumn()-1);
            captRight = new ChessPosition(pos.getRow()+1, pos.getColumn()+1);
            startRow = 2;
            promoRow = 8;
        }
        else if(board.getPiece(pos).getTeamColor() == ChessGame.TeamColor.BLACK){
            upOne = new ChessPosition(pos.getRow()-1, pos.getColumn());
            upTwo = new ChessPosition(pos.getRow()-2, pos.getColumn());
            captLeft = new ChessPosition(pos.getRow()-1, pos.getColumn()-1);
            captRight = new ChessPosition(pos.getRow()-1, pos.getColumn()+1);
            startRow = 7;
            promoRow = 1;
        }
        // move
        if(posOnBoard(upOne) && posEmpty(board, upOne)){
            if (pos.getRow() == startRow && posEmpty(board, upTwo)){
                list.add(new ChessMove(pos, upOne, null));
                list.add(new ChessMove(pos, upTwo, null));
            }else if (upOne.getRow() == promoRow){
                addPromoMoves(list, pos, upOne);
            }else {
                list.add(new ChessMove(pos, upOne, null));
            }
        }
        //capture left
        if(posOnBoard(captLeft) && !posEmpty(board, captLeft) && teamColor!=board.getPiece(captLeft).getTeamColor()){
            if(captLeft.getRow() == promoRow){
                addPromoMoves(list, pos, captLeft);
            }else{
                list.add(new ChessMove(pos, captLeft, null));
            }
        }
        //capture right
        if(posOnBoard(captRight) && !posEmpty(board, captRight) && teamColor!=board.getPiece(captRight).getTeamColor()){
            if(captRight.getRow() == promoRow){
                addPromoMoves(list, pos, captRight);
            }else{
                list.add(new ChessMove(pos, captRight, null));
            }
        }

        return list;
    }

    public void addPromoMoves(Collection<ChessMove> list, ChessPosition startPos, ChessPosition endPos){
        list.add(new ChessMove(startPos, endPos, ChessPiece.PieceType.ROOK));
        list.add(new ChessMove(startPos, endPos, ChessPiece.PieceType.KNIGHT));
        list.add(new ChessMove(startPos, endPos, ChessPiece.PieceType.BISHOP));
        list.add(new ChessMove(startPos, endPos, ChessPiece.PieceType.QUEEN));
    }
}