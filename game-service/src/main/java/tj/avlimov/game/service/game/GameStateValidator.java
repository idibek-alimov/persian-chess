package tj.avlimov.game.service.game;

import tj.avlimov.game.exceptions.InvalidMoveException;
import tj.avlimov.game.model.enums.Color;
import tj.avlimov.game.model.game.Game;

public class GameStateValidator {
    public static void validateTurn(Game game, Color color){
        if(game.getCurrentPlayerColor() != color){
            throw new InvalidMoveException("This is not you turn");
        }
    }
}
