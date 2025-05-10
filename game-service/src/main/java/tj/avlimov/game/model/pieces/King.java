package tj.avlimov.game.model.pieces;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import tj.avlimov.game.model.game.Board;
import tj.avlimov.game.model.game.Position;
import tj.avlimov.game.model.enums.Color;
import tj.avlimov.game.utils.BoardUtils;

@Entity
@DiscriminatorValue("KING")
public class King extends Piece{

    public King(Color color, Position position){
        super(color, position);
    }

    public King() {

    }

    @Override
    public boolean isValidMove(Position newPosition, Board board){
        return isValidMove(newPosition, board, true);
    }
    @Override
    public boolean isValidMove(Position newPosition, Board board, boolean checkKingSafety) {
        if(getPosition().equals(newPosition)){
            return false;
        }
        if(!BoardUtils.isKingMove(getPosition(), newPosition)){
            return false;
        }
        if(checkKingSafety && BoardUtils.isKingInCheck(position, newPosition, board, getColor())){return false;}
        return true;
    }

    @Override
    public String getName() {
        return (color == Color.WHITE ? "W_" : "B_")  + "KG";
    }

    @Override
    public Piece copy(){
        return new King(color, this.position);
    }
}
