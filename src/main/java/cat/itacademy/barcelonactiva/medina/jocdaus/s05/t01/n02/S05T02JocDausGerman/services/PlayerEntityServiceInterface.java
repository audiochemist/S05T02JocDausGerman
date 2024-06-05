package cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.services;

import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.dto.PlayerEntityDTO;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.exceptions.PlayerNotFound;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface PlayerEntityServiceInterface {

    List<PlayerEntityDTO> findAll();
    PlayerEntityDTO save(PlayerEntityDTO playerEntityDTO, Authentication authentication);
    PlayerEntityDTO update (long id, PlayerEntityDTO playerEntityDTO) throws PlayerNotFound;
    void deleteById(long id) throws PlayerNotFound;
    PlayerEntityDTO getBestWinRate();
    PlayerEntityDTO getWorstWinRate();
    List<PlayerEntityDTO> findAllByWinrate();

}
