package com.proyecto.notificaciones.controller;

import jakarta.validation.Valid;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proyecto.notificaciones.model.Notificacion;
import com.proyecto.notificaciones.dto.NotificacionDto;
import com.proyecto.notificaciones.service.NotificacionService;
import com.proyecto.notificaciones.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    private final NotificacionService service;
    private final AuthService authService;

    public NotificacionController(NotificacionService service, AuthService authService) {
        this.service = service;
        this.authService = authService;
    }

    // * Método Listar Notificaciones Existentes * //

    @GetMapping

    @Operation(summary = "Listar todas las notificaciones", description = "Obtiene la lista completa de notificaciones")
    public ResponseEntity<com.proyecto.notificaciones.dto.ApiResponse<List<Notificacion>>> listar(@RequestHeader("Authorization") String authHeader) {

        String token = authHeader.replace("Bearer ", "");
        com.proyecto.notificaciones.dto.ApiResponse<String> validationResponse = authService.validateToken(token);

        if (validationResponse == null || validationResponse.getCode() != 200) {
            com.proyecto.notificaciones.dto.ApiResponse<List<Notificacion>> errorResponse =
                    new com.proyecto.notificaciones.dto.ApiResponse<>(401, "Token inválido", null);
            return ResponseEntity.status(401).body(errorResponse);
        }

        List<Notificacion> lista = service.listar();
        com.proyecto.notificaciones.dto.ApiResponse<List<Notificacion>> response =
                new com.proyecto.notificaciones.dto.ApiResponse<>(200, "Listado de notificaciones", lista);
        return ResponseEntity.ok(response);
    }

    // * Método Obtener Notificación por ID * //

    @GetMapping("/{id}")
    @Operation(summary = "Obtener notificación por ID", description = "Obtiene una notificación específica")

    public ResponseEntity<com.proyecto.notificaciones.dto.ApiResponse<Notificacion>> obtener(@RequestHeader("Authorization") String authHeader, @PathVariable Long id) {
        String token = authHeader.replace("Bearer ", "");
        com.proyecto.notificaciones.dto.ApiResponse<String> validationResponse = authService.validateToken(token);

        if (validationResponse == null || validationResponse.getCode() != 200) {
            com.proyecto.notificaciones.dto.ApiResponse<Notificacion> errorResponse =
                new com.proyecto.notificaciones.dto.ApiResponse<>(401, "Token inválido", null);
            return ResponseEntity.status(401).body(errorResponse);
        }

        return service.buscarPorId(id)
                .map(notif -> ResponseEntity.ok(
                    new com.proyecto.notificaciones.dto.ApiResponse<>(200, "Notificación encontrada", notif)))
                .orElse(ResponseEntity.status(404).body(
                    new com.proyecto.notificaciones.dto.ApiResponse<>(404, "Notificación no encontrada", null)));
    }

    // * Método Crear Notificación Nueva * //

    @PostMapping
    @Operation(summary = "Crear notificación", description = "Registra una nueva notificación")

    public ResponseEntity<com.proyecto.notificaciones.dto.ApiResponse<Notificacion>> crear(@RequestHeader("Authorization") String authHeader, @Valid @RequestBody NotificacionDto dto){
        String token = authHeader.replace("Bearer ", "");
        com.proyecto.notificaciones.dto.ApiResponse<String> validationResponse = authService.validateToken(token);

        if (validationResponse == null || validationResponse.getCode() != 200) {
            com.proyecto.notificaciones.dto.ApiResponse<Notificacion> errorResponse =
                new com.proyecto.notificaciones.dto.ApiResponse<>(401, "Token inválido", null);
            return ResponseEntity.status(401).body(errorResponse);
        }

        Notificacion nueva = service.crear(dto);
        com.proyecto.notificaciones.dto.ApiResponse<Notificacion> response =
            new com.proyecto.notificaciones.dto.ApiResponse<>(201, "Notificación creada", nueva);
        return ResponseEntity.status(201).body(response);

    }

    // * Método Marcar Notificación como Leída * //

    @PatchMapping("/{id}/leer")
    @Operation(summary = "Marcar como leída", description = "Marca una notificación como leída")

    public ResponseEntity<com.proyecto.notificaciones.dto.ApiResponse<Void>> marcarComoLeida(@RequestHeader("Authorization") String authHeader, @PathVariable Long id){

        String token = authHeader.replace("Bearer ", "");
        com.proyecto.notificaciones.dto.ApiResponse<String> validationResponse = authService.validateToken(token);

        if (validationResponse == null || validationResponse.getCode() != 200) {
            com.proyecto.notificaciones.dto.ApiResponse<Void> errorResponse =
                new com.proyecto.notificaciones.dto.ApiResponse<>(401, "Token inválido", null);
            return ResponseEntity.status(401).body(errorResponse);
        }

        if (service.buscarPorId(id).isPresent()) {
            service.marcarComoLeida(id);
            return ResponseEntity.ok(
                new com.proyecto.notificaciones.dto.ApiResponse<>(200, "Notificación marcada como leída", null));
        }
        return ResponseEntity.status(404).body(
            new com.proyecto.notificaciones.dto.ApiResponse<>(404, "Notificación no encontrada", null));
    }

    // * Método Eliminar Notificación * //

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar notificación", description = "Elimina una notificación por ID")

    public ResponseEntity<com.proyecto.notificaciones.dto.ApiResponse<Void>> eliminar(@PathVariable Long id, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        com.proyecto.notificaciones.dto.ApiResponse<String> validationResponse = authService.validateToken(token);

        if (validationResponse == null || validationResponse.getCode() != 200) {
            com.proyecto.notificaciones.dto.ApiResponse<Void> errorResponse =
                new com.proyecto.notificaciones.dto.ApiResponse<>(401, "Token inválido", null);
            return ResponseEntity.status(401).body(errorResponse);
        }

        if (service.buscarPorId(id).isPresent()) {
            service.eliminar(id);
            return ResponseEntity.ok(
                new com.proyecto.notificaciones.dto.ApiResponse<>(200, "Notificación eliminada", null));
        }
        return ResponseEntity.status(404).body(
            new com.proyecto.notificaciones.dto.ApiResponse<>(404, "Notificación no encontrada", null));
    }
}