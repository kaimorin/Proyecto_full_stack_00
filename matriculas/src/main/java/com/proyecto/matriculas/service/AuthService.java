package com.proyecto.matriculas.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.proyecto.matriculas.dto.ApiResponse;


import org.springframework.core.ParameterizedTypeReference;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final WebClient.Builder webClientBuilder;

    public ApiResponse<String> validateToken(String token) {
        try {
            return webClientBuilder.build()
                    .get()
                    .uri(uriBuilder -> uriBuilder
                        .scheme("http")
                        .host("login") // nombre lógico en Eureka
                        .path("/api/v1/users/validate")
                        .queryParam("token", token)
                        .build())
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<ApiResponse<String>>() {})
                    .block();
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error al validar token: " + e.getMessage(), null);
        }
    }
}