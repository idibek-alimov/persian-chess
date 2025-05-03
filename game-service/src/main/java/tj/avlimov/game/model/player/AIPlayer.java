package tj.avlimov.game.model.player;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("AI")
public class AIPlayer extends Player{
}
