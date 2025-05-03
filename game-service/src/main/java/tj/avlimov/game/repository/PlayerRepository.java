package tj.avlimov.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tj.avlimov.game.model.player.Player;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}
