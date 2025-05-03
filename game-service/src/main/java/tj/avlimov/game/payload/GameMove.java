package tj.avlimov.game.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tj.avlimov.game.model.game.Position;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameMove {
    private String gameId;
    private String playerId;
    private Position from;
    private Position to;
}
