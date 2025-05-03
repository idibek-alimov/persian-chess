package tj.avlimov.game.service.gameState;

import tj.avlimov.game.model.game.Position;
import tj.avlimov.game.model.enums.GameStateType;
import tj.avlimov.game.model.game.Game;
import tj.avlimov.game.model.player.Player;

public class CheckmateState implements GameState {
    @Override
    public void handleMove(Game game, Position from, Position to) {
        System.out.println("Game is over. No further moves allowed");
    }

    @Override
    public void handleCheckmate(Game game) {
        Player winner = game.getCurrentPlayer();
//        System.out.println("Checkmate! " + winner.getColor().toString() + " wins!");
        game.setCurrentState(new GameOverState());
    }

    @Override
    public void handleResign(Game game, Player player) {
        System.out.println("Game already over with checkmate.");
    }

    @Override
    public GameStateType getStateType() {
        return GameStateType.CHECKMATE;
    }
}
