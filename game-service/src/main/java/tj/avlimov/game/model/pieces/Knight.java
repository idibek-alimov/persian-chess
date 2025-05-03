package tj.avlimov.game.model.pieces;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import tj.avlimov.game.model.game.Board;
import tj.avlimov.game.model.game.Move;
import tj.avlimov.game.model.game.Position;
import tj.avlimov.game.model.enums.Color;

@Entity
@DiscriminatorValue("KNIGHT")
public class Knight extends Piece{

    public Knight(Color color, Position position){
        super(color, position);
    }

    public Knight() {

    }

    @Override
    public boolean isValidMove(Position newPosition, Board board){
        return isValidMove(newPosition, board, true);
    }
    @Override
    public boolean isValidMove(Position newPosition, Board board, boolean checkKingSafety) {
        if(getPosition().equals(newPosition)){
            System.out.println("Moving to the same position");
        }
        if(!BoardUtils.isLShapedMove(getPosition(), newPosition)){
            System.out.println(String.format("From position %s to position %s ", getPosition().toString(), newPosition.toString()));
            System.out.println("Is not L shaped move");
        }
        if(getPosition().equals(newPosition) || !BoardUtils.isLShapedMove(getPosition(), newPosition)){
            System.out.println("Is the same position or not l shaped");
            return false;
        }
        Piece piece = board.getPiece(newPosition);

        if(piece != null && piece.getColor() == getColor()){
            System.out.println("Knight capturing comrade");
            return false;
        }
        if(checkKingSafety && BoardUtils.isKingInCheck(position, newPosition, board, getColor())){return false;}
        return true;
    }

    @Override
    public Knight copy(){
        return new Knight(this.color, this.position);
    }
    @Override
    public String getName() {
        return (color == Color.WHITE ? "W_" : "B_")  + "KN";
    }
}
