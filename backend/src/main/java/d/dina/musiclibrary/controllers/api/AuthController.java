package d.dina.musiclibrary.controllers.api;

import d.dina.musiclibrary.dtos.auth.AuthResponse;
import d.dina.musiclibrary.dtos.auth.LoginRequest;
import d.dina.musiclibrary.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;

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

        UserDetails user =
                org.springframework.security.core.userdetails
                        .User
                        .withUsername(request.getUsername())
                        .password("")
                        .authorities("USER")
                        .build();

        String token =
                jwtService.generateToken(user);

        return ResponseEntity.ok(
                new AuthResponse(token)
        );
    }
}