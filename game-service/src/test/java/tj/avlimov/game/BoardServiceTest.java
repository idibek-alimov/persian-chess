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
import tj.avlimov.game.model.game.Move;
import tj.avlimov.game.model.game.Position;
import tj.avlimov.game.model.player.HumanPlayer;
import tj.avlimov.game.model.player.Player;
import tj.avlimov.game.repository.BoardRepository;
import tj.avlimov.game.service.game.BoardService;
import tj.avlimov.game.service.game.MoveService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BoardServiceTest {

    @Mock
    private BoardRepository boardRepository;

    @Mock
    private MoveService moveService;

    @InjectMocks
    private BoardService boardService;

    Player whitePlayer = new HumanPlayer(1L);
    Player blackPlayer = new HumanPlayer(2L);

    Board board ;//= new Board();

    @BeforeEach
    void setup(){
        board = new Board();
    }

    @Test
    void testInitializingBoard(){
        Assertions.assertDoesNotThrow(() -> boardService.initializeBoard(board));
//        System.out.println(board.getPieces().size());
    }
    @Test
    void testPawnMoveForwardOne(){
        boardService.initializeBoard(board);

        Position from = new Position(3, 1);
        Position to = new Position(3,2);
        when(boardRepository.findById(any())).thenReturn(Optional.ofNullable(board));
        when(moveService.validateAndCreateMove(board, board.getPiece(from), from, to, Color.WHITE)).thenReturn(new Move(board.getPiece(from), to, board));
        Assertions.assertDoesNotThrow(() -> boardService.executeMove(1L, from, to, Color.WHITE));
    }

    @Test
    void testPawnMoveForwardTwo(){
        boardService.initializeBoard((board));
        Position from = new Position(3, 1);
        Position to = new Position(3,3);
        when(boardRepository.findById(any())).thenReturn(Optional.ofNullable(board));
        when(moveService.validateAndCreateMove(board, board.getPiece(from), from, to, Color.WHITE)).thenReturn(new Move(board.getPiece(from), to, board));
        Assertions.assertDoesNotThrow(() -> boardService.executeMove(1L, from, to, Color.WHITE));
    }
    @Test
    void testPawnIllegalDiagonalMove(){
        boardService.initializeBoard((board));
        Position from = new Position(3, 1);
        Position to = new Position(4,2);
        when(boardRepository.findById(any())).thenReturn(Optional.ofNullable(board));
        when(moveService.validateAndCreateMove(board, board.getPiece(from), from, to, Color.WHITE)).thenThrow(InvalidMoveException.class);
        Assertions.assertThrows(InvalidMoveException.class,() -> boardService.executeMove(1L, from, to, Color.WHITE));
    }
//    @Test
//    void testIllegalMovePawn(){
//        boardService.initializeBoard(board);
//        Position from new Position((3,1));
//    }

//    @Test

}
