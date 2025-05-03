package tj.avlimov.game.model.player;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("HUMAN")
@NoArgsConstructor
public class HumanPlayer extends Player{
    public HumanPlayer(Long id){
        super(id);
    }
}
