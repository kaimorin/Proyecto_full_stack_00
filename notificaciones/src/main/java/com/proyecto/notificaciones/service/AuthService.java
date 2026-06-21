package com.proyecto.notificaciones.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.core.ParameterizedTypeReference;
import com.proyecto.notificaciones.dto.ApiResponse;

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
                        .host("login") 
                        .path("/api/v1/users/validate")
                        .queryParam("token", token)
                        .build())
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<ApiResponse<String>>() {})
                    .block();
        } catch (WebClientResponseException e) {
            
            try {
                ApiResponse<String> errorBody = e.getResponseBodyAs(new ParameterizedTypeReference<ApiResponse<String>>() {});
                if (errorBody != null) {
                    return errorBody;
                }
            } catch (Exception ignored) {
                
            }
            return new ApiResponse<>(e.getStatusCode().value(), "Error de autenticación: " + e.getMessage(), null);
        } catch (Exception e) {
            
            return new ApiResponse<>(500, "Error de comunicación con servicio Auth: " + e.getMessage(), null);
        }
    }
}