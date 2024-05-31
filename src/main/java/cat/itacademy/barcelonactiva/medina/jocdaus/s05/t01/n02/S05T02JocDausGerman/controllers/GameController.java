package cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.controllers;

import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.domain.GameEntity;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.dto.GameEntityDTO;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.services.impl.GameEntityService;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.services.impl.PlayerEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/players")
@RequiredArgsConstructor
public class GameController {

    @Autowired
    private GameEntityService gameService;

    @PostMapping("/{playerId}/games")
    public ResponseEntity<?> playGame(@PathVariable("playerId") long playerId){
        GameEntityDTO gameDTO = gameService.playGame(playerId);
        return new ResponseEntity<>(gameDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{playerId}/games")
    public ResponseEntity<?> getAllGames(@PathVariable("playerId") long playerId){
        List<GameEntityDTO> gameDTOList = gameService.getByPlayerId(playerId);
        return new ResponseEntity<>(gameDTOList, HttpStatus.OK);
    }

    @DeleteMapping("/{playerId}/games")
    public ResponseEntity<?> deleteAllGames(@PathVariable("playerId") long playerId){
        gameService.deleteByPlayer(playerId);
        return new ResponseEntity<>("Games have been deleted.", HttpStatus.OK);
    }

}
