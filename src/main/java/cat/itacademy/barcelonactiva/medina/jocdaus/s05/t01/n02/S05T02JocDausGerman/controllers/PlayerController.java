package cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.controllers;


import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.domain.UserEntity;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.dto.PlayerEntityDTO;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.exceptions.UserNotFound;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.services.PlayerEntityServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PlayerController {

    @Autowired
    PlayerEntityServiceInterface playerService;

    @PostMapping("/addPlayers")
    public ResponseEntity<PlayerEntityDTO> addPlayer(@RequestBody PlayerEntityDTO playerDTO, Authentication authentication) {
        return new ResponseEntity<>(playerService.save(playerDTO, authentication), HttpStatus.CREATED);
    }

    @PutMapping("/players/update/{id}")
    public ResponseEntity<PlayerEntityDTO> updatePlayer(@PathVariable long id, @RequestBody PlayerEntityDTO playerDTO) {
        PlayerEntityDTO playerToUpdate = playerService.update(id, playerDTO);
        return new ResponseEntity<>(playerToUpdate, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<PlayerEntityDTO>> getAllPlayers(Authentication authentication) {
        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
            //TODO hacer excepcion personalizada
            throw new InsufficientAuthenticationException("You dont have permissions");
        }
        List<PlayerEntityDTO> players = playerService.findAll();
        return new ResponseEntity<>(players, HttpStatus.OK);
    }

    @DeleteMapping("players/delete/{id}")
    public ResponseEntity<?> deletePlayer(@PathVariable long id, Authentication authentication) {
        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
            //TODO hacer excepcion personalizada
            throw new InsufficientAuthenticationException("You dont have permissions");
        }
        playerService.deleteById(id);
        return new ResponseEntity<>("Player deleted.", HttpStatus.OK);
    }
}

