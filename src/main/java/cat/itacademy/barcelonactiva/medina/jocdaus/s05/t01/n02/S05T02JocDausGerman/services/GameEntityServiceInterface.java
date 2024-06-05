package cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.services;

import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.dto.GameEntityDTO;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.dto.PlayerEntityDTO;

import java.util.List;

public interface GameEntityServiceInterface {

    List<GameEntityDTO> findAll();
    GameEntityDTO playGame(long playerID);
    List<GameEntityDTO> getByPlayerId(long playerID);
    void deleteByPlayer(long playerID);

}
