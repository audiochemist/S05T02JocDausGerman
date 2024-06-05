package cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.security.service;

import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.domain.Role;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.domain.UserEntity;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.exceptions.BadCredentials;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.model.exceptions.PlayerNotFound;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.security.auth.*;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.repositories.UserRepository;
import cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02.S05T02JocDausGerman.security.service.AuthServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService implements AuthServiceInterface {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public UserEntity register(RegisterRequest request) {

        if (userRepository.findUserByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        if (userRepository.findUserByUserName(request.getUserName()).isPresent()) {
            throw new RuntimeException("User name already exists");
        }
        String userId = UUID.randomUUID().toString();

        UserEntity user = UserEntity.builder()
                .userId(userId)
                .userName(request.getUserName().isBlank() ? "ANONYMOUS" : request.getUserName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getUserName().isBlank() ? Role.ANONYMOUS :
                        (request.getUserName().startsWith("admin") ? Role.ADMIN : Role.USER))
                .build();

        return userRepository.save(user);

    }

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findUserByEmail(request.getEmail()).orElseThrow(() -> new BadCredentials("Incorrect credentials"));
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder().token(jwtToken).build();
    }
}
