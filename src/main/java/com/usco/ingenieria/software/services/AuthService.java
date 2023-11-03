package com.usco.ingenieria.software.services;

import com.usco.ingenieria.software.controllers.auth.dtos.AuthResponse;
import com.usco.ingenieria.software.controllers.auth.dtos.LoginRequest;
import com.usco.ingenieria.software.controllers.auth.dtos.RegisterRequest;

public interface AuthService {

    AuthResponse login(LoginRequest request);

    AuthResponse register(RegisterRequest request);

}
