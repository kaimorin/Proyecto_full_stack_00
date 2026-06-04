package com.proyecto.asistencia.controller;

import com.proyecto.asistencia.service.AsistenciaService;

import io.swagger.v3.oas.annotations.Operation;

import com.proyecto.asistencia.dto.ApiResponse;
import com.proyecto.asistencia.dto.AsistenciaDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/asistencia")
public class AsistenciaController {

    private final AsistenciaService asistenciaService;

    @Operation(summary = "Obtener todas las asistencias")
    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<AsistenciaDTO>>> getAllAsistencias() {
        List<AsistenciaDTO> asistencias = asistenciaService.getAllAsistenciasDTO();
        ApiResponse<List<AsistenciaDTO>> response =
                new ApiResponse<>(200, "Listado de asistencias", asistencias);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtener asistencia por ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AsistenciaDTO>> getAsistenciaById(@PathVariable Long id) {
        AsistenciaDTO asistencia = asistenciaService.getAsistenciaById(id);
        ApiResponse<AsistenciaDTO> response =
                new ApiResponse<>(200, "Asistencia encontrada", asistencia);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Crear nueva asistencia")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<AsistenciaDTO>> createAsistencia(
            @Valid @RequestBody AsistenciaDTO dto) {
        AsistenciaDTO created = asistenciaService.createAsistencia(dto);
        ApiResponse<AsistenciaDTO> response =
                new ApiResponse<>(201, "Creado estado de asistencia", created);
        return ResponseEntity.status(201).body(response);
    }

    @Operation(summary = "Actualizar asistencia")
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<AsistenciaDTO>> updateAsistencia(
            @PathVariable Long id,
            @Valid @RequestBody AsistenciaDTO dto) {
        AsistenciaDTO updated = asistenciaService.updateAsistencia(id, dto);
        ApiResponse<AsistenciaDTO> response =
                new ApiResponse<>(200, "Estado de asistencia actualizado", updated);
        return ResponseEntity.ok(response);
    }
}