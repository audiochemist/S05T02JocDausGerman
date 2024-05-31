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
    @Autowired
    private PlayerEntityService playerService;

    @PostMapping("/{id}/games")
    public ResponseEntity<?> playGame (@PathVariable long playerID){
        GameEntityDTO gameDTO = gameService.playGame(playerID);
        return new ResponseEntity<>(gameDTO, HttpStatus.CREATED);
    }
    @GetMapping("/{id}/games")
    public ResponseEntity<?> getAllGames (@PathVariable long id){
        List<GameEntityDTO> gameDTOList = gameService.getByPlayerId(id);
        return new ResponseEntity<>(gameDTOList, HttpStatus.OK);
    }
    @DeleteMapping("/{id}/games")
    public ResponseEntity<?> deleteAllGames (@PathVariable long id){
        gameService.deleteByPlayer(id);
        return new ResponseEntity<>("Games have been deleted.", HttpStatus.OK);
    }

}
