package tj.avlimov.game.model.pieces;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import tj.avlimov.game.model.game.Board;
import tj.avlimov.game.model.game.Move;
import tj.avlimov.game.model.game.Position;
import tj.avlimov.game.model.enums.Color;

@Data
@Entity
@DiscriminatorValue("ROOK")
public class Rook extends Piece{

    public Rook(Color color, Position position){
        super(color, position);
    }

    public Rook() {

    }

    @Override
    public boolean isValidMove(Position newPosition, Board board) {
       return isValidMove(newPosition, board, true);
    }
    @Override
    public boolean isValidMove(Position newPosition, Board board, boolean checkKingSafety){
        if(getPosition().equals(newPosition)){
            return false;
        }
        if(!BoardUtils.isStraightMove(getPosition(), newPosition)){
            return false;
        }

        if(checkKingSafety && BoardUtils.isKingInCheck(position, newPosition, board, getColor())){return false;}
        return true;
    }

    @Override
    public Rook copy(){
        return new Rook(this.color, this.position);
    }

    @Override
    public String getName() {
        return (color == Color.WHITE ? "W_" : "B_")  + "RK";
    }

}
