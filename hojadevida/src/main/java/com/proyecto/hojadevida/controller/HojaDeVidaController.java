package com.proyecto.hojadevida.controller;

import com.proyecto.hojadevida.service.HojaDeVidaService;

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

    @GetMapping("/list")
    @Operation(summary = "Listar todas las hojas de vida", description = "Permite listar las hojas de vida existentes")
    public ResponseEntity<ApiResponse<List<HojaDeVidaDTO>>> getAllHojaDeVidas() {
        List<HojaDeVidaDTO> hojas = hojaDeVidaService.getAllHojaDeVidasDTO();
        ApiResponse<List<HojaDeVidaDTO>> response =
                new ApiResponse<>(200, "Listado de hojas de vida", hojas);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar hoja de vida por ID", description = "Permite buscar una hoja de vida por ID o Rut")
    public ResponseEntity<ApiResponse<HojaDeVidaDTO>> getHojaDeVidaById(@PathVariable Long id) {
        HojaDeVidaDTO hoja = hojaDeVidaService.getHojaDeVidaById(id);
        ApiResponse<HojaDeVidaDTO> response =
                new ApiResponse<>(200, "Hoja de vida encontrada", hoja);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    @Operation(summary = "Crear una hoja de vida", description = "Permite crear una nueva hoja de vida")
    public ResponseEntity<ApiResponse<HojaDeVidaDTO>> createHojaDeVida(
            @Valid @RequestBody HojaDeVidaDTO dto) {
        HojaDeVidaDTO created = hojaDeVidaService.createHojaDeVida(dto);
        ApiResponse<HojaDeVidaDTO> response =
                new ApiResponse<>(201, "Hoja de vida creada", created);
        return ResponseEntity.status(201).body(response);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Actualizar hoja de vida", description = "Permite actualizar una hoja de vida existente")
    public ResponseEntity<ApiResponse<HojaDeVidaDTO>> updateHojaDeVida(
            @PathVariable Long id,
            @Valid @RequestBody HojaDeVidaDTO dto) {
        HojaDeVidaDTO updated = hojaDeVidaService.updateHojaDeVida(id, dto);
        ApiResponse<HojaDeVidaDTO> response =
                new ApiResponse<>(200, "Hoja de vida actualizada", updated);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Eliminar hoja de vida", description = "Permite eliminar una hoja de vida específica")
    public ResponseEntity<ApiResponse<Void>> deleteHojaDeVida(@PathVariable Long id) {
        hojaDeVidaService.deleteHojaDeVida(id);
        ApiResponse<Void> response =
                new ApiResponse<>(200, "Hoja de vida eliminada", null);
        return ResponseEntity.ok(response);
    }
}