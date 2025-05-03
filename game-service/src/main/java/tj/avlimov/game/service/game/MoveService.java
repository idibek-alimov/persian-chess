package tj.avlimov.game.service.game;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tj.avlimov.game.exceptions.InvalidMoveException;
import tj.avlimov.game.model.enums.Color;
import tj.avlimov.game.model.game.Board;
import tj.avlimov.game.model.game.Move;
import tj.avlimov.game.model.game.Position;
import tj.avlimov.game.model.pieces.Piece;
import tj.avlimov.game.repository.MoveRepository;

@Service
@RequiredArgsConstructor
public class MoveService {
    private final MoveRepository moveRepository;


    public Move validateAndCreateMove(Board board, Piece piece, Position from, Position to, Color playerColor){
        GameStateValidator.validateTurn(board.getGame(), playerColor);
        validatePieceMovement(piece, to, board);

        return new Move(piece, to, board);
    }

    public Move saveMove(Move move){
        return moveRepository.save(move);
    }

    public void validatePieceMovement(Piece piece, Position to, Board board){
//        System.out.println("Testing for valid move");
        if(!piece.isValidMove(to, board)){
            throw new InvalidMoveException("Illegal move for " + piece.getClass().getSimpleName());
        }
    }



}
