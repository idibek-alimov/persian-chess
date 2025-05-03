package tj.avlimov.game.dto.response;

import tj.avlimov.game.model.enums.GameStateType;

public record MoveResponse(
   Long moveId,
   GameStateType gameStateType,
   String boardString
) {}
