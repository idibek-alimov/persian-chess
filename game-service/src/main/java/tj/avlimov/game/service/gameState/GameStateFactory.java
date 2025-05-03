package tj.avlimov.game.service.gameState;

import tj.avlimov.game.model.enums.GameStateType;

public class GameStateFactory {
    public static GameState createGameState(GameStateType gameStateType){
        return switch (gameStateType){
            case WAITING_FOR_PLAYER -> null;
            case PLAYING -> new PlayingState();
            case CHECKMATE -> new CheckmateState();
            case GAME_OVER -> new GameOverState();
        };
    }
}
