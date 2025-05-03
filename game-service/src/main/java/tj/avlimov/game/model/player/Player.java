package tj.avlimov.game.model.player;

import jakarta.persistence.*;
import lombok.*;
import tj.avlimov.game.model.enums.Color;
import tj.avlimov.game.model.enums.PlayerType;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "player_type")
@Setter
@Getter
public abstract class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    protected String name;

    public Player(Long id){
        this.id = id;
    }

}
