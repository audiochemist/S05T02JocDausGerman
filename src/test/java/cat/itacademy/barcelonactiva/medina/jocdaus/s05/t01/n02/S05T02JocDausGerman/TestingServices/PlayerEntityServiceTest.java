package cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.TestingServices;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.domain.PlayerEntity;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.domain.Role;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.domain.UserEntity;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.dto.PlayerEntityDTO;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.exceptions.PlayerNameAlreadyExists;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.exceptions.PlayerNotFound;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.exceptions.UserNotFound;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.repositories.PlayerRepository;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.repositories.UserRepository;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.services.impl.PlayerEntityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("unchecked")
public class PlayerEntityServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper playerModelMapper;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private PlayerEntityService playerEntityService;

    @BeforeEach
    void setUp() {
        lenient().when(playerModelMapper.map(any(), any())).thenReturn(new PlayerEntity());
    }


    @Test
    void testSavePlayer() throws UserNotFound {
        UserEntity user = new UserEntity();
        user.setUserId("userId");
        user.setEmail("test@test.com");
        user.setRole(Role.USER);

        PlayerEntity player = new PlayerEntity();
        player.setPlayerID(1L);

        when(authentication.getName()).thenReturn("test@test.com");
        when(userRepository.findUserByEmail("test@test.com")).thenReturn(Optional.of(user));
        when(playerRepository.save(any(PlayerEntity.class))).thenReturn(player);
        when(playerModelMapper.map(any(PlayerEntity.class), any(Class.class))).thenReturn(new PlayerEntityDTO());

        PlayerEntityDTO playerDTO = new PlayerEntityDTO();
        playerDTO.setPlayerName("PlayerName");

        PlayerEntityDTO result = playerEntityService.save(playerDTO, authentication);

        assertNotNull(result);
    }

    @Test
    void testSavePlayer_UserNotFound() {
        when(authentication.getName()).thenReturn("test@test.com");
        when(userRepository.findUserByEmail("test@test.com")).thenReturn(Optional.empty());

        assertThrows(UserNotFound.class, () -> {
            playerEntityService.save(new PlayerEntityDTO(), authentication);
        });
    }


    @Test
    void testUpdatePlayer_PlayerNotFound() {
        when(playerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(PlayerNotFound.class, () -> {
            playerEntityService.update(1L, new PlayerEntityDTO());
        });
    }

    @Test
    void testDeletePlayer() throws PlayerNotFound {
        PlayerEntity player = new PlayerEntity();
        player.setPlayerID(1L);

        when(playerRepository.findById(1L)).thenReturn(Optional.of(player));

        playerEntityService.deleteById(1L);

        verify(playerRepository, times(1)).delete(player);
    }

    @Test
    void testDeletePlayer_PlayerNotFound() {
        when(playerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(PlayerNotFound.class, () -> {
            playerEntityService.deleteById(1L);
        });
    }
}