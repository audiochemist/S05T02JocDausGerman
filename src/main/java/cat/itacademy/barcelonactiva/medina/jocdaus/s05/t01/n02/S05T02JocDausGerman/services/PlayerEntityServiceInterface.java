package cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.services;

import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.dto.PlayerEntityDTO;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.exceptions.PlayerNotFound;

import java.util.List;

public interface PlayerEntityServiceInterface {

    PlayerEntityDTO save(PlayerEntityDTO playerEntityDTO);
    PlayerEntityDTO update (long id, PlayerEntityDTO playerEntityDTO) throws PlayerNotFound;
    void deleteById(long id) throws PlayerNotFound;
    PlayerEntityDTO findById(long id) throws PlayerNotFound;
    List<PlayerEntityDTO> findAll();
}
