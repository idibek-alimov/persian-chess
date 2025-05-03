package tj.avlimov.game.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tj.avlimov.game.dto.game.create.GameCreateDTO;
import tj.avlimov.game.dto.game.create.OpenGameDTO;
import tj.avlimov.game.service.game.GameService;

@Controller
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {

    @Autowired
    private final GameService gameService;


    @PostMapping("")
    public ResponseEntity<OpenGameDTO> createGame(@RequestBody GameCreateDTO gameCreateDTO){
        OpenGameDTO gameDTO = gameService.createGame(gameCreateDTO);
        return ResponseEntity.ok(gameDTO);
    }
}
