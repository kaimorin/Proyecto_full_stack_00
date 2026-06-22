package com.proyecto.notificaciones.controller;

import com.proyecto.notificaciones.dto.NotificacionDto;
import com.proyecto.notificaciones.service.NotificacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
@RequiredArgsConstructor
@Tag(name = "Notificaciones", description = "Controlador para la gestión de notificaciones del sistema")
public class NotificacionController {

    private final NotificacionService service;

    @GetMapping
    @Operation(summary = "Listar todas las notificaciones", description = "Permite obtener un listado completo de todas las notificaciones registradas")
    public ResponseEntity<List<NotificacionDto>> listarTodos() {
        try {
            return ResponseEntity.ok(service.listarTodos());
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener notificación por ID", description = "Permite buscar una notificación específica mediante su identificador único")
    public ResponseEntity<NotificacionDto> obtenerPorId(@PathVariable Long id) {
        try {
            return service.obtenerPorId(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/origen")
    @Operation(summary = "Obtener notificación por origen", description = "Permite buscar notificaciones filtrándolas por el nombre del sistema u origen que las generó")
    public ResponseEntity<NotificacionDto> obtenerPorOrigen(@PathVariable String origen) {
        try {
            return service.obtenerPorOrigen(origen)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping
    @Operation(summary = "Crear nueva notificación", description = "Permite registrar una nueva notificación dentro del sistema")
    public ResponseEntity<NotificacionDto> crear(@RequestBody NotificacionDto dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto));
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar notificación", description = "Permite modificar los datos de una notificación existente utilizando su ID")
    public ResponseEntity<NotificacionDto> actualizar(@PathVariable Long id, @RequestBody NotificacionDto dto) {
        try {
            return ResponseEntity.ok(service.actualizar(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar notificación", description = "Permite borrar permanentemente una notificación del sistema mediante su ID")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}