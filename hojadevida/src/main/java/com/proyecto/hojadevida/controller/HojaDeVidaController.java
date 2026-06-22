package com.proyecto.hojadevida.controller;

import com.proyecto.hojadevida.service.HojaDeVidaService;
import com.proyecto.hojadevida.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;

import com.proyecto.hojadevida.dto.HojaDeVidaDTO;
import com.proyecto.hojadevida.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hojadevida")
public class HojaDeVidaController {

    private final HojaDeVidaService hojaDeVidaService;
    private final AuthService authService;

    // * Método Listar Hojas de Vida Existentes * //

    @GetMapping("/list")

    @Operation(summary = "Listar todas las hojas de vida", description = "Permite listar las hojas de vida existentes")
    public ResponseEntity<ApiResponse<List<HojaDeVidaDTO>>> getAllHojaDeVidas(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.replace("Bearer ", "");
            ApiResponse<String> validationResponse = authService.validateToken(token);

            if (validationResponse == null || validationResponse.getCode() != 200) {
                ApiResponse<List<HojaDeVidaDTO>> errorResponse =
                        new ApiResponse<>(401, "Token inválido", null);
                return ResponseEntity.status(401).body(errorResponse);
            }

            List<HojaDeVidaDTO> hojas = hojaDeVidaService.getAllHojaDeVidasDTO();
            ApiResponse<List<HojaDeVidaDTO>> response =
                    new ApiResponse<>(200, "Listado de hojas de vida", hojas);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<List<HojaDeVidaDTO>> response = new ApiResponse<>(500, "Error al listar hojas de vida: " + e.getMessage(), null);
            return ResponseEntity.status(500).body(response);
        }
    }

    // * Método Obtener Hoja de Vida por ID * //

    @GetMapping("/{id}")
    @Operation(summary = "Buscar hoja de vida por ID", description = "Permite buscar una hoja de vida por ID o Rut")

    public ResponseEntity<ApiResponse<HojaDeVidaDTO>> getHojaDeVidaById(@RequestHeader("Authorization") String authHeader, @PathVariable Long id) {
        try {
            String token = authHeader.replace("Bearer ", "");
            ApiResponse<String> validationResponse = authService.validateToken(token);

            if (validationResponse == null || validationResponse.getCode() != 200) {
                ApiResponse<HojaDeVidaDTO> errorResponse =
                    new ApiResponse<>(401, "Token inválido", null);
                return ResponseEntity.status(401).body(errorResponse);
            }

            HojaDeVidaDTO hoja = hojaDeVidaService.getHojaDeVidaById(id);
            ApiResponse<HojaDeVidaDTO> response =
                    new ApiResponse<>(200, "Hoja de vida encontrada", hoja);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<HojaDeVidaDTO> response = new ApiResponse<>(500, "Error al obtener hoja de vida: " + e.getMessage(), null);
            return ResponseEntity.status(500).body(response);
        }
    }

    // * Método Crear Hoja de Vida Nueva * //

    @PostMapping("/create")
    @Operation(summary = "Crear una hoja de vida", description = "Permite crear una nueva hoja de vida")

    public ResponseEntity<ApiResponse<HojaDeVidaDTO>> createHojaDeVida(@RequestHeader("Authorization") String authHeader, @Valid @RequestBody HojaDeVidaDTO dto){
        try {
            String token = authHeader.replace("Bearer ", "");
            ApiResponse<String> validationResponse = authService.validateToken(token);

            if (validationResponse == null || validationResponse.getCode() != 200) {
                ApiResponse<HojaDeVidaDTO> errorResponse =
                    new ApiResponse<>(401, "Token inválido", null);
                return ResponseEntity.status(401).body(errorResponse);
            }

            HojaDeVidaDTO created = hojaDeVidaService.createHojaDeVida(dto);
            ApiResponse<HojaDeVidaDTO> response =
                new ApiResponse<>(201, "Hoja de vida creada", created);
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            ApiResponse<HojaDeVidaDTO> response = new ApiResponse<>(500, "Error al crear hoja de vida: " + e.getMessage(), null);
            return ResponseEntity.status(500).body(response);
        }

    }

    // * Método Actualizar Hoja de Vida * //

    @PutMapping("/update/{id}")
    @Operation(summary = "Actualizar hoja de vida", description = "Permite actualizar una hoja de vida existente")

    public ResponseEntity<ApiResponse<HojaDeVidaDTO>> updateHojaDeVida(@RequestHeader("Authorization") String authHeader, @Valid @RequestBody HojaDeVidaDTO dto, @PathVariable Long id){
        try {
            String token = authHeader.replace("Bearer ", "");
            ApiResponse<String> validationResponse = authService.validateToken(token);

            if (validationResponse == null || validationResponse.getCode() != 200) {
                ApiResponse<HojaDeVidaDTO> errorResponse =
                    new ApiResponse<>(401, "Token inválido", null);
                return ResponseEntity.status(401).body(errorResponse);
            }

            HojaDeVidaDTO updated = hojaDeVidaService.updateHojaDeVida(id, dto);
            ApiResponse<HojaDeVidaDTO> response =
                    new ApiResponse<>(200, "Hoja de vida actualizada", updated);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<HojaDeVidaDTO> response = new ApiResponse<>(500, "Error al actualizar hoja de vida: " + e.getMessage(), null);
            return ResponseEntity.status(500).body(response);
        }
    }

    // * Método Eliminar Hoja de Vida * //

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Eliminar hoja de vida", description = "Permite eliminar una hoja de vida específica")

    public ResponseEntity<ApiResponse<Void>> deleteHojaDeVida(@PathVariable Long id, @RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.replace("Bearer ", "");
            ApiResponse<String> validationResponse = authService.validateToken(token);

            if (validationResponse == null || validationResponse.getCode() != 200) {
                ApiResponse<Void> errorResponse =
                    new ApiResponse<>(401, "Token inválido", null);
                return ResponseEntity.status(401).body(errorResponse);
            }

            hojaDeVidaService.deleteHojaDeVida(id);
            ApiResponse<Void> response =
                    new ApiResponse<>(200, "Hoja de vida eliminada", null);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<Void> response = new ApiResponse<>(500, "Error al eliminar hoja de vida: " + e.getMessage(), null);
            return ResponseEntity.status(500).body(response);
        }
    }
}