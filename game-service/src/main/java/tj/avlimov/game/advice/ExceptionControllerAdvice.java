package tj.avlimov.game.advice;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import tj.avlimov.game.exceptions.PlayerNotFoundException;

@ControllerAdvice
@ResponseBody

public class ExceptionControllerAdvice {
    @ExceptionHandler({PlayerNotFoundException.class})
    public ResponseEntity<String> handlePlayerNotFountException(Exception e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
