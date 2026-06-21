package com.proyecto.reuniones.controller;

import com.proyecto.reuniones.service.AuthService;
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
    private final AuthService authService;

    // * Método Listar Reuniones Existentes * //

    @GetMapping("/list")

    @Operation(summary = "Obtener todas las reuniones", description = "Permite listar todas las reuniones existentes")
    public ResponseEntity<ApiResponse<List<ReunionDTO>>> listar(@RequestHeader("Authorization") String authHeader) {

        String token = authHeader.replace("Bearer ", "");
        ApiResponse<String> validationResponse = authService.validateToken(token);

        if (validationResponse == null || validationResponse.getCode() != 200) {
            ApiResponse<List<ReunionDTO>> errorResponse =
                    new ApiResponse<>(401, "Token inválido", null);
            return ResponseEntity.status(401).body(errorResponse);
        }

        List<ReunionDTO> reuniones = reunionService.getAllReunionesDTO();
        ApiResponse<List<ReunionDTO>> response =
                new ApiResponse<>(200, "Listado de reuniones", reuniones);
        return ResponseEntity.ok(response);
    }

    // * Método Obtener Reunión por ID * //

    @GetMapping("/{id}")
    @Operation(summary = "Obtener reunión por ID", description = "permite obtener una reunión específica por ID")

    public ResponseEntity<ApiResponse<ReunionDTO>> buscarPorId (@RequestHeader("Authorization") String authHeader, @PathVariable Long id) {
        String token = authHeader.replace("Bearer ", "");
        ApiResponse<String> validationResponse = authService.validateToken(token);

        if (validationResponse == null || validationResponse.getCode() != 200) {
            ApiResponse<ReunionDTO> errorResponse =
                new ApiResponse<>(401, "Token inválido", null);
            return ResponseEntity.status(401).body(errorResponse);
        }

        ReunionDTO reunion = reunionService.getReunionById(id);
        ApiResponse<ReunionDTO> response =
                new ApiResponse<>(200, "Reunión encontrada", reunion);
        return ResponseEntity.ok(response);
    }

    // * Método Crear Reunión Nueva * //

    @PostMapping("/create")
    @Operation(summary = "Crear una nueva reunión", description = "Permite crear una nueva reunión")

    public ResponseEntity<ApiResponse<ReunionDTO>> createReunion (@RequestHeader("Authorization") String authHeader, @Valid @RequestBody ReunionDTO dto){
        String token = authHeader.replace("Bearer ", "");
        ApiResponse<String> validationResponse = authService.validateToken(token);

        if (validationResponse == null || validationResponse.getCode() != 200) {
            ApiResponse<ReunionDTO> errorResponse =
                new ApiResponse<>(401, "Token inválido", null);
            return ResponseEntity.status(401).body(errorResponse);
        }
        
        ReunionDTO created = reunionService.createReunion(dto);
        ApiResponse<ReunionDTO> response =
            new ApiResponse<>(201, "Reunión creada", created);
        return ResponseEntity.status(201).body(response);

    }

    // * Método Actualizar Reunión * //

    @PutMapping("/update/{id}")
    @Operation(summary = "Actualizar una reunión existente", description = "Permite actualizar una reunión ya creada")

    public ResponseEntity<ApiResponse<ReunionDTO>> updateReunion (@RequestHeader("Authorization") String authHeader, @Valid @RequestBody ReunionDTO dto, @PathVariable Long id){
            
        String token = authHeader.replace("Bearer ", "");
        ApiResponse<String> validationResponse = authService.validateToken(token);

        if (validationResponse == null || validationResponse.getCode() != 200) {
            ApiResponse<ReunionDTO> errorResponse =
                new ApiResponse<>(401, "Token inválido", null);
            return ResponseEntity.status(401).body(errorResponse);
        }
        
        ReunionDTO updated = reunionService.updateReunion(id, dto);
        ApiResponse<ReunionDTO> response =
                new ApiResponse<>(200, "Reunión actualizada", updated);
        return ResponseEntity.ok(response);
    }

    // * Método Eliminar Reunión * //

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Eliminar una reunión", description = "Eliminar una reunión existente")

    public ResponseEntity<ApiResponse<Void>> updateReunion (@PathVariable Long id, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        ApiResponse<String> validationResponse = authService.validateToken(token);

        if (validationResponse == null || validationResponse.getCode() != 200) {
            ApiResponse<Void> errorResponse =
                new ApiResponse<>(401, "Token inválido", null);
            return ResponseEntity.status(401).body(errorResponse);
        }

        reunionService.deleteReunion(id);
        ApiResponse<Void> response =
                new ApiResponse<>(200, "Reunión eliminada", null);
        return ResponseEntity.ok(response);
    }
}