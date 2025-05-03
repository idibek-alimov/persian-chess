package tj.avlimov.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tj.avlimov.game.model.game.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
