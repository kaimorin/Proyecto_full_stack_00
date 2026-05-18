package com.proyecto.reuniones.controller;

import com.proyecto.reuniones.service.ReunionService;
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
    public ResponseEntity<ApiResponse<List<ReunionDTO>>> getAllReuniones() {
        List<ReunionDTO> reuniones = reunionService.getAllReunionesDTO();
        ApiResponse<List<ReunionDTO>> response =
                new ApiResponse<>(200, "Listado de reuniones", reuniones);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ReunionDTO>> getReunionById(@PathVariable Long id) {
        ReunionDTO reunion = reunionService.getReunionById(id);
        ApiResponse<ReunionDTO> response =
                new ApiResponse<>(200, "Reunión encontrada", reunion);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<ReunionDTO>> createReunion(
            @Valid @RequestBody ReunionDTO dto) {
        ReunionDTO created = reunionService.createReunion(dto);
        ApiResponse<ReunionDTO> response =
                new ApiResponse<>(201, "Reunión creada", created);
        return ResponseEntity.status(201).body(response);
    }
@PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<ReunionDTO>> updateReunion(
            @PathVariable Long id,
            @Valid @RequestBody ReunionDTO dto) {
        ReunionDTO updated = reunionService.updateReunion(id, dto);
        ApiResponse<ReunionDTO> response =
                new ApiResponse<>(200, "Reunión actualizada", updated);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteReunion(@PathVariable Long id) {
        reunionService.deleteReunion(id);
        ApiResponse<Void> response =
                new ApiResponse<>(200, "Reunión eliminada", null);
        return ResponseEntity.ok(response);
    }
}