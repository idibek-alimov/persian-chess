package tj.avlimov.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tj.avlimov.game.model.game.Move;

public interface MoveRepository extends JpaRepository<Move, Long> {
}
