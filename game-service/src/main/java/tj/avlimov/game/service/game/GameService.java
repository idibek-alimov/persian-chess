package tj.avlimov.game.service.game;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tj.avlimov.game.dto.board.BoardDTO;
import tj.avlimov.game.dto.game.create.GameCreateDTO;
import tj.avlimov.game.dto.game.create.OpenGameDTO;
import tj.avlimov.game.dto.response.GameJoinResponse;
import tj.avlimov.game.dto.response.MoveResponse;
import tj.avlimov.game.exceptions.GameJoinException;
import tj.avlimov.game.exceptions.GameNotFoundException;
import tj.avlimov.game.exceptions.InvalidMoveException;
import tj.avlimov.game.exceptions.PlayerNotFoundException;
import tj.avlimov.game.model.game.Board;
import tj.avlimov.game.model.game.Position;
import tj.avlimov.game.model.enums.Color;
import tj.avlimov.game.model.enums.GameStateType;
import tj.avlimov.game.model.game.Game;
import tj.avlimov.game.model.pieces.Piece;
import tj.avlimov.game.model.player.Player;
import tj.avlimov.game.repository.BoardRepository;
import tj.avlimov.game.repository.GameRepository;
import tj.avlimov.game.repository.PlayerRepository;
import tj.avlimov.game.service.gameState.GameStateFactory;
import tj.avlimov.game.service.player.PlayerService;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;


    private final BoardService boardService;
    private final PlayerService playerService;


    @Transactional
    public OpenGameDTO createGame(GameCreateDTO gameCreateDTO){
        Player player = playerService.getPlayerById(gameCreateDTO.getPlayerId());
        Game game = new Game();
        game.setCurrentStateType(GameStateType.WAITING_FOR_PLAYER);
        gameRepository.save(game);

        return new OpenGameDTO(game.getGameCode(), game.getGameName(), List.of(player.getName()));
    }


    @Transactional
    public Long createGame(Long playerId){
        Player whitePlayer = playerService.getPlayerById(playerId);
        Game game = new Game();
        game.setWhitePlayer(whitePlayer);
        game.setCurrentPlayer(whitePlayer);
        game.setCurrentStateType(GameStateType.WAITING_FOR_PLAYER);
        Board board = new Board();


        boardService.initializeBoard(board);
        game.setBoard(board);
        board.setGame(game);

        return gameRepository.save(game).getId();
    }

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
//    private void validateMove(Game game, Piece piece, Position from, Position to){
//        Color currentPlayerColor = game.getCurrentPlayerColor();
//        if(piece.getColor() != currentPlayerColor) {
//            throw new InvalidMoveException("You are trying to play with opponents piece");
//        }
//    }
}
