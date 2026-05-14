package com.proyecto.hojadevida.controller;

import com.proyecto.hojadevida.service.HojaDeVidaService;
import com.proyecto.hojadevida.dto.HojaDeVidaDTO;
import com.proyecto.login.dto.ApiResponse;
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
    public ResponseEntity<ApiResponse<List<HojaDeVidaDTO>>> getAllHojaDeVidas() {
        List<HojaDeVidaDTO> hojas = hojaDeVidaService.getAllHojaDeVidasDTO();
        ApiResponse<List<HojaDeVidaDTO>> response =
                new ApiResponse<>(200, "Listado de hojas de vida", hojas);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<HojaDeVidaDTO>> getHojaDeVidaById(@PathVariable Long id) {
        HojaDeVidaDTO hoja = hojaDeVidaService.getHojaDeVidaById(id);
        ApiResponse<HojaDeVidaDTO> response =
                new ApiResponse<>(200, "Hoja de vida encontrada", hoja);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<HojaDeVidaDTO>> createHojaDeVida(
            @Valid @RequestBody HojaDeVidaDTO dto) {
        HojaDeVidaDTO created = hojaDeVidaService.createHojaDeVida(dto);
        ApiResponse<HojaDeVidaDTO> response =
                new ApiResponse<>(201, "Hoja de vida creada", created);
        return ResponseEntity.status(201).body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<HojaDeVidaDTO>> updateHojaDeVida(
            @PathVariable Long id,
            @Valid @RequestBody HojaDeVidaDTO dto) {
        HojaDeVidaDTO updated = hojaDeVidaService.updateHojaDeVida(id, dto);
        ApiResponse<HojaDeVidaDTO> response =
                new ApiResponse<>(200, "Hoja de vida actualizada", updated);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteHojaDeVida(@PathVariable Long id) {
        hojaDeVidaService.deleteHojaDeVida(id);
        ApiResponse<Void> response =
                new ApiResponse<>(200, "Hoja de vida eliminada", null);
        return ResponseEntity.ok(response);
    }
}