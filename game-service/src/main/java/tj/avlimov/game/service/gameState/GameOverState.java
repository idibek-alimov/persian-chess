package tj.avlimov.game.service.gameState;

import tj.avlimov.game.model.game.Position;
import tj.avlimov.game.model.enums.GameStateType;
import tj.avlimov.game.model.game.Game;
import tj.avlimov.game.model.player.Player;

public class GameOverState implements GameState {
    @Override
    public void handleMove(Game game, Position from, Position to) {
        System.out.println("Game already over");
    }

    @Override
    public void handleCheckmate(Game game) {
        System.out.println("Game already over");
    }

    @Override
    public void handleResign(Game game, Player player) {
        System.out.println("Game already over");
    }

    @Override
    public GameStateType getStateType() {
        return GameStateType.GAME_OVER;
    }
}
