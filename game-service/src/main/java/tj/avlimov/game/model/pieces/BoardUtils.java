package tj.avlimov.game.model.pieces;

import tj.avlimov.game.model.game.Board;
import tj.avlimov.game.model.game.Move;
import tj.avlimov.game.model.game.Position;
import tj.avlimov.game.model.enums.Color;

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
}
