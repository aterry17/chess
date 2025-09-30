package chess;

import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ChessPiece piece = board.getPiece(myPosition);
        Collection<ChessMove> listOfMoves = (theMovesCalculatorINeed(piece.getPieceType()).pieceMoves(board, myPosition));
        return listOfMoves;
    }

    public PieceMovesCalculator theMovesCalculatorINeed(ChessPiece.PieceType pieceType) {
        if(pieceType == PieceType.BISHOP) {
            return new BishopMovesCalculator();
        }
        else if(pieceType == PieceType.ROOK) {
            return new RookMovesCalculator();
        }
        else if(pieceType == PieceType.PAWN) {
            return new PawnMovesCalculator();
        }
        else if(pieceType == PieceType.QUEEN){
            return new QueenMovesCalculator();
        }
        else if(pieceType == PieceType.KING){
            return new KingMovesCalculator();
        }
        else if(pieceType == PieceType.KNIGHT){
            return new KnightMovesCalculator();
        }
        else {
            throw new RuntimeException("you haven't implemented the other piece types yet :(");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }

    @Override
    public String toString() {
        return "ChessPiece{" +
                "pieceColor=" + pieceColor +
                ", type=" + type +
                '}';
    }
}
