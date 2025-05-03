package tj.avlimov.game.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tj.avlimov.game.model.game.Move;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameUpdate {
    private String gameId;
    private String status;
    private List<Move> moves;
}
