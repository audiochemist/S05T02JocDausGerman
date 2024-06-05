package cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.services.impl;

import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.domain.PlayerEntity;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.domain.Role;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.domain.UserEntity;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.dto.PlayerEntityDTO;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.exceptions.PlayerNameAlreadyExists;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.exceptions.PlayerNotFound;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.exceptions.UserNotFound;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.repositories.PlayerRepository;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.repositories.UserRepository;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.services.PlayerEntityServiceInterface;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
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
    public PlayerEntityDTO save(PlayerEntityDTO player, Authentication authentication) throws UserNotFound {
        String userEmail = authentication.getName();
        UserEntity user = userRepository.findUserByEmail(userEmail)
                .orElseThrow(() -> new UserNotFound("User not found"));

        // Asigna el userId del usuario autenticado al playerDTO
        PlayerEntity newPlayer = getPlayerEntityFrom(player);
        newPlayer.setUserId(user.getUserId());
        newPlayer.setUser(user);


        String playerName = player.getPlayerName();
        if (user.getRole() != Role.ANONYMOUS && !playerName.isBlank() && playerRepository.findByPlayerName(playerName).isPresent()) {
            throw new PlayerNameAlreadyExists("Player name already in use");
        }

        newPlayer.setPlayerName(
                user.getRole() == Role.ANONYMOUS ? "Anonymous" + new Random().nextInt(9000) + 1000 :
                        playerName.isBlank() ? "Anonymous" : playerName
        );

        return getPlayerDTOFrom(playerRepository.save(newPlayer));
    }

    @Override
    public PlayerEntityDTO update(long id, PlayerEntityDTO playerEntityDTO) throws PlayerNotFound {
        PlayerEntity existingPlayer = playerRepository.findById(id).orElseThrow(() -> new PlayerNotFound("Player was not found"));
        if (playerRepository.findByPlayerName(existingPlayer.getPlayerName()).isPresent()) {
            throw new PlayerNameAlreadyExists("Player name already in use");
        }
        existingPlayer.setPlayerName(playerEntityDTO.getPlayerName());
        playerRepository.save(existingPlayer);
        return getPlayerDTOFrom(existingPlayer);
    }

    @Override
    public void deleteById(long id) throws PlayerNotFound {
        PlayerEntity existingPlayer = playerRepository.findById(id).orElseThrow(() -> new PlayerNotFound("Player was not found"));
        playerRepository.delete(existingPlayer);
    }

    @Override
    public List<PlayerEntityDTO> findAll() {
        List<PlayerEntity> players = playerRepository.findAll();
        if (players.isEmpty()){
            throw new PlayerNotFound("No players found in the system");
        }
        return players.stream().map(this::getPlayerDTOFrom).collect(Collectors.toList());
    }

    @Override
    public List<PlayerEntityDTO> findAllByWinrate() {
        List<PlayerEntity> players = playerRepository.findAllByOrderByWinRateDesc();
        return players.stream().map(this::getPlayerDTOFrom).collect(Collectors.toList());
    }

    @Override
    public PlayerEntityDTO getBestWinRate() {

        PlayerEntity player = playerRepository.findTopByOrderByWinRateDesc();
        if (player == null) throw new PlayerNotFound("No players found");
        return getPlayerDTOFrom(player);
    }

    @Override
    public PlayerEntityDTO getWorstWinRate() {
        PlayerEntity player = playerRepository.findTopByOrderByWinRateAsc();

        if (player == null) throw new PlayerNotFound("No players found");
        return getPlayerDTOFrom(player);
    }


    private PlayerEntityDTO getPlayerDTOFrom(PlayerEntity player) {
        return playerModelMapper.map(player, PlayerEntityDTO.class);
    }

    private PlayerEntity getPlayerEntityFrom(PlayerEntityDTO playerDTO) {
        return playerModelMapper.map(playerDTO, PlayerEntity.class);
    }

}
