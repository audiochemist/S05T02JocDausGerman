package cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.controllers;

import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.domain.GameEntity;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.dto.GameEntityDTO;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.services.impl.GameEntityService;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.services.impl.PlayerEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/players")
@RequiredArgsConstructor
public class GameController {

    @Autowired
    private GameEntityService gameService;

    //all games
    @GetMapping(path = "/allExistingGames")
    public ResponseEntity<List<GameEntityDTO>> getAllGames(Authentication authentication) {
        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
            throw  new InsufficientAuthenticationException("You dont have permissions");
        }
        List<GameEntityDTO> games = gameService.findAll();
        return ResponseEntity.ok().body(games);
    }

    //all games by player
    @GetMapping("/{playerId}/games")
    public ResponseEntity<?> getAllGames(@PathVariable("playerId") long playerId) {
        List<GameEntityDTO> gameDTOList = gameService.getByPlayerId(playerId);
        return new ResponseEntity<>(gameDTOList, HttpStatus.OK);
    }

    //create game
    @PostMapping("/{playerId}/games")
    public ResponseEntity<?> playGame(@PathVariable("playerId") long playerId) {
        GameEntityDTO gameDTO = gameService.playGame(playerId);
        return new ResponseEntity<>(gameDTO, HttpStatus.CREATED);
    }

    //delete all games of a player
    @DeleteMapping("/{playerId}/games")
    public ResponseEntity<?> deleteAllGames(@PathVariable Long playerId) {
        gameService.deleteByPlayer(playerId);
        return new ResponseEntity<>("Games have been deleted.", HttpStatus.OK);
    }

}
