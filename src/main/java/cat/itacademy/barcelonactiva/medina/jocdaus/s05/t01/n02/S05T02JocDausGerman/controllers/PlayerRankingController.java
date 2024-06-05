package cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.controllers;

import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.domain.PlayerEntity;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.dto.PlayerEntityDTO;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.services.PlayerEntityServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ranking")
@RequiredArgsConstructor
public class PlayerRankingController {

    @Autowired
    PlayerEntityServiceInterface playerService;

    @GetMapping("/winRateAverage")
    public ResponseEntity<?> getSuccessAveragePlayers (){
        List<PlayerEntityDTO> players = playerService.findAllByWinrate();
        return new ResponseEntity<>(players, HttpStatus.OK);
    }

    @GetMapping("/winner")
    public ResponseEntity<?> getWinner (){
        PlayerEntityDTO winner = playerService.getBestWinRate();
        return new ResponseEntity<>(winner, HttpStatus.OK);
    }

    @GetMapping("/loser")
    public ResponseEntity<?> getLoser (){
        PlayerEntityDTO loser = playerService.getWorstWinRate();
        return new ResponseEntity<>(loser, HttpStatus.OK);
    }

}
