package cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.security.service;


import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.domain.UserEntity;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.security.auth.*;

public interface AuthServiceInterface {

    public UserEntity register (RegisterRequest request);

    public AuthResponse authenticate (AuthRequest request);

}
