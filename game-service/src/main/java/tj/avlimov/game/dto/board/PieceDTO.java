package tj.avlimov.game.dto.board;

import tj.avlimov.game.model.enums.Color;
import tj.avlimov.game.model.game.Position;

public record PieceDTO (
  String type,
  Color color,
  Position position,
  boolean captured
) {}
