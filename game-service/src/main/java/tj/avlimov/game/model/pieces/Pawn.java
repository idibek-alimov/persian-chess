package tj.avlimov.game.model.pieces;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import tj.avlimov.game.model.game.Board;
import tj.avlimov.game.model.game.Position;
import tj.avlimov.game.model.enums.Color;
import tj.avlimov.game.model.game.Move;
import tj.avlimov.game.utils.BoardUtils;

@Entity
@AllArgsConstructor
@DiscriminatorValue("PAWN")
public class Pawn extends Piece{

    public Pawn(Color color, Position position){
        super(color, position);
    }
    @Override
    public boolean isValidMove(Position newPosition, Board board){
        return isValidMove(newPosition, board, true);
    }
    @Override
    public boolean isValidMove(Position newPosition, Board board, boolean checkKingSafety){
        System.out.println("Pawn Is move valid called");
        int direction = getColor() == Color.WHITE ? 1 : -1;
        int startRow = getColor() == Color.WHITE ? 1 : 6;
        Position currentPos = getPosition();

        if(newPosition.getX() == currentPos.getX()){
            if(newPosition.getY() == currentPos.getY() + direction){
                return !board.isOccupied(newPosition);
            }
            else if(newPosition.getY() == currentPos.getY() + 2*direction && currentPos.getY() == startRow){
                Position intermediate = new Position(currentPos.getX(), currentPos.getY() + direction);

                return !board.isOccupied(intermediate) && !board.isOccupied(newPosition);
            }
            return false;
        }

        // Capture
        if(Math.abs(newPosition.getX() - currentPos.getX()) == 1
                && newPosition.getY() == currentPos.getY() + direction ){
            if(board.isOccupiedByOpponent(newPosition, getColor())){
                return true;
            }

            Move lastMove = board.getLastMove();
            if(lastMove != null && isEnPassantPossible(lastMove, currentPos)){
                return true;
            }
        }
        if(checkKingSafety && BoardUtils.isKingInCheck(position, newPosition, board, getColor())){return false;}
        return false;
    }

    private boolean isEnPassantPossible(Move lastMove, Position currentPos){
        if(!(lastMove.getPiece() instanceof Pawn)){
            return false;
        }
        if(Math.abs(lastMove.getFrom().getY() - lastMove.getTo().getY()) != 2){
            return false;
        }
        if(Math.abs(lastMove.getTo().getX() - currentPos.getX()) != 1){
            return false;
        }
        return lastMove.getTo().getY() == currentPos.getY();
    }

    @Override
    public Piece copy(){
        return new Pawn(this.color, this.position);
    }
    @Override
    public String getName(){
        return (color == Color.WHITE ? "W_" : "B_")  + "PW";
    }
}
