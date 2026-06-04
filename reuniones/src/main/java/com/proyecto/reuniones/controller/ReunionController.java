package com.proyecto.reuniones.controller;

import com.proyecto.reuniones.service.ReunionService;

import io.swagger.v3.oas.annotations.Operation;

import com.proyecto.reuniones.dto.ReunionDTO;
import com.proyecto.reuniones.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reuniones")
public class ReunionController {

    private final ReunionService reunionService;

    @GetMapping("/list")
    @Operation(summary = "Obtener todas las reuniones")
    public ResponseEntity<ApiResponse<List<ReunionDTO>>> getAllReuniones() {
        List<ReunionDTO> reuniones = reunionService.getAllReunionesDTO();
        ApiResponse<List<ReunionDTO>> response =
                new ApiResponse<>(200, "Listado de reuniones", reuniones);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener reunión por ID")
    public ResponseEntity<ApiResponse<ReunionDTO>> getReunionById(@PathVariable Long id) {
        ReunionDTO reunion = reunionService.getReunionById(id);
        ApiResponse<ReunionDTO> response =
                new ApiResponse<>(200, "Reunión encontrada", reunion);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    @Operation(summary = "Crear una nueva reunión")
    public ResponseEntity<ApiResponse<ReunionDTO>> createReunion(
            @Valid @RequestBody ReunionDTO dto) {
        ReunionDTO created = reunionService.createReunion(dto);
        ApiResponse<ReunionDTO> response =
                new ApiResponse<>(201, "Reunión creada", created);
        return ResponseEntity.status(201).body(response);
    }
    @PutMapping("/update/{id}")
    @Operation(summary = "Actualizar una reunión existente")
    public ResponseEntity<ApiResponse<ReunionDTO>> updateReunion(
            @PathVariable Long id,
            @Valid @RequestBody ReunionDTO dto) {
        ReunionDTO updated = reunionService.updateReunion(id, dto);
        ApiResponse<ReunionDTO> response =
                new ApiResponse<>(200, "Reunión actualizada", updated);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Eliminar una reunión")
    public ResponseEntity<ApiResponse<Void>> deleteReunion(@PathVariable Long id) {
        reunionService.deleteReunion(id);
        ApiResponse<Void> response =
                new ApiResponse<>(200, "Reunión eliminada", null);
        return ResponseEntity.ok(response);
    }
}