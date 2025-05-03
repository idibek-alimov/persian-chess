package tj.avlimov.game.model.pieces;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tj.avlimov.game.model.game.Board;
import tj.avlimov.game.model.game.Position;
import tj.avlimov.game.model.enums.Color;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "piece_type")
public abstract class Piece{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @Enumerated(EnumType.STRING)
    protected Color color;
    @Embedded
    protected Position position;

    public Piece(Color color, Position position){
        this.color = color;
        this.position = position;
    }

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "board_id")
    private Board board;

    public abstract boolean isValidMove(Position newPosition, Board board);

    public abstract boolean isValidMove(Position newPosition, Board board, boolean checkKingSafety);

    public abstract String getName();

    public abstract Piece copy();

    public boolean move(Position newPosition, Color kingColor){
        return false;
    }

}
