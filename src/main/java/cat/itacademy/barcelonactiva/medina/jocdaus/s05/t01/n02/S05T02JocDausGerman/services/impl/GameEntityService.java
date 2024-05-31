package cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.services.impl;


import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.domain.GameEntity;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.domain.PlayerEntity;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.dto.GameEntityDTO;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.repositories.GameRepository;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.repositories.PlayerRepository;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.services.GameEntityServiceInterface;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GameEntityService implements GameEntityServiceInterface {

    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private PlayerEntityService playerEntityService;

    @Autowired
    ModelMapper gameModelMapper;


    //TODO refactorizar linea para buscar juagdor en un metodo - lo utilizan todos los metodos igual

    @Transactional
    @Override
    public GameEntityDTO playGame(long playerID) {

        PlayerEntity playerSelected = playerRepository.findById(playerID)
                .orElseThrow(() -> new EntityNotFoundException("The player was not found"));
        GameEntity newGame = new GameEntity();
        newGame.play();
        newGame.setPlayer(playerSelected);
        playerSelected.addingGame(newGame);
        gameRepository.save(newGame);

        return getGameDTOFrom(newGame);
    }

    @Transactional
    @Override
    public List<GameEntityDTO> getByPlayerId(long playerID) {
        playerRepository.findById(playerID).
                orElseThrow(() -> new EntityNotFoundException("Id not found"));
        return gameRepository.findByPlayer_PlayerID(playerID).stream().
                map(this::getGameDTOFrom).collect(Collectors.toList());

    }
    @Transactional
    @Override
    public void deleteByPlayer(long playerID) {
        playerRepository.findById(playerID).orElseThrow(() -> new EntityNotFoundException("Id not found."));
        gameRepository.deleteByPlayer_PlayerID(playerID);
    }
//TODO Hacer una clase convertidora de modelos para ambos players y games
    private GameEntityDTO getGameDTOFrom(GameEntity gameEntity) {
        return gameModelMapper.map(gameEntity, GameEntityDTO.class);
    }

    private GameEntity getGameEntityFrom(GameEntityDTO gameEntityDTO) {
        return gameModelMapper.map(gameEntityDTO, GameEntity.class);
    }

}
