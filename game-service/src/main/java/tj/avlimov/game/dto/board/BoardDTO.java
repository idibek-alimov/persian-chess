package tj.avlimov.game.dto.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import tj.avlimov.game.model.enums.Color;
import tj.avlimov.game.model.enums.GameStateType;

import java.util.List;


public record BoardDTO (
        Long id,
        List<PieceDTO> pieces,
        Color currentPlayer,
        GameStateType gameState
)
{}
