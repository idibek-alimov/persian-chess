package tj.avlimov.game.model.game;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
public class Position {

    @Min(0)
    @Max(7)
    private int x;
    @Min(0)
    @Max(7)
    private int y;

    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    public String toString(){
        return String.format("[%s, %s]", x, y);
    }


    public boolean equals(Position position){
        return this.x == position.getX() && this.y == position.getY();
    }
}
