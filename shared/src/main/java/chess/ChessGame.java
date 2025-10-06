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
public class ChessGame implements Cloneable{

    private TeamColor currTeamColor;
    private ChessBoard board;
    public ChessGame() {
        currTeamColor = TeamColor.WHITE; // White moves first, so when the ChessGame is initialized it should start on white
        board = new ChessBoard();
        board.resetBoard();
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
        setTeamTurn(piece.getTeamColor());
        // return all moves which do not result in isInCheck
        Collection<ChessMove> allPossibleMoves = piece.pieceMoves(board, startPosition);
        Collection<ChessMove> validMoves = new ArrayList<>();
        for(ChessMove move: allPossibleMoves){
            ChessGame hypGame = (ChessGame) this.clone();
            try {
                hypGame.makeMove(move);
                if (!hypGame.isInCheck(currTeamColor)){
                    validMoves.add(move);
                }
            }
            catch (InvalidMoveException exception){
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
        ChessPiece piece = board.getPiece(move.getStartPosition());
        if (piece == null){
            throw new InvalidMoveException("piece is null");
        }
        Collection<ChessMove> pieceMoves = piece.pieceMoves(board, move.getStartPosition());
        if(!pieceMoves.contains(move)){
            throw new InvalidMoveException("move is not valid for piece in this position");
        }
        else if (piece.getTeamColor() != currTeamColor){
            throw new InvalidMoveException("It is not your turn :(");
        }
        else {
            ChessGame.TeamColor nextTeamTurn;
            if (piece.getTeamColor() == TeamColor.BLACK){
                nextTeamTurn = TeamColor.WHITE;
            }
            else {
                nextTeamTurn = TeamColor.BLACK;
            }
            if (move.getPromotionPiece() == null) {
                board.removePiece(move.getStartPosition(), piece);
                board.addPiece(move.getEndPosition(), piece);
                if (isInCheck(currTeamColor)) { // if you're in check --> undo the move & throw exceptions
                    board.removePiece(move.getEndPosition(), piece);
                    board.addPiece(move.getStartPosition(), piece);
                    throw new InvalidMoveException("you have to save your king!");
                }
                else{ // if you're not in check, then you didn't remove the piece and you should set the team turn
                    setTeamTurn(nextTeamTurn);
                }
            }
            else{
                board.removePiece(move.getStartPosition(), piece);
                board.addPiece(move.getEndPosition(), new ChessPiece(currTeamColor, move.getPromotionPiece()));

                if (isInCheck(currTeamColor)) { // if you're in check --> undo the move & throw exception
                    board.removePiece(move.getEndPosition(), new ChessPiece(currTeamColor, move.getPromotionPiece()));
                    board.addPiece(move.getStartPosition(), piece);
                    throw new InvalidMoveException("you have to save your king!");
                }
                else {
                    setTeamTurn(nextTeamTurn);
                }
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
        var enemyTeam = board.getTeam(enemyColor); // team = [[piece, piece, ...], [position, position, ...]]
        ArrayList<ChessPiece> enemyPieces = (ArrayList<ChessPiece>) enemyTeam.get(0);
        ArrayList<ChessPosition> enemyPositions = (ArrayList <ChessPosition>) enemyTeam.get(1);

        // arrayList.indexOf(king) -- outputs the index of the king
        var team = board.getTeam(teamColor);
        ArrayList<ChessPiece> teamPieces = (ArrayList<ChessPiece>) team.get(0);
        ArrayList<ChessPosition> teamPositions = (ArrayList<ChessPosition>) team.get(1);

        var kingPos = teamPositions.get(teamPieces.indexOf(new ChessPiece(teamColor, ChessPiece.PieceType.KING)));

//        for (var piece: enemyPieces){
          for (int i=0; i < enemyPieces.size(); i++) {
            var moves = enemyPieces.get(i).pieceMoves(board, enemyPositions.get(i));

            for (var move: moves){
                var endPos = move.getEndPosition();
                if ((endPos.getRow() == kingPos.getRow()) && (endPos.getColumn() == kingPos.getColumn())){
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
        if (!isInCheck(teamColor)){
            return false;
        }
        return noValidMoves(teamColor);
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        if (isInCheck(teamColor)){
            return false;
        }
        return noValidMoves(teamColor);
    }

    /**
     * Sets this game's chessboard with a given boar
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

    public boolean noValidMoves(ChessGame.TeamColor teamColor){
        Collection<ChessMove> moves = new ArrayList<>();
        var team = board.getTeam(teamColor);
        ArrayList<ChessPosition> teamPositions = (ArrayList<ChessPosition>) team.get(1);
        for (var pos: teamPositions){
            moves.addAll(validMoves(pos));
        }
        if (moves.isEmpty()){
            return true;
        }
        else{
            return false;
        }
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

    @Override
    protected Object clone() {
        try {
            var clone = (ChessGame) super.clone();
            clone.currTeamColor = this.currTeamColor;
            clone.board = (ChessBoard) this.board.clone();

            return clone;
        } catch (CloneNotSupportedException e){
            throw new RuntimeException(e);
        }
    }



}
