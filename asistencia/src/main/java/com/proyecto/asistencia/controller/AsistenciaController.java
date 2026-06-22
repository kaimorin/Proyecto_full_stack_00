package com.proyecto.asistencia.controller;

import com.proyecto.asistencia.service.AsistenciaService;
import com.proyecto.asistencia.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;

import com.proyecto.asistencia.dto.ApiResponse;
import com.proyecto.asistencia.dto.AsistenciaDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/asistencia")
public class AsistenciaController {

    private final AsistenciaService asistenciaService;
    private final AuthService authService;
    private static final Logger logger = LoggerFactory.getLogger(AsistenciaController.class);

    // * Método Listar Asistencias Existentes * //

    @GetMapping("/list")

    @Operation(summary = "Obtener todas las asistencias")
    public ResponseEntity<ApiResponse<List<AsistenciaDTO>>> getAllAsistencias(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.replace("Bearer ", "");
            ApiResponse<String> validationResponse = authService.validateToken(token);

            if (validationResponse == null || validationResponse.getCode() != 200) {
                ApiResponse<List<AsistenciaDTO>> errorResponse =
                        new ApiResponse<>(401, "Token inválido", null);
                return ResponseEntity.status(401).body(errorResponse);
            }

            List<AsistenciaDTO> asistencias = asistenciaService.getAllAsistenciasDTO();
            ApiResponse<List<AsistenciaDTO>> response =
                    new ApiResponse<>(200, "Listado de asistencias", asistencias);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error al listar asistencias: {}", e.getMessage());
            ApiResponse<List<AsistenciaDTO>> response = new ApiResponse<>(500, "Error al listar asistencias: " + e.getMessage(), null);
            return ResponseEntity.status(500).body(response);
        }
    }

    // * Método Obtener Asistencia por ID * //

    @GetMapping("/{id}")
    @Operation(summary = "Obtener asistencia por ID")

    public ResponseEntity<ApiResponse<AsistenciaDTO>> getAsistenciaById(@RequestHeader("Authorization") String authHeader, @PathVariable Long id) {
        try {
            String token = authHeader.replace("Bearer ", "");
            ApiResponse<String> validationResponse = authService.validateToken(token);

            if (validationResponse == null || validationResponse.getCode() != 200) {
                ApiResponse<AsistenciaDTO> errorResponse =
                    new ApiResponse<>(401, "Token inválido", null);
                return ResponseEntity.status(401).body(errorResponse);
            }

            AsistenciaDTO asistencia = asistenciaService.getAsistenciaById(id);
            ApiResponse<AsistenciaDTO> response =
                    new ApiResponse<>(200, "Asistencia encontrada", asistencia);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<AsistenciaDTO> response = new ApiResponse<>(500, "Error al obtener asistencia: " + e.getMessage(), null);
            return ResponseEntity.status(500).body(response);
        }
    }

    // * Método Crear Asistencia Nueva * //

    @PostMapping("/create")
    @Operation(summary = "Crear nueva asistencia")

    public ResponseEntity<ApiResponse<AsistenciaDTO>> createAsistencia(@RequestHeader("Authorization") String authHeader, @Valid @RequestBody AsistenciaDTO dto){
        try {
            String token = authHeader.replace("Bearer ", "");
            ApiResponse<String> validationResponse = authService.validateToken(token);

            if (validationResponse == null || validationResponse.getCode() != 200) {
                ApiResponse<AsistenciaDTO> errorResponse =
                    new ApiResponse<>(401, "Token inválido", null);
                return ResponseEntity.status(401).body(errorResponse);
            }

            AsistenciaDTO created = asistenciaService.createAsistencia(dto);
            ApiResponse<AsistenciaDTO> response =
                new ApiResponse<>(201, "Creado estado de asistencia", created);
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            ApiResponse<AsistenciaDTO> response = new ApiResponse<>(500, "Error al crear asistencia: " + e.getMessage(), null);
            return ResponseEntity.status(500).body(response);
        }

    }

    // * Método Actualizar Asistencia * //

    @PutMapping("/update/{id}")
    @Operation(summary = "Actualizar asistencia")

    public ResponseEntity<ApiResponse<AsistenciaDTO>> updateAsistencia(@RequestHeader("Authorization") String authHeader, @Valid @RequestBody AsistenciaDTO dto, @PathVariable Long id){
        try {
            String token = authHeader.replace("Bearer ", "");
            ApiResponse<String> validationResponse = authService.validateToken(token);

            if (validationResponse == null || validationResponse.getCode() != 200) {
                ApiResponse<AsistenciaDTO> errorResponse =
                    new ApiResponse<>(401, "Token inválido", null);
                return ResponseEntity.status(401).body(errorResponse);
            }

            AsistenciaDTO updated = asistenciaService.updateAsistencia(id, dto);
            ApiResponse<AsistenciaDTO> response =
                    new ApiResponse<>(200, "Estado de asistencia actualizado", updated);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<AsistenciaDTO> response = new ApiResponse<>(500, "Error al actualizar asistencia: " + e.getMessage(), null);
            return ResponseEntity.status(500).body(response);
        }
    }
}