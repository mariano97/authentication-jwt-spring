package com.usco.ingenieria.software.services.impl;

import com.usco.ingenieria.software.controllers.auth.dtos.AuthResponse;
import com.usco.ingenieria.software.controllers.auth.dtos.LoginRequest;
import com.usco.ingenieria.software.controllers.auth.dtos.RegisterRequest;
import com.usco.ingenieria.software.security.jwt.JwtService;
import com.usco.ingenieria.software.domain.user.Role;
import com.usco.ingenieria.software.services.AuthService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.usco.ingenieria.software.domain.user.User;
import com.usco.ingenieria.software.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        AuthResponse response = null;
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow();
            String token = jwtService.getToken(user);
            response = AuthResponse.builder()
                    .token(token)
                    .statusCode(HttpStatus.OK)
                    .build();
        } catch (AuthenticationException e) {
            response = AuthResponse.builder()
                    .error(ExceptionUtils.getRootCauseMessage(e))
                    .statusCode(HttpStatus.FORBIDDEN)
                    .stackTrace(ExceptionUtils.getStackTrace(e))
                    .build();
        } catch (Exception e) {
            response = AuthResponse.builder()
                    .error(ExceptionUtils.getRootCauseMessage(e))
                    .statusCode(HttpStatus.BAD_REQUEST)
                    .stackTrace(ExceptionUtils.getStackTrace(e))
                    .build();
        }
        return response;

    }

    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
            .username(request.getUsername())
            .password(passwordEncoder.encode( request.getPassword()))
            .firstname(request.getFirstname())
            .lastname(request.getLastname())
            .role(Role.USER)
            .build();

        AuthResponse response = null;
        try {
            userRepository.save(user);
            response = AuthResponse.builder()
                    .token(jwtService.getToken(user))
                    .statusCode(HttpStatus.OK)
                    .build();
        } catch (Exception e) {
            response = AuthResponse.builder()
                    .error(ExceptionUtils.getRootCauseMessage(e))
                    .stackTrace(ExceptionUtils.getStackTrace(e))
                    .statusCode(HttpStatus.BAD_REQUEST)
                    .build();
        }

        return response;
        
    }

}
