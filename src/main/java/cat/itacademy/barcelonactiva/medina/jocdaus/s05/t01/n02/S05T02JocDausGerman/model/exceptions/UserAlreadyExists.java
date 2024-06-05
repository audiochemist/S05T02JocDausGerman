package cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.exceptions;

public class UserAlreadyExists extends RuntimeException{

    public UserAlreadyExists(String message) {
        super(message);
    }
}
