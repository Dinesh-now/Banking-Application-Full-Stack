package com.example.BankingApplicationFSD.security;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.BankingApplicationFSD.entity.User;
import com.example.BankingApplicationFSD.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

private final JwtService jwtService;
private final UserRepository userRepository;

public JwtAuthenticationFilter(
        JwtService jwtService,
        UserRepository userRepository) {

    this.jwtService = jwtService;
    this.userRepository = userRepository;
}

@Override
protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain)
        throws ServletException, IOException {

    String authHeader = request.getHeader("Authorization");

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        filterChain.doFilter(request, response);
        return;
    }

    String token = authHeader.substring(7);

    try {
        String email = jwtService.extractEmail(token);

        if (email != null
                && SecurityContextHolder.getContext().getAuthentication() == null
                && jwtService.isTokenValid(token, email)) {

            Optional<User> optionalUser =
                    userRepository.findByEmail(email);

            if (optionalUser.isPresent()) {

                User user = optionalUser.get();

                SimpleGrantedAuthority authority =
                        new SimpleGrantedAuthority("ROLE_" + user.getRole());

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                email,
                                null,
                                Collections.singletonList(authority));

                authentication.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request));

                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
            }
        }

    } catch (Exception e) {
        SecurityContextHolder.clearContext();
    }

    filterChain.doFilter(request, response);
}


}
