package tj.avlimov.game.model.game;

import jakarta.persistence.*;
import lombok.Data;
import tj.avlimov.game.dto.board.BoardDTO;
import tj.avlimov.game.dto.board.PieceDTO;
import tj.avlimov.game.exceptions.KingNotFoundException;
import tj.avlimov.game.model.enums.Color;
import tj.avlimov.game.model.pieces.*;

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

    public void placePiece(Piece piece){
        piece.setBoard(this);
        pieces.add(piece);
    }
//    public void placePiece(Piece piece, Position position){
//        piece.setBoard(this);
//        pieces.add(piece);
//    }
    public void initializeChessPieces(){
        // Rooks
        placePiece(new Rook(Color.WHITE, new Position(0,0)));
        placePiece(new Rook(Color.WHITE, new Position(7,0)));
        placePiece(new Rook(Color.BLACK, new Position(0,7)));
        placePiece(new Rook(Color.BLACK, new Position(7,7)));

        // Knights
        placePiece(new Knight(Color.WHITE, new Position(1,0)));
        placePiece(new Knight(Color.WHITE, new Position(6,0)));
        placePiece(new Knight(Color.BLACK, new Position(1,7)));
        placePiece(new Knight(Color.BLACK, new Position(6,7)));

        // Bishops
        placePiece(new Bishop(Color.WHITE, new Position(2,0)));
        placePiece(new Bishop(Color.WHITE, new Position(5,0)));
        placePiece(new Bishop(Color.BLACK, new Position(2,7)));
        placePiece(new Bishop(Color.BLACK, new Position(5,7)));

        // Queens
        placePiece(new Queen(Color.WHITE, new Position(3, 0)));
        placePiece(new Queen(Color.BLACK, new Position(4,7)));

        // Kings
        placePiece(new King(Color.WHITE, new Position(4, 0)));
        placePiece(new King(Color.BLACK, new Position(3, 7)));

        // Pawns
        for(int i=0;i<8;i++){
            placePiece(new Pawn(Color.WHITE, new Position(i,1)));
            placePiece(new Pawn(Color.BLACK, new Position(i,6)));
        }
    }

    public Move makeMove(Move move){
        move.setBoard(this);
        move.execute();
//        moves.add(move);
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
        return new BoardDTO(
                this.id,
                this.pieces.stream()
                        .filter(p -> p.getPosition() != null)
                        .map(p -> new PieceDTO(
                                p.getClass().getSimpleName().toUpperCase(),
                                p.getColor(),
                                p.getPosition(),
                                false
                        ))
                        .toList(),
                this.game.getCurrentPlayerColor(),
                this.game.getCurrentStateType()
        );
    }

    public String getBoardString(){
        StringBuilder sb = new StringBuilder(" ");
        for(int i=0;i<8;i++){
            sb.append("   " + i + "   ");
        }
        sb.append(System.getProperty("line.separator"));

        for(int y=0; y<8;y++){
            sb.append(" " + y + " ");
            for(int x=0; x<8;x++){
                Position position = new Position(x, y);
                Piece piece = getPiece(position);
                if(piece != null){
                    sb.append(" "+piece.getName()+"  ");
                }
                else{
                    sb.append("   .   ");
                }
            }
            sb.append(System.getProperty("line.separator"));
        }

        return sb.toString();
    }

}
