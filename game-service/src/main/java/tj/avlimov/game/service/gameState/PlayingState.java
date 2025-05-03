package tj.avlimov.game.service.gameState;

import tj.avlimov.game.exceptions.InvalidMoveException;
import tj.avlimov.game.interfaces.Command;
import tj.avlimov.game.model.game.Board;
import tj.avlimov.game.model.game.Position;
import tj.avlimov.game.model.enums.GameStateType;
import tj.avlimov.game.model.game.Game;
import tj.avlimov.game.model.game.Move;
import tj.avlimov.game.model.pieces.Piece;
import tj.avlimov.game.model.player.Player;

public class PlayingState implements GameState {
    @Override
    public void handleMove(Game game, Position from, Position to) {
        Board board = game.getBoard();
        Piece piece = board.getPiece(from);

        // No piece at position
        if(piece == null || piece.isValidMove(to, board)){
            throw new InvalidMoveException("Invalid move");
        }

        Command moveCommand = new Move(piece, to, board);
        moveCommand.execute();
//        game.addToHistory(moveCommand);

        if(board.isCheckmate()){
            game.setCurrentState(new CheckmateState());
            game.handleCheckmate();
        }
    }

    @Override
    public void handleCheckmate(Game game) {

    }

    @Override
    public void handleResign(Game game, Player player) {
//        System.out.println(player.getColor().toString() + "resigns. Game over!");
        game.setCurrentState(new GameOverState());
    }

    @Override
    public GameStateType getStateType() {
        return GameStateType.PLAYING;
    }
}
