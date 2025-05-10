package tj.avlimov.game.dto.game.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameCreationResponse {
    private String gameCode;
    private String gameName;
}
