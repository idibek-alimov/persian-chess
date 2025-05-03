package tj.avlimov.game.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.server.WebSession;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import tj.avlimov.game.dto.board.BoardDTO;
import tj.avlimov.game.dto.response.GameCreationResponse;
import tj.avlimov.game.dto.response.GameJoinResponse;
import tj.avlimov.game.dto.response.MoveResponse;
import tj.avlimov.game.logic.GameManager;
import tj.avlimov.game.model.game.Position;
import tj.avlimov.game.service.game.GameService;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class MyWebSocketHandler extends TextWebSocketHandler {


    private final GameManager gameManager;
    private final GameService gameService;
    private Map<String, WebSocketSession> playerSessions = new ConcurrentHashMap<>();



    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        playerSessions.put(session.getId(), session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        System.out.println("Message received");

        System.out.println(message.getPayload());

        try{
            JsonNode json = new ObjectMapper().readTree(message.getPayload());
            String type = json.get("type").asText();
            switch(type){
//                case("CREATE_GAME"):
//                    handleCreateGame(session, json);
                case("JOIN_GAME"):
                    handleJoinGame(session, json);
                case("MAKE_MOVE"):
                    handleMove(session, json);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void handleJoinGame(WebSocketSession session, JsonNode json) throws IOException {
        Long joinerId = json.get("player_id").asLong();
        Long gameId = json.get("game_id").asLong();

        GameJoinResponse response = gameService.joinGame(joinerId, gameId);
        gameManager.gameJoined(gameId, session);
        gameManager.broadcastToGame(gameId, response);
    }
    public void handleMove(WebSocketSession session, JsonNode json) throws IOException{
        Long playerId = json.get("player_id").asLong();
        Long gameId = json.get("game_id").asLong();
        int from_x = json.get("from_x").asInt();
        int from_y = json.get("from_y").asInt();

        int to_x = json.get("to_x").asInt();
        int to_y = json.get("to_y").asInt();

        Position from = new Position(from_x, from_y);
        Position to = new Position(to_x, to_y);

        MoveResponse moveResponse = gameService.makeMove(playerId, gameId, from, to);
        gameManager.broadcastToGame(gameId, moveResponse);

    }
}
