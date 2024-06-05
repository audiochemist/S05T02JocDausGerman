package cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.controllers;


import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.dto.PlayerEntityDTO;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.dto.UserEntityDTO;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.services.UserEntityServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    @Autowired
    UserEntityServiceInterface userService;

    @GetMapping("/users")
    public ResponseEntity<?> getSuccessAveragePlayers (Authentication authentication){
        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
            //TODO hacer excepcion personalizada
            throw  new InsufficientAuthenticationException("You dont have permissions");
        }
        List<UserEntityDTO> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }




}
