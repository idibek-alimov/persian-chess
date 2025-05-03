package tj.avlimov.game.model.pieces;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import tj.avlimov.game.model.game.Board;
import tj.avlimov.game.model.game.Move;
import tj.avlimov.game.model.game.Position;
import tj.avlimov.game.model.enums.Color;

@Entity
@DiscriminatorValue("BISHOP")
public class Bishop extends Piece{

    public Bishop(Color color, Position position){
        super(color, position);
    }

    public Bishop() {

    }

    @Override
    public boolean isValidMove(Position newPosition, Board board){
        return isValidMove(newPosition, board, true);
    }

    @Override
    public boolean isValidMove(Position newPosition, Board board, boolean checkKingSafety) {
        if(getPosition().equals(newPosition) || !BoardUtils.isDiagonalMove(getPosition(), newPosition) || !BoardUtils.isPathClear(board, getPosition(), newPosition)){
            return false;
        }
        Piece piece = board.getPiece(newPosition);
        if(piece != null && piece.getColor() == getColor()){
            return false;
        }
        if(checkKingSafety && BoardUtils.isKingInCheck(position, newPosition, board, getColor())){return false;}
        return true;
    }

    @Override
    public Piece copy(){
        return new Bishop(this.color, this.position);
    }

    @Override
    public String getName() {
        return (color == Color.WHITE ? "W_" : "B_")  + "BP";
    }
}
