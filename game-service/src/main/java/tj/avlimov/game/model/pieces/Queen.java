package tj.avlimov.game.model.pieces;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import tj.avlimov.game.model.game.Board;
import tj.avlimov.game.model.game.Move;
import tj.avlimov.game.model.game.Position;
import tj.avlimov.game.model.enums.Color;

@Entity
@DiscriminatorValue("QUEEN")
public class Queen extends Piece{
    public Queen(Color color, Position position){
        super(color, position);
    }

    public Queen() {

    }

    @Override
    public boolean isValidMove(Position newPosition, Board board){
        return isValidMove(newPosition, board);
    }
    @Override
    public boolean isValidMove(Position newPosition, Board board, boolean checkKingSafety) {
        if(!BoardUtils.isDiagonalMove(getPosition(), newPosition) || !BoardUtils.isStraightMove(getPosition(), newPosition)){
            return false;
        }
        if(getPosition().equals(newPosition)){
            return false;
        }

        if(checkKingSafety && BoardUtils.isKingInCheck(position, newPosition, board, getColor())){return false;}
        return true;
    }

    @Override
    public Piece copy(){
        return new Queen(this.color, this.position);
    }

    @Override
    public String getName() {
        return (color == Color.WHITE ? "W_" : "B_")  + "QN";
    }
}
