package tj.avlimov.game.service.game;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tj.avlimov.game.dto.game.create.GameCreationRequest;
import tj.avlimov.game.dto.game.create.GameCreationResponse;
import tj.avlimov.game.dto.game.join.GameJoiningRequest;
import tj.avlimov.game.dto.response.GameJoinResponse;
import tj.avlimov.game.dto.response.MoveResponse;
import tj.avlimov.game.exceptions.GameJoinException;
import tj.avlimov.game.exceptions.GameNotFoundException;
import tj.avlimov.game.exceptions.InvalidMoveException;
import tj.avlimov.game.model.game.Board;
import tj.avlimov.game.model.game.Position;
import tj.avlimov.game.model.enums.GameStateType;
import tj.avlimov.game.model.game.Game;
import tj.avlimov.game.model.player.Player;
import tj.avlimov.game.repository.GameRepository;
import tj.avlimov.game.service.player.PlayerService;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;


    private final BoardService boardService;
    private final PlayerService playerService;


    private final MatchmakingPool matchmakingPool;


    @Transactional
    public GameCreationResponse createGame(GameCreationRequest gameCreationRequest){
        System.out.println("Creator id = " + gameCreationRequest.getCreatorId());
        Player player = playerService.getPlayerById(gameCreationRequest.getCreatorId());
        Game newGame = Game.builder()
                .creator(player)
                .gamePrivacyType(gameCreationRequest.getPrivacyType())
                .build();
        gameRepository.save(newGame);
        return new GameCreationResponse(newGame.getGameCode(), newGame.getGameName());
    }
    @Transactional
    public void joinGame(GameJoiningRequest gameJoiningRequest){
        Player player = playerService.getPlayerById(gameJoiningRequest.getPlayerId());
        Game game = gameRepository.findByGameCodeWithLock(gameJoiningRequest.getGameCode()).orElseThrow(() -> new GameNotFoundException("Game with given gameCode was not found"));
        if(matchmakingPool.isGameWaiting(game.getId()));

    }

    @Transactional
    public void startGame(Game game, Long playerOneId, Long playerTwoId){
        Player playerOne = playerService.getPlayerById(playerOneId);
        Player playerTwo = playerService.getPlayerById(playerTwoId);
        List<Player> playerList = Arrays.asList(playerOne, playerTwo);
        Collections.shuffle(playerList);

        game.setWhitePlayer(playerList.get(0));
        game.setCurrentPlayer(game.getWhitePlayer());

        game.setBlackPlayer(playerList.get(1));

        game.setCurrentStateType(GameStateType.PLAYING);

        Board board = new Board(game);
        game.setBoard(board);
        board.initializeChessPieces();
    }


//    @Transactional
//    public Long createGame(Long playerId){
//        Player whitePlayer = playerService.getPlayerById(playerId);
//        Game game = new Game();
//        game.setWhitePlayer(whitePlayer);
//        game.setCurrentPlayer(whitePlayer);
//        game.setCurrentStateType(GameStateType.WAITING_FOR_PLAYER);
//        Board board = new Board();
//
//
//        boardService.initializeBoard(board);
//        game.setBoard(board);
//        board.setGame(game);
//
//        return gameRepository.save(game).getId();
//    }

    @Transactional
    public GameJoinResponse joinGame(Long playerId, Long gameId) {
        Objects.requireNonNull(gameId, "Game id can not be null");

        Player player = playerService.getPlayerById(playerId);
        Game game = getGameById(gameId);

        validateJoinConditions(game);
        game.setBlackPlayer(player);
        game.startGame();

        return new GameJoinResponse(
                game.getId(),
                player.getId(),
                game.getWhitePlayer().getId(),
                game.getCurrentStateType()
        );
    }

    @Transactional
    public MoveResponse makeMove(Long playerId, Long gameId, Position from, Position to){
        Game game = getGameById(gameId);
        Player player = playerService.getPlayerById(playerId);

        Board board = game.getBoard();
        validateMoveTurn(game, player);

        return boardService.executeMove(board.getId(), from, to, game.getCurrentPlayerColor());

    }
    private Game getGameById(Long gameId){
        return gameRepository.findByIdWithLock(gameId).orElseThrow(() -> new GameNotFoundException("Game not found"));
    }
    private void validateJoinConditions(Game game){
        if(game.getBlackPlayer() != null){
            throw new GameJoinException("Game already has both players");
        }

        if(game.getCurrentStateType() != GameStateType.WAITING_FOR_PLAYER){
            throw new GameJoinException(" Game is not on waiting state");
        }
    }

    public void validateMoveTurn(Game game, Player player){
        if(game.getCurrentPlayer().getId() != player.getId()){
            throw new InvalidMoveException("This is not yor turn to move");
        }
    }

    public String getBoardString(Long gameId){
        return boardService.getBoardString(getGameById(gameId).getBoard().getId());
    }

    @Scheduled(fixedRate = 10000)
    public void findStaleGames(){
        LocalDateTime cutoff = LocalDateTime.now().minusMinutes(2);

        List<Game> games = gameRepository.findStaleGames(cutoff);
        System.out.println("Stale games");
        for(Game game : games){
            System.out.println(game.getId());
            System.out.println(game);
        }
    }
}
