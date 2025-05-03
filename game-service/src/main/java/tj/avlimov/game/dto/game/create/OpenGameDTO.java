package tj.avlimov.game.dto.game.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpenGameDTO {
    public String gameCode;
    public String gameName;
    public List<String> players = new ArrayList<>();
}
