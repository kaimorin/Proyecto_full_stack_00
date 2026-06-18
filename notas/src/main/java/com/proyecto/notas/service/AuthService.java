package com.proyecto.notas.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.core.ParameterizedTypeReference;
import com.proyecto.notas.dto.ApiResponse;

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
        } catch (WebClientResponseException e) {
            // Si el servicio login responde un 401/400, intentamos leer su propia respuesta ApiResponse
            try {
                ApiResponse<String> errorBody = e.getResponseBodyAs(new ParameterizedTypeReference<ApiResponse<String>>() {});
                if (errorBody != null) {
                    return errorBody;
                }
            } catch (Exception ignored) {
                // Si falla al parsear el cuerpo, usamos el código HTTP de la excepción
            }
            return new ApiResponse<>(e.getStatusCode().value(), "Error de autenticación: " + e.getMessage(), null);
        } catch (Exception e) {
            // Errores de conexión, timeout o Eureka no encuentra el servicio
            return new ApiResponse<>(500, "Error de comunicación con servicio Auth: " + e.getMessage(), null);
        }
    }
}