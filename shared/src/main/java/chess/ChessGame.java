package chess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private TeamColor currTeamColor;
    private ChessBoard board;
    public ChessGame() {
        currTeamColor = TeamColor.WHITE; // White moves first, so when the ChessGame is initialized it should start on white
        board = new ChessBoard();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return currTeamColor;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        // this should set the team color after the turn is made, e.g white makes move, then setTeamTurn changes to black
        if (currTeamColor == TeamColor.WHITE){
            currTeamColor = TeamColor.BLACK;
        }
        else {
            currTeamColor = TeamColor.WHITE;
        }
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        // return null if there is no piece
        ChessPiece piece = board.getPiece(startPosition);
        if (piece == null){
            return null;
        }
        // return all moves which do not result in isInCheck
        Collection<ChessMove> allPossibleMoves = piece.pieceMoves(board, startPosition);
        Collection<ChessMove> validMoves = new ArrayList<>();

        for(ChessMove move: allPossibleMoves){
            // make a new ChessBoard that is a copy, apply the move to the copy and check for kingWillBeSafe
            ChessBoard copyBoard = new ChessBoard(board); // utilizing the deep copy method we just implemented

            if (kingWillBeSafe(copyBoard, currTeamColor, move)){
                validMoves.add(move);
            }

        }
        return validMoves;

    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        return true;
        // modify this later
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        return false;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        return false;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        board.resetBoard();
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }


    /**
     * adding in a deep copy method for ChessGame
     */
    public ChessGame deepCopy(){
        ChessGame copy = new ChessGame();
        copy.currTeamColor = this.currTeamColor;
        copy.board = this.board;
        return copy;
    }




    /**
     * @param board
     * @param color
     * @param move
     * @return True if the proposed move would keep the king safe
     */
    private boolean kingWillBeSafe(ChessBoard board, ChessGame.TeamColor color, ChessMove move){

        return true;
    }




    // override the equals and hascode methods


}
