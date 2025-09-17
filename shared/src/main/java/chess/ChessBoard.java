package chess;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {

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
        addPiece(new ChessPosition(1,5), new ChessPiece(white, queen));
        addPiece(new ChessPosition(8,5), new ChessPiece(black, queen));
    }



    /// My methods start here:

}
