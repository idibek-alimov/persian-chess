package tj.avlimov.game.service.game;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tj.avlimov.game.dto.board.BoardDTO;
import tj.avlimov.game.dto.response.MoveResponse;
import tj.avlimov.game.exceptions.BoardNotFoundException;
import tj.avlimov.game.model.enums.Color;
import tj.avlimov.game.model.enums.GameStateType;
import tj.avlimov.game.model.game.Board;
import tj.avlimov.game.model.game.Move;
import tj.avlimov.game.model.game.Position;
import tj.avlimov.game.model.pieces.Piece;
import tj.avlimov.game.repository.BoardRepository;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MoveService moveService;
    public BoardDTO getBoardState(Long boardId){
        Board board = getBoard(boardId);
        return board.toDTO();
    }

    public MoveResponse executeMove(Long boardId, Position from, Position to, Color playerColor){
        Board board = getBoard(boardId);
        Piece piece = board.getPiece(from);

        Move move = moveService.validateAndCreateMove(board, piece, from, to, playerColor);

        board.makeMove(move);
        moveService.saveMove(move);
        boardRepository.save(board);
        String boardString = board.getBoardString();
        return new MoveResponse(move.getId(), GameStateType.PLAYING, boardString);//board.getGame().getCurrentStateType()); FOR TESTING , FIX LATER
    }

    public void initializeBoard(Board board){
        board.initializeChessPieces();

    }

    public String getBoardString(Long id){
        return getBoard(id).getBoardString();
    }
    private Board getBoard(Long id){
        return boardRepository.findById(id).orElseThrow(() -> new BoardNotFoundException(String.format("Board with id = %s not found", id)));
    }
}
