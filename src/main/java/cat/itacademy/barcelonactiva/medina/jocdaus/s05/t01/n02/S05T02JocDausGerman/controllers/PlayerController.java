package cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.controllers;


import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.dto.PlayerEntityDTO;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.services.PlayerEntityServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PlayerController {

    @Autowired
    PlayerEntityServiceInterface playerService;

    @PostMapping("/addPlayers")
    public ResponseEntity<PlayerEntityDTO> addPlayer(@RequestBody PlayerEntityDTO playerDTO){
        return new ResponseEntity<>(playerService.save(playerDTO), HttpStatus.CREATED);
    }

    @PutMapping("/players/update/{id}")
    public ResponseEntity<PlayerEntityDTO> updatePlayer(@PathVariable long id, @RequestBody PlayerEntityDTO playerDTO) {
        PlayerEntityDTO playerToUpdate = playerService.update(id,playerDTO);
        return new ResponseEntity<>(playerToUpdate,HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<PlayerEntityDTO>> getAllPlayers (){
        List<PlayerEntityDTO> players = playerService.findAll();
        return new ResponseEntity<>(players,HttpStatus.OK);
    }

    @DeleteMapping("players/delete/{id}")
    public ResponseEntity<?> deletePlayer (@PathVariable long id){
        playerService.deleteById(id);
        return new ResponseEntity<>("Player deleted.",HttpStatus.OK);
    }



}
