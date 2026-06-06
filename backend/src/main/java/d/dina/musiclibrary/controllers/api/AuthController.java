package d.dina.musiclibrary.controllers.api;

import d.dina.musiclibrary.dtos.auth.AuthResponse;
import d.dina.musiclibrary.dtos.auth.LoginRequest;
import d.dina.musiclibrary.dtos.auth.RegisterRequest;
import d.dina.musiclibrary.entities.Role;
import d.dina.musiclibrary.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import d.dina.musiclibrary.entities.User;
import d.dina.musiclibrary.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequest request
    ) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow();

        UserDetails userDetails =
                org.springframework.security.core.userdetails
                        .User
                        .withUsername(user.getUsername())
                        .password("")
                        .authorities(
                                "ROLE_" + user.getRole().name()
                        )
                        .build();

        String token =
                jwtService.generateToken(userDetails);

        return ResponseEntity.ok(
                new AuthResponse(
                        token,
                        user.getUsername(),
                        user.getRole().name()
                )
        );
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequest request
    ) {

        if (
                userRepository.existsByUsername(
                        request.getUsername()
                )
        ) {

            return ResponseEntity.badRequest()
                    .body("Username already exists");
        }

        User user = new User();

        user.setUsername(
                request.getUsername()
        );

        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );

        user.setRole(
                Role.EMPLOYEE
        );

        userRepository.save(user);

        return ResponseEntity.ok(
                "User registered successfully"
        );
    }
}