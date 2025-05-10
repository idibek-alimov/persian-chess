package tj.avlimov.game.model.game;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import tj.avlimov.game.logic.GameNameGenerator;
import tj.avlimov.game.model.enums.Color;
import tj.avlimov.game.model.enums.GamePrivacyType;
import tj.avlimov.game.model.enums.GameStateType;
import tj.avlimov.game.service.gameState.GameState;
import tj.avlimov.game.model.player.Player;
import tj.avlimov.game.service.gameState.GameStateFactory;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gameIdGenerator")
    @SequenceGenerator(name = "gameIdGenerator", sequenceName = "GameIdGenerator", allocationSize = 1)
    private Long id;

    private String gameCode;

    private String gameName;

    @ManyToOne
    @JoinColumn(name = "creator_id", referencedColumnName = "id")
    private Player creator;

    @OneToOne(mappedBy = "game",cascade=CascadeType.ALL, orphanRemoval = true)
    private Board board;

    @ManyToOne
    @JoinColumn(name="white_player_id", referencedColumnName="id")
    private Player whitePlayer;

    @ManyToOne
    @JoinColumn(name="black_player_id", referencedColumnName = "id")
    private Player blackPlayer;

    @ManyToOne
    @JoinColumn(name="current_player_id", referencedColumnName = "id")
    private Player currentPlayer;

    @Enumerated(EnumType.STRING)
    private GameStateType currentStateType;

    @Enumerated(EnumType.STRING)
    private GamePrivacyType gamePrivacyType;
    @Transient
    private GameState currentState;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    public Game(Player whitePlayer, Player blackPlayer){
        this.whitePlayer = whitePlayer;
        this.currentPlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
    }

    public void handleMove(Position from, Position to){
        currentState.handleMove(this, from, to);
        this.currentStateType = currentState.getStateType();
    }

    public void setCurrentState(GameState currentState){
        this.currentState = currentState;
    }

    public void handleCheckmate(){
        currentState.handleCheckmate(this);
    }

    @PostLoad
    public void postLoad(){
        this.currentState = GameStateFactory.createGameState(currentStateType);
    }

    public Color getCurrentPlayerColor(){
        if(currentPlayer == whitePlayer){
            return Color.WHITE;
        }
        return Color.BLACK;
    }

    @PrePersist
    public void prePersist(){
        this.gameCode = UUID.randomUUID().toString();
        this.gameName = GameNameGenerator.generateGameName();
        this.currentStateType = GameStateType.WAITING_FOR_PLAYER;
    }

    public void startGame(){
        setCurrentStateType(GameStateType.PLAYING);
        setCurrentPlayer(getWhitePlayer());
    }
}
