package tj.avlimov.game.dto.game.join;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameJoiningRequest {
    Long playerId;
    String gameCode;
}
