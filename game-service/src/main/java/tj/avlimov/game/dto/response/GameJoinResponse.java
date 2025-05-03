package tj.avlimov.game.dto.response;

import lombok.Data;
import tj.avlimov.game.model.enums.GameStateType;
import tj.avlimov.game.service.gameState.GameState;

//@Data
public record GameJoinResponse (
    Long gameId,
    Long whitePlayerId,
    Long blackPlayerId,
    GameStateType gameState
    ){}

