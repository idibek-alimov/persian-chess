package tj.avlimov.game.utils;

import tj.avlimov.game.dto.board.BoardDTO;
import tj.avlimov.game.dto.board.PieceDTO;
import tj.avlimov.game.model.game.Board;
import tj.avlimov.game.model.game.Move;
import tj.avlimov.game.model.game.Position;
import tj.avlimov.game.model.enums.Color;
import tj.avlimov.game.model.pieces.*;

public class BoardUtils {
    public static boolean isPathClear(Board board, Position from, Position to){
        if(from.equals(to)) return false;

        int xDiff = to.getX() - from.getX();
        int yDiff = to.getY() - from.getY();
        int steps = Math.max(Math.abs(xDiff), Math.abs(yDiff));
        int xStep = Integer.signum(xDiff);
        int yStep = Integer.signum(yDiff);

        for(int i=1;i<steps;i++){
            Position pos = new Position(
                    from.getX() + xStep*i,
                    to.getY() + yStep*i
            );
            if(board.getPiece(pos) != null){
                return false;
            }
        }
        return true;
    }
    public static boolean isDiagonalMove(Position from, Position to){
        return Math.abs(from.getX() - to.getX()) == Math.abs(from.getY() - to.getY());
    }

    public static boolean isStraightMove(Position from, Position to){
        return from.getX() == to.getX() || from.getY() == to.getY();
    }

    public static boolean isLShapedMove(Position from, Position to){
        int xDis = Math.abs(from.getX() - to.getX());
        int yDis = Math.abs(from.getY() - to.getY());

        return (xDis == 2 && yDis == 1)||(xDis == 1 && yDis == 2);
    }
    public static boolean isKingMove(Position from, Position to){
        int xDis = Math.abs(from.getX() - to.getX());
        int yDis = Math.abs(from.getY() - to.getY());
        return xDis + yDis <= 2;

    }
    public static boolean isKingInCheck(Position piecePosition, Position newPosition ,Board board, Color color){
        Board copyBoard = board.copy();
        Piece movingPiece = copyBoard.getPiece(piecePosition);
        Move move = new Move( movingPiece, newPosition, copyBoard);
        System.out.println(copyBoard.getBoardString());
        copyBoard.makeMove(move);

        Position kingPosition = copyBoard.findKingPosition(color);
        System.out.println("King position = " + kingPosition.toString());
        Color opponentColor = color == Color.BLACK ? Color.WHITE : Color.BLACK;
        for(Piece piece: copyBoard.getPieces()){
            if(piece.getColor() == opponentColor && piece.isValidMove(kingPosition, copyBoard, false)){
                System.out.println(String.format("Piece at position %s can reach the king ", piece.getPosition().toString()));
                return true;
            }
        }
        return false;
    }

    public static void initializeChessPieces(Board board){

        // Rooks
        board.placePiece(new Rook(Color.WHITE, new Position(0,0)));
        board.placePiece(new Rook(Color.WHITE, new Position(7,0)));
        board.placePiece(new Rook(Color.BLACK, new Position(0,7)));
        board.placePiece(new Rook(Color.BLACK, new Position(7,7)));

        // Knights
        board.placePiece(new Knight(Color.WHITE, new Position(1,0)));
        board.placePiece(new Knight(Color.WHITE, new Position(6,0)));
        board.placePiece(new Knight(Color.BLACK, new Position(1,7)));
        board.placePiece(new Knight(Color.BLACK, new Position(6,7)));

        // Bishops
        board.placePiece(new Bishop(Color.WHITE, new Position(2,0)));
        board.placePiece(new Bishop(Color.WHITE, new Position(5,0)));
        board.placePiece(new Bishop(Color.BLACK, new Position(2,7)));
        board.placePiece(new Bishop(Color.BLACK, new Position(5,7)));

        // Queens
        board.placePiece(new Queen(Color.WHITE, new Position(3, 0)));
        board.placePiece(new Queen(Color.BLACK, new Position(4,7)));

        // Kings
        board.placePiece(new King(Color.WHITE, new Position(4, 0)));
        board.placePiece(new King(Color.BLACK, new Position(3, 7)));

        // Pawns
        for(int i=0;i<8;i++){
            board.placePiece(new Pawn(Color.WHITE, new Position(i,1)));
            board.placePiece(new Pawn(Color.BLACK, new Position(i,6)));
        }
    }

    public static BoardDTO toDTO(Board board){
        return new BoardDTO(
                board.getId(),
                board.getPieces().stream()
                        .filter(p -> p.getPosition() != null)
                        .map(p -> new PieceDTO(
                                p.getClass().getSimpleName().toUpperCase(),
                                p.getColor(),
                                p.getPosition(),
                                false
                        ))
                        .toList(),
                board.getGame().getCurrentPlayerColor(),
                board.getGame().getCurrentStateType()
        );
    }

    public static String getBoardString(Board board){
        StringBuilder sb = new StringBuilder(" ");
        for(int i=0;i<8;i++){
            sb.append("   " + i + "   ");
        }
        sb.append(System.getProperty("line.separator"));

        for(int y=0; y<8;y++){
            sb.append(" " + y + " ");
            for(int x=0; x<8;x++){
                Position position = new Position(x, y);
                Piece piece = board.getPiece(position);
                if(piece != null){
                    sb.append(" "+piece.getName()+"  ");
                }
                else{
                    sb.append("   .   ");
                }
            }
            sb.append(System.getProperty("line.separator"));
        }

        return sb.toString();
    }
}
