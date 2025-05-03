package tj.avlimov.game.service.gameState;

import tj.avlimov.game.model.game.Position;
import tj.avlimov.game.model.enums.GameStateType;
import tj.avlimov.game.model.game.Game;
import tj.avlimov.game.model.player.Player;

public interface GameState {
    void handleMove(Game game, Position from, Position to);
    void handleCheckmate(Game game);
    void handleResign(Game game, Player player);

    GameStateType getStateType();
}
