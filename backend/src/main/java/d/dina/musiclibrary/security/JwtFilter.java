package d.dina.musiclibrary.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter
        extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final CustomUserDetailsService
            userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader =
                request.getHeader("Authorization");

        System.out.println("=================================");
        System.out.println("REQUEST: " + request.getMethod()
                + " " + request.getRequestURI());
        System.out.println("AUTH HEADER: " + authHeader);

        final String jwt;

        final String username;

        if (authHeader == null
                || !authHeader.startsWith("Bearer ")) {

            System.out.println("NO TOKEN FOUND");

            filterChain.doFilter(request, response);

            return;
        }

        jwt = authHeader.substring(7);

        username =
                jwtService.extractUsername(jwt);

        System.out.println("USERNAME FROM TOKEN: " + username);

        if (username != null
                && SecurityContextHolder
                .getContext()
                .getAuthentication() == null) {

            UserDetails userDetails =
                    userDetailsService
                            .loadUserByUsername(username);

            System.out.println(
                    "AUTHORITIES: "
                            + userDetails.getAuthorities()
            );

            if (jwtService.isTokenValid(
                    jwt,
                    userDetails
            )) {

                System.out.println("TOKEN VALID");

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );

                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authToken);

                System.out.println(
                        "AUTHENTICATION SET"
                );
            }
        }

        filterChain.doFilter(request, response);
    }
}