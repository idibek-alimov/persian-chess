package tj.avlimov.game.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import tj.avlimov.game.model.game.Game;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface GameRepository extends JpaRepository<Game,Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Game p WHERE p.id = :id")
    Optional<Game> findByIdWithLock(Long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Game p WHERE p.gameCode = :gameCode")
    Optional<Game> findByGameCodeWithLock(String gameCode);

    @Query("SELECT g FROM Game g WHERE g.createdAt < :cutoffTime AND g.whitePlayer IS NULL AND g.blackPlayer IS NULL")
    List<Game> findStaleGames(LocalDateTime cutoffTime);
}
