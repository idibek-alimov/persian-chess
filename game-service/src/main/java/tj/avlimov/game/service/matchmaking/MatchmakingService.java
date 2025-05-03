package tj.avlimov.game.service.matchmaking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tj.avlimov.game.model.player.Player;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
@RequiredArgsConstructor
public class MatchmakingService {
    private final Queue<Player> waitingPlayers = new ConcurrentLinkedQueue<>();

    public void joinGame(Player)

}
