package cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.exceptions;

public class PlayerNameAlreadyExists extends RuntimeException{
    public PlayerNameAlreadyExists() {
    }
    public PlayerNameAlreadyExists(String message) {
        super(message);
    }
}
