package chess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

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
            // make a copy ChessGame
            // apply the make move
            // then check isInCheck,if false, add the move to the new list
            ChessGame hypotheticalGame = this.deepCopy();

            // need to put in a try / catch block -- not sure if this is correct
            try {
                hypotheticalGame.makeMove(move);
            }
            catch (InvalidMoveException exception){
            }

            if (!hypotheticalGame.isInCheck(currTeamColor)){
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
        //if move is not a valid move for the piece at its starting loc:
            // throw InvalidMoveException
        ChessPiece piece = board.getPiece(move.getStartPosition()); // piece is null here -- not sure why
        Collection<ChessMove> pieceMoves = piece.pieceMoves(board, move.getStartPosition());
        if(!pieceMoves.contains(move)){
            throw new InvalidMoveException("move is not valid for piece in this position");
        }
        // else if it is not your team's turn
            // throw InvalidMoveException
        else if (piece.getTeamColor() != currTeamColor){
            throw new InvalidMoveException("It is not your turn :(");
        }
        // else:
            // make the move by modifying the board
        else {
            // not sure how to fit promotion piece into this
            if(move.getPromotionPiece() == null) {
                board.addPiece(move.getEndPosition(), piece);
            }
            // not sure how to do this pawn part, I don't think this is right
            // this is creating a new piece of the promotion type
            // but the piece that the pawn gets promoted to, is it a piece currently on the board, if so we need to remove that piece from its original position
            // for example, if you promote your pawn to a queen, do you now have two queens on board? or do you remove the og queen from her position
            // for a queen this wouldn't be so hard, becasue there is only 1, but for bishop or rook...?
            else{
                board.addPiece(move.getEndPosition(), new ChessPiece(currTeamColor, move.getPromotionPiece()));
            }
        }
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


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessGame chessGame = (ChessGame) o;
        return currTeamColor == chessGame.currTeamColor && Objects.equals(board, chessGame.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currTeamColor, board);
    }

    @Override
    public String toString() {
        return "ChessGame{" +
                "currTeamColor=" + currTeamColor +
                ", board=" + board +
                '}';
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
}
