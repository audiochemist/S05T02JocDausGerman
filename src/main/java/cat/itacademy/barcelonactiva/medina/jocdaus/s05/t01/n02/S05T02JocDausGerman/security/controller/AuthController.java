package cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.security.controller;


import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.domain.UserEntity;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.security.auth.*;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.security.service.AuthServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private AuthServiceInterface authService;

    @PostMapping("/register")
    public ResponseEntity<UserEntity> register(@RequestBody RegisterRequest request){

        return ResponseEntity.ok(authService.register(request));

    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request){
        return ResponseEntity.ok(authService.authenticate(request));

    }

}
