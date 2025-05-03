package tj.avlimov.game.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class GameManager {
    private final Map<Long, GameSession> activeGames = new ConcurrentHashMap<>();
    ObjectMapper objectMapper = new ObjectMapper();

    public void registerGame(Long gameId, WebSocketSession player1, WebSocketSession player2){
        activeGames.put(gameId, new GameSession(player1, player2));
    }

    public void gameCreated(Long gameId, WebSocketSession creatorSession){
        GameSession gameSession = new GameSession(creatorSession);
        activeGames.put(gameId, gameSession);
    }
    public void gameJoined(Long gameId, WebSocketSession joinerSession){
        GameSession gameSession = activeGames.get(gameId);
        gameSession.setPlayer2(joinerSession);
        activeGames.put(gameId, gameSession);
    }
    public void broadcastToGame(Long gameId, Object object) throws JsonProcessingException {
        String message = objectMapper.writeValueAsString(object);
        broadcastToGame(gameId, message);
    }

    public void broadcastToGame(Long gameId, String message){
        GameSession gameSession = activeGames.get(gameId);
        if(gameSession != null){
            try{
                gameSession.getPlayer1().sendMessage(new TextMessage(message));
                gameSession.getPlayer2().sendMessage(new TextMessage(message));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
