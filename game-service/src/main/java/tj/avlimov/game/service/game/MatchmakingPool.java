package tj.avlimov.game.service.game;

import org.springframework.stereotype.Component;
import tj.avlimov.game.model.player.Player;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MatchmakingPool {
    Map<Long, Player> pool = new ConcurrentHashMap<>();

    public void addWaitingPlayer(Long gameId, Player player){
        pool.put(gameId, player);
    }

    public Player removeWaitingPlayer(Long gameId){
        return pool.remove(gameId);
    }

    public boolean isGameWaiting(Long gameId){
        return pool.containsKey(gameId);
    }

    public Player getWaitingPlayer(Long gameId){
        return pool.get(gameId);
    }
}
