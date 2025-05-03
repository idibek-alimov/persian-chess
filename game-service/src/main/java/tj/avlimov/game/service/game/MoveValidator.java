package tj.avlimov.game.service.game;

import org.springframework.stereotype.Component;
import tj.avlimov.game.model.game.Board;
import tj.avlimov.game.model.game.Position;
import tj.avlimov.game.model.pieces.Piece;

@Component
public class MoveValidator {
    public boolean isValidMove(Board board, Piece piece, Position to){
//        if(!piece)
        return false;
    }
}
