package tj.avlimov.game;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tj.avlimov.game.exceptions.InvalidMoveException;
import tj.avlimov.game.model.enums.Color;
import tj.avlimov.game.model.game.Board;
import tj.avlimov.game.model.game.Game;
import tj.avlimov.game.model.game.Position;
import tj.avlimov.game.model.pieces.Pawn;
import tj.avlimov.game.model.pieces.Piece;
import tj.avlimov.game.model.pieces.Rook;
import tj.avlimov.game.model.player.HumanPlayer;
import tj.avlimov.game.model.player.Player;
import tj.avlimov.game.repository.GameRepository;
import tj.avlimov.game.repository.MoveRepository;
import tj.avlimov.game.repository.PlayerRepository;
import tj.avlimov.game.service.game.GameStateValidator;
import tj.avlimov.game.service.game.MoveService;

@ExtendWith(MockitoExtension.class)
public class MoveServiceTest {
    @Mock
    private GameStateValidator gameStateValidator;
    @Mock
    private MoveRepository moveRepository;
    @Mock
    private PlayerRepository repository;
    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private MoveService moveService;



    private Board testBoard;
    private Piece whitePawn;
    private Piece blackPawn;
    private Piece whiteRook;
    private Game game= new Game();

    private Player whitePlayer = new HumanPlayer();
    private Player blackPlayer = new HumanPlayer();


    @BeforeEach
    void setup(){

        testBoard = new Board();
        whiteRook = new Rook(Color.WHITE, new Position(0, 0));
        testBoard.placePiece(whiteRook);
//        whitePawn = new Pawn(Color.WHITE, new Position(3,2));
//        blackPawn = new Pawn(Color.BLACK, new Position(4,3));
//        testBoard.placePiece(whitePawn);
//        testBoard.placePiece(blackPawn);
//        testBoard.initializeChessPieces();


        game.setWhitePlayer(whitePlayer);
        game.setBlackPlayer(blackPlayer);
        game.setBoard(testBoard);
        testBoard.setGame(game);
        game.startGame();
//        testBoard.placePiece(testPawn, testPawn.getPosition());
    }

    @Test
    void pawnInvalidDiagonalMove(){
        System.out.println(testBoard.getBoardString());
        Position from = new Position(3, 1);
        Position to = new Position(4,3);

        Assertions.assertThrows(InvalidMoveException.class ,() -> moveService.validateAndCreateMove(testBoard, testBoard.getPiece(from), from, to, Color.WHITE));

    }

//    @Test
//    void pawnCapture(){
//        Assertions.assertDoesNotThrow(() -> {
//           moveService.validateAndCreateMove(testBoard, whitePawn, whitePawn.getPosition(), blackPawn.getPosition(), Color.WHITE);
//        });
//    }

//    @Test
//    void isValidMove_WhenKingInCheck_ThrowsException(){
//        when(gameStateValidator.)
//    }
    @Test
    void knightMove(){
        Position from = new Position(1, 0);
        Position to = new Position(2,2);

        Assertions.assertDoesNotThrow(() -> {
           moveService.validatePieceMovement(testBoard.getPiece(from), to, testBoard);
        });
    }

    @Test
    void rookMove(){
        Position from = new Position(0, 0);
        Position to = new Position(0, 2);

//        Assertions.assertDoesNotThrow(() ->{
//            moveService.validatePieceMovement(testBoard.getPiece(from), to, testBoard);
//        });

        Assertions.assertDoesNotThrow(() -> {
            moveService.validatePieceMovement(whiteRook, new Position(5, 5), testBoard);
        });
    }
}
