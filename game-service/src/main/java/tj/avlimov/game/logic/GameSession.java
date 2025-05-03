package tj.avlimov.game.logic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

@Getter
@AllArgsConstructor
@Setter
public class GameSession {
    private WebSocketSession player1;
    private WebSocketSession player2;

    public GameSession(WebSocketSession creatorSession){

        this.player1 = creatorSession;
    }

}
