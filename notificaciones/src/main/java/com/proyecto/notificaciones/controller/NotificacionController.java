package com.proyecto.notificaciones.controller;

import jakarta.validation.Valid;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proyecto.notificaciones.model.Notificacion;
import com.proyecto.notificaciones.dto.NotificacionDto;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.proyecto.notificaciones.service.NotificacionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controlador REST para el microservicio de notificaciones.
 *
 * Expone endpoints que permiten listar, crear, obtener, marcar como leídas
 * y eliminar notificaciones.
 */
@RestController
@RequestMapping("/api/notificaciones")
@Tag(name = "Notificaciones", description = "API para gestionar notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService service;

    /**
     * Lista todas las notificaciones.
     */
    @GetMapping
    @Operation(summary = "Listar todas las notificaciones", description = "Obtiene la lista completa de notificaciones")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Notificaciones obtenidas exitosamente"),
        @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    public ResponseEntity<com.proyecto.notificaciones.dto.ApiResponse<List<Notificacion>>> listar() {
        List<Notificacion> lista = service.listar();
        com.proyecto.notificaciones.dto.ApiResponse<List<Notificacion>> response = 
            new com.proyecto.notificaciones.dto.ApiResponse<>(200, "Listado de notificaciones", lista);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener notificación por ID", description = "Obtiene una notificación específica")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Notificación encontrada"),
        @ApiResponse(responseCode = "404", description = "Notificación no encontrada")
    })
    public ResponseEntity<com.proyecto.notificaciones.dto.ApiResponse<Notificacion>> obtener(
            @Parameter(description = "ID de la notificación", required = true, example = "1")
            @PathVariable Long id) {
        return service.buscarPorId(id)
                .map(notif -> ResponseEntity.ok(
                    new com.proyecto.notificaciones.dto.ApiResponse<>(200, "Notificación encontrada", notif)))
                .orElse(ResponseEntity.status(404).body(
                    new com.proyecto.notificaciones.dto.ApiResponse<>(404, "Notificación no encontrada", null)));
    }

    @PostMapping
    @Operation(summary = "Crear notificación", description = "Registra una nueva notificación")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Notificación creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<com.proyecto.notificaciones.dto.ApiResponse<Notificacion>> crear(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la notificación",
                    required = true,
                    content = @Content(schema = @Schema(implementation = NotificacionDto.class))
            )
            @Valid @RequestBody NotificacionDto dto) {
        Notificacion nueva = service.crear(dto);
        com.proyecto.notificaciones.dto.ApiResponse<Notificacion> response = 
            new com.proyecto.notificaciones.dto.ApiResponse<>(201, "Notificación creada", nueva);
        return ResponseEntity.status(201).body(response);
    }

    @PatchMapping("/{id}/leer")
    @Operation(summary = "Marcar como leída", description = "Marca una notificación como leída")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Notificación marcada como leída"),
        @ApiResponse(responseCode = "404", description = "Notificación no encontrada")
    })
    public ResponseEntity<com.proyecto.notificaciones.dto.ApiResponse<Void>> marcarComoLeida(
            @Parameter(description = "ID de la notificación", required = true, example = "1")
            @PathVariable Long id) {
        if (service.buscarPorId(id).isPresent()) {
            service.marcarComoLeida(id);
            return ResponseEntity.ok(
                new com.proyecto.notificaciones.dto.ApiResponse<>(200, "Notificación marcada como leída", null));
        }
        return ResponseEntity.status(404).body(
            new com.proyecto.notificaciones.dto.ApiResponse<>(404, "Notificación no encontrada", null));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar notificación", description = "Elimina una notificación por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Notificación eliminada correctamente"),
        @ApiResponse(responseCode = "404", description = "Notificación no encontrada")
    })
    public ResponseEntity<com.proyecto.notificaciones.dto.ApiResponse<Void>> eliminar(
            @Parameter(description = "ID de la notificación", required = true, example = "1")
            @PathVariable Long id) {
        if (service.buscarPorId(id).isPresent()) {
            service.eliminar(id);
            return ResponseEntity.ok(
                new com.proyecto.notificaciones.dto.ApiResponse<>(200, "Notificación eliminada", null));
        }
        return ResponseEntity.status(404).body(
            new com.proyecto.notificaciones.dto.ApiResponse<>(404, "Notificación no encontrada", null));
    }
}

