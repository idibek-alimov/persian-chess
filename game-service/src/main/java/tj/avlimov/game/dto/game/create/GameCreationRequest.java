package tj.avlimov.game.dto.game.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tj.avlimov.game.model.enums.GamePrivacyType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameCreationRequest {
    Long creatorId;
    GamePrivacyType privacyType;
}
