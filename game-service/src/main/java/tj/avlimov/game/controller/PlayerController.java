package tj.avlimov.game.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tj.avlimov.game.dto.player.PlayerCreateDto;
import tj.avlimov.game.service.player.PlayerService;

@RestController
@RequestMapping("/player")
@RequiredArgsConstructor
public class PlayerController {
    private final PlayerService playerService;

    @PostMapping("/create")
    public ResponseEntity<Long> createPlayer(){
        Long id = playerService.createPlayer(new PlayerCreateDto());
        return ResponseEntity.ok(id);
    }

}
