package tj.avlimov.game.model.game;

import jakarta.persistence.*;
import lombok.Data;
import tj.avlimov.game.dto.board.BoardDTO;
import tj.avlimov.game.dto.board.PieceDTO;
import tj.avlimov.game.exceptions.KingNotFoundException;
import tj.avlimov.game.model.enums.Color;
import tj.avlimov.game.model.pieces.*;
import tj.avlimov.game.utils.BoardUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Data
public class Board extends Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToMany(mappedBy="board", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Piece> pieces = new ArrayList<>();

    @OneToMany(mappedBy="board", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("moveNumber ASC")
    private List<Move> moves = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Game game;

    public Board copy(){
        Board board = new Board();

        for(Piece piece: pieces){
            board.placePiece(piece.copy());
        }
        return board;
    }

    public Board(Game game){
        this.game = game;
    }
    public void placePiece(Piece piece){
        piece.setBoard(this);
        pieces.add(piece);
    }
    public void initializeChessPieces(){
        BoardUtils.initializeChessPieces(this);
    }

    public Move makeMove(Move move){
        move.setBoard(this);
        move.execute();
        return move;
    }
    public Position findKingPosition(Color kingColor){
        for(Piece piece: pieces){
            if(piece instanceof King && piece.getColor() == kingColor){
                return piece.getPosition();
            }
        }
        throw new KingNotFoundException("The king not found");
    }

    public Piece getPiece(Position position){
        return pieces.stream()
                .filter(piece -> piece.getPosition() != null)
                .filter(piece -> piece.getPosition().equals(position))
                .findFirst().orElse(null);
    }

    public boolean isCheckmate(){
        // To Do
        return false;
    }

    public boolean isOccupied(Position position){
        return getPiece(position) != null;
    }
    public boolean isOccupiedByOpponent(Position position, Color color){
        Piece piece = getPiece(position);
        return piece != null && piece.getColor() != color;
    }

    public Move getLastMove(){
        List<Move> moves = getMoves();
        if(moves == null){
            System.out.println("moves == null");
        }
        if(getMoves().size() <= 0){
            return null;
        }
        return getMoves().get(getMoves().size() - 1);
    }

    public BoardDTO toDTO(){
       return BoardUtils.toDTO(this);
    }

    public String getBoardString(){
        return BoardUtils.getBoardString(this);
    }

}
