package tj.avlimov.game.model.game;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tj.avlimov.game.interfaces.Command;
import tj.avlimov.game.model.pieces.Piece;

import java.time.LocalDateTime;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Move implements Command {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Piece piece;

    @ManyToOne()
    @JoinColumn(nullable = true)
    private Piece capturedPiece;


    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="x", column = @Column(name = "from_X")),
            @AttributeOverride(name="y", column = @Column(name = "from_y"))
    })
    private Position from;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="x", column = @Column(name="to_x")),
            @AttributeOverride(name="y", column = @Column(name="to_y"))
    })
    private Position to;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = true)
    private Board board;


    @Column(nullable = false, updatable = false)
    private Integer moveNumber;

    private LocalDateTime timeStamp;

    public Move(Piece piece, Position to, Board board){
        this.piece = piece;
        this.from = piece.getPosition();
        this.to = to;
        this.board = board;
    }

    @Override
    public void execute() {
        System.out.println(" \n \n  Before execution move \n \n" );
        System.out.println(board.getBoardString());

        this.capturedPiece = board.getPiece(to);
        if(capturedPiece != null){
            capturedPiece.setPosition(null);
        }
        piece.setPosition(to);

        System.out.println(" \n \n  After move \n \n" );
        System.out.println(board.getBoardString());
    }

    @Override
    public void undo(){
        if(this.capturedPiece != null){
            capturedPiece.setPosition(to);
        }
        piece.setPosition(from);
    }
    @PrePersist
    public void prePersist(){
        if(this.moveNumber == null){
            Integer maxMoveNumber = board.getMoves().stream()
                    .map(Move::getMoveNumber)
                    .filter(Objects::nonNull)
                    .max(Integer::compare)
                    .orElse(0);

            this.moveNumber = maxMoveNumber + 1;
        }
    }

    public String toString(){
        return String.format("From %s to %s", from.toString(), to.toString());
    }
}
