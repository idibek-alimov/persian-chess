package tj.avlimov.game;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.configuration.IMockitoConfiguration;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import tj.avlimov.game.repository.BoardRepository;
import tj.avlimov.game.repository.GameRepository;
import tj.avlimov.game.service.game.BoardService;
import tj.avlimov.game.service.game.GameService;
import tj.avlimov.game.service.game.MoveService;
import tj.avlimov.game.service.player.PlayerService;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {

}
