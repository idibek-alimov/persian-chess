package tj.avlimov.game.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameCreationResponse {
    String type;
    Long gameId;
}
