package com.usco.ingenieria.software.controllers.auth.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private HttpStatusCode statusCode;
    private String token;
    private String error;
    private String stackTrace;

    public int getStatusCode() {
        return statusCode.value();
    }
}
