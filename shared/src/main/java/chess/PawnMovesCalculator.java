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