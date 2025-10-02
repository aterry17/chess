package chess;

import java.util.ArrayList;
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
        currTeamColor = team;
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
        // find your piece
        ChessPiece piece = board.getPiece(move.getStartPosition());
        // check to see if the piece actually exists
        if (piece == null){
            throw new InvalidMoveException("piece is null");
        }
        // collect all possible moves for that piece
        Collection<ChessMove> pieceMoves = piece.pieceMoves(board, move.getStartPosition());

        if(!pieceMoves.contains(move)){
            throw new InvalidMoveException("move is not valid for piece in this position");
        }
        // else if it is not your team's turn
            // throw InvalidMoveException
        else if (piece.getTeamColor() != currTeamColor){
            throw new InvalidMoveException("It is not your turn :(");
        }

        else {
            ChessGame.TeamColor next_team_turn;
            if (piece.getTeamColor() == TeamColor.BLACK){
                next_team_turn = TeamColor.WHITE;
            }
            else {
                next_team_turn = TeamColor.BLACK;
            }

            // not sure how to fit promotion piece into this
            if (move.getPromotionPiece() == null) {
                // we also need to remove the piece from its current position
                board.removePiece(move.getStartPosition(), piece);
                board.addPiece(move.getEndPosition(), piece);
                setTeamTurn(next_team_turn);
            }
            else{
                board.removePiece(move.getStartPosition(), piece);
                board.addPiece(move.getEndPosition(), new ChessPiece(currTeamColor, move.getPromotionPiece()));
                setTeamTurn(next_team_turn);
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
        var enemyColor = TeamColor.WHITE;
        if (teamColor == TeamColor.WHITE){
            enemyColor = TeamColor.BLACK;
        }
        var enemyTeam = board.getTeam(enemyColor);
        // need to find King
        var team = board.getTeam(teamColor);
        var kingPos = team.get(new ChessPiece(TeamColor.WHITE, ChessPiece.PieceType.KING)); // is this the correct way to grab the King's position?
        for (var entry: enemyTeam.entrySet()){
            // piece:position
            var moves = entry.getKey().pieceMoves(board, entry.getValue());
            for (var move: moves){
                if (move.getEndPosition() == kingPos){
                    return true;
                }
            }
        }
        return false;
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
        if (!isInCheck(teamColor)){ // king is safe
            var team = board.getTeam(teamColor); // map of piece:position
//            // using pieceMoves
//            for (var entry: team.entrySet()){
//                // getKey = piece, getValue = position, .pieceMoves returns an ArrayList --> if ArrayList is not empty we want to return false
//                if (entry.getKey().pieceMoves(board, entry.getValue()).size() != 0) {
//                    return false; // pieceMoves ArrayList was not size 0
//                }
//            }
            // trying to use validMoves instead of pieceMoves
            for (var pos: team.values()){
                // getKey = piece, getValue = position, .pieceMoves returns an ArrayList --> if ArrayList is not empty we want to return false
                if (validMoves(pos).size() != 0) {
                    return false; // pieceMoves ArrayList was not size 0
                }
            }
            return true; // all pieceMoves lists were of size 0 --> we can't move
        }
        else {
            return false; // king is not safe
        }
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
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
