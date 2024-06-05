package cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.exceptions;

import org.springframework.security.core.AuthenticationException;

public class BadCredentials extends AuthenticationException {

    public BadCredentials(String message) {
        super(message);
    }
}
