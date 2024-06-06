package cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.TestingServices;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.domain.GameEntity;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.domain.PlayerEntity;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.dto.GameEntityDTO;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.exceptions.PlayerNotFound;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.repositories.GameRepository;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.repositories.PlayerRepository;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.services.impl.GameEntityService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("unchecked")
@ExtendWith(MockitoExtension.class)
public class GameEntityServiceTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private ModelMapper gameModelMapper;

    @InjectMocks
    private GameEntityService gameEntityService;

    @Test
    void testPlayGame() {
        PlayerEntity player = new PlayerEntity();
        player.setPlayerID(1L);

        when(playerRepository.findById(1L)).thenReturn(Optional.of(player));
        when(gameRepository.save(any(GameEntity.class))).thenReturn(new GameEntity());
        when(gameModelMapper.map(any(GameEntity.class), any(Class.class))).thenReturn(new GameEntityDTO());

        GameEntityDTO gameDTO = gameEntityService.playGame(1L);

        assertNotNull(gameDTO);
    }

    @Test
    void testPlayGame_PlayerNotFound() {
        when(playerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            gameEntityService.playGame(1L);
        });
    }

    @Test
    void testGetByPlayerId() {
        PlayerEntity player = new PlayerEntity();
        player.setPlayerID(1L);
        GameEntity game = new GameEntity();
        List<GameEntity> games = new ArrayList<>();
        games.add(game);

        when(playerRepository.findById(1L)).thenReturn(Optional.of(player));
        when(gameRepository.findByPlayer_PlayerID(1L)).thenReturn(games);
        when(gameModelMapper.map(any(GameEntity.class), any(Class.class))).thenReturn(new GameEntityDTO());

        List<GameEntityDTO> gameDTOs = gameEntityService.getByPlayerId(1L);

        assertFalse(gameDTOs.isEmpty());
    }

    @Test
    void testGetByPlayerId_PlayerNotFound() {
        when(playerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(PlayerNotFound.class, () -> {
            gameEntityService.getByPlayerId(1L);
        });
    }

    @Test
    void testDeleteByPlayer() {
        PlayerEntity player = new PlayerEntity();
        player.setPlayerID(1L);
        GameEntity game = new GameEntity();
        List<GameEntity> games = new ArrayList<>();
        games.add(game);

        when(playerRepository.findById(1L)).thenReturn(Optional.of(player));
        when(gameRepository.findByPlayer_PlayerID(1L)).thenReturn(games);

        gameEntityService.deleteByPlayer(1L);

        verify(gameRepository, times(1)).deleteAllByPlayer(player);
    }

    @Test
    void testDeleteByPlayer_PlayerNotFound() {
        when(playerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(PlayerNotFound.class, () -> {
            gameEntityService.deleteByPlayer(1L);
        });
    }

    @Test
    void testFindAll() {
        GameEntity game = new GameEntity();
        List<GameEntity> games = new ArrayList<>();
        games.add(game);

        when(gameRepository.findAll()).thenReturn(games);
        when(gameModelMapper.map(any(GameEntity.class), any(Class.class))).thenReturn(new GameEntityDTO());

        List<GameEntityDTO> gameDTOs = gameEntityService.findAll();

        assertFalse(gameDTOs.isEmpty());
    }
}