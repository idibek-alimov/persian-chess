package tj.avlimov.game.model.game;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tj.avlimov.game.logic.GameNameGenerator;
import tj.avlimov.game.model.enums.Color;
import tj.avlimov.game.model.enums.GameStateType;
import tj.avlimov.game.service.gameState.GameState;
import tj.avlimov.game.model.player.Player;
import tj.avlimov.game.service.gameState.GameStateFactory;

import java.util.UUID;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String gameCode;

    private String gameName;


    @OneToOne(mappedBy = "game",cascade=CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "board_id", referencedColumnName = "id")
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
    @Transient
    private GameState currentState;

//    @OneToMany(mappedBy="game", cascade = CascadeType.ALL, orphanRemoval = true)
//    @OrderBy("moveNumber ASC")
//    private List<Move> moves = new ArrayList<>();

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

//    public Move getLastMove(){
//        if(moves.size() <= 0){
//            return null;
//        }
//        return moves.get(moves.size()-1);
//    }
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
    }

    public void startGame(){
        setCurrentStateType(GameStateType.PLAYING);
        setCurrentPlayer(getWhitePlayer());
    }
}
