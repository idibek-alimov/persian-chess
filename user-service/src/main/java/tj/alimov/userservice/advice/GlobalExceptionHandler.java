package tj.alimov.userservice.advice;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tj.alimov.userservice.exception.DuplicateUsernameException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DuplicateUsernameException.class)
    public ResponseEntity<String> handleDuplicateUsername(DuplicateUsernameException exception){
        System.out.println("Duplicate Username Exception caught");
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exception.getMessage());
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException exception){
        System.out.println("data Integrity violation exception caught");
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("Data Integrity exception. Probably use with this username already exists");//exception.getMessage());
    }
}
