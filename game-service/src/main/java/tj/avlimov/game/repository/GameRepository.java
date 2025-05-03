package tj.avlimov.game.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import tj.avlimov.game.model.game.Game;

import java.util.Optional;

public interface GameRepository extends JpaRepository<Game,Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Game p WHERE p.id = :id")
    Optional<Game> findByIdWithLock(Long id);
}
