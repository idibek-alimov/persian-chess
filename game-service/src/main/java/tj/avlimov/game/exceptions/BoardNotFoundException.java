package tj.avlimov.game.exceptions;

public class BoardNotFoundException extends RuntimeException{
    public BoardNotFoundException(String message){
        super(message);
    }
}
