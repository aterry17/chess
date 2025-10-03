package chess;


import java.sql.Array;
import java.util.*;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard implements Cloneable{

    private ChessPiece[][] squares = new ChessPiece[8][8];
    public ChessBoard() {}

    /**
     * Adds a chess piece to the chessboard
     *
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        squares[position.getRow()-1][position.getColumn()-1] = piece; // arrays are 0-based but moves are 1-based so sub 1
    }

    // adding this in here to make the ChessGame makeMove method work :)
    public void removePiece(ChessPosition position, ChessPiece piece){
        squares[position.getRow()-1][position.getColumn()-1] = null;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */

    public ChessPiece getPiece(ChessPosition position) {
        return squares[position.getRow()-1][position.getColumn()-1];
    }

    // make a new function to return all pieces of one color
    public ArrayList<List<?>> getTeam(ChessGame.TeamColor teamColor){
//        var team = new LinkedHashMap<ChessPiece, ChessPosition>();
        // hashmap isn't working because we're replacing values (positions) for the same key, instead of duplicating the key
        // could try using a hashmap with ChessPiece --> set(ChessPosition)

        // also could try making two separate arrayLists -- pieces & positions w/ team = ArrayList(pieces, positions)
        ArrayList<List<?>> team = new ArrayList<>();
        ArrayList<ChessPiece> pieces = new ArrayList<>();
        ArrayList<ChessPosition> positions = new ArrayList<>();

        for (int i=1; i<=8; i++){
            for (int j=1; j<=8; j++){
                var currPiece = this.getPiece(new ChessPosition(i, j));
                if (currPiece == null){
                    continue;
                }
                if (currPiece.getTeamColor() == teamColor){
                    pieces.add(currPiece);
                    positions.add(new ChessPosition(i, j));
//                    team.put(currPiece, new ChessPosition(i, j));
                }
            }
        }
        team.add(pieces);
        team.add(positions);
        return team; // team = [[piece, piece, ...], [position, position, ...]]
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        squares = new ChessPiece[8][8];
        var white = ChessGame.TeamColor.WHITE;
        var black = ChessGame.TeamColor.BLACK;
        var king = ChessPiece.PieceType.KING;
        var queen = ChessPiece.PieceType.QUEEN;
        var bishop = ChessPiece.PieceType.BISHOP;
        var knight = ChessPiece.PieceType.KNIGHT;
        var rook = ChessPiece.PieceType.ROOK;
        var pawn = ChessPiece.PieceType.PAWN;

        // PAWNS
        for (int i=1; i<=8; i++) {
            addPiece(new ChessPosition(2, i), new ChessPiece(white, pawn));
            addPiece(new ChessPosition(7,i), new ChessPiece(black, pawn));
        }

        // ROOKS
        addPiece(new ChessPosition(1,1), new ChessPiece(white, rook));
        addPiece(new ChessPosition(1,8), new ChessPiece(white, rook));
        addPiece(new ChessPosition(8,1), new ChessPiece(black, rook));
        addPiece(new ChessPosition(8,8), new ChessPiece(black, rook));

        // KNIGHTS
        addPiece(new ChessPosition(1,2), new ChessPiece(white, knight));
        addPiece(new ChessPosition(1,7), new ChessPiece(white, knight));
        addPiece(new ChessPosition(8,2), new ChessPiece(black, knight));
        addPiece(new ChessPosition(8,7), new ChessPiece(black, knight));

        // BISHOPS
        addPiece(new ChessPosition(1,3), new ChessPiece(white, bishop));
        addPiece(new ChessPosition(1,6), new ChessPiece(white, bishop));
        addPiece(new ChessPosition(8,3), new ChessPiece(black, bishop));
        addPiece(new ChessPosition(8,6), new ChessPiece(black, bishop));

        // QUEENS
        addPiece(new ChessPosition(1,4), new ChessPiece(white, queen));
        addPiece(new ChessPosition(8,4), new ChessPiece(black, queen));

        //KINGS
        addPiece(new ChessPosition(1,5), new ChessPiece(white, king));
        addPiece(new ChessPosition(8,5), new ChessPiece(black, king));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessBoard that = (ChessBoard) o;
        return Objects.deepEquals(squares, that.squares);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(squares);
    }

    @Override
    public String toString() {
        return "ChessBoard{" +
                "squares=" + Arrays.toString(squares) +
                '}';
    }


    @Override
    protected Object clone() {
        try {
            var clone = (ChessBoard) super.clone();

            var cloneSquares = new ChessPiece[8][8];
            for (int i = 0; i < 8; i++) {
                cloneSquares[i] = Arrays.copyOf(squares[i], squares[i].length);
            }
            clone.squares = cloneSquares;
            return clone;
        } catch (CloneNotSupportedException e){
            throw new RuntimeException(e);
        }
    }
}
