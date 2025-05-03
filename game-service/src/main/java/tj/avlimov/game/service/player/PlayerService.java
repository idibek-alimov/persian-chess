package tj.avlimov.game.service.player;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tj.avlimov.game.dto.player.PlayerCreateDto;
import tj.avlimov.game.exceptions.PlayerNotFoundException;
import tj.avlimov.game.model.player.HumanPlayer;
import tj.avlimov.game.model.player.Player;
import tj.avlimov.game.repository.PlayerRepository;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;

    public Long createPlayer(PlayerCreateDto dto){
        Player player = new HumanPlayer(1L);
        return playerRepository.save(player).getId();
    }

    public Player getPlayerById(Long playerId){
        return playerRepository.findById(playerId).orElseThrow(() -> new PlayerNotFoundException(String.format("Player with given id does not exist")));
    }
}
