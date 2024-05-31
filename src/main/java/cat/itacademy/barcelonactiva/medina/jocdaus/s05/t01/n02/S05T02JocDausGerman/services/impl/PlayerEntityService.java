package cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.services.impl;

import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.domain.PlayerEntity;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.dto.PlayerEntityDTO;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.exceptions.PlayerNameAlreadyExists;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.exceptions.PlayerNotFound;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.repositories.PlayerRepository;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.repositories.UserRepository;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.services.PlayerEntityServiceInterface;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerEntityService implements PlayerEntityServiceInterface {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    ModelMapper playerModelMapper;
    @Override
    public PlayerEntityDTO save(PlayerEntityDTO player) {
        String playerName = player.getPlayerName();
        if (playerName == null) {
            player.setPlayerName("Anonymous");
        }
        else if (playerRepository.findByPlayerName(playerName).isPresent()) {
            throw new PlayerNameAlreadyExists("Player name already in use");
        }
        return getPlayerDTOFrom(
                playerRepository.save(
                        getPlayerEntityFrom(player)));
    }

    @Override
    public PlayerEntityDTO update(long id, PlayerEntityDTO playerEntityDTO) throws PlayerNotFound {
        PlayerEntityDTO existingPlayer = findById(id);
        existingPlayer.setPlayerName(playerEntityDTO.getPlayerName());
        playerRepository.save(getPlayerEntityFrom(existingPlayer));
        return existingPlayer;
    }

    @Override
    public void deleteById(long id) throws PlayerNotFound {
        PlayerEntityDTO existingPlayer = findById(id);
        playerRepository.delete(getPlayerEntityFrom(existingPlayer));
    }

    @Override
    public PlayerEntityDTO findById(long id) throws PlayerNotFound {
        return playerRepository.findById(id).map(this::getPlayerDTOFrom).orElseThrow(() -> new PlayerNotFound("The player was not found"));
    }

    @Override
    public List<PlayerEntityDTO> findAll() {
        List<PlayerEntity> players = playerRepository.findAll();
        return players.stream().map(this::getPlayerDTOFrom).collect(Collectors.toList());
    }

    private PlayerEntityDTO getPlayerDTOFrom(PlayerEntity player) {
        return playerModelMapper.map(player, PlayerEntityDTO.class);
    }

    private PlayerEntity getPlayerEntityFrom(PlayerEntityDTO playerDTO) {
        return playerModelMapper.map(playerDTO, PlayerEntity.class);
    }

}
