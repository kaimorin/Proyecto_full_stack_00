package com.proyecto.matriculas.controller;

import com.proyecto.matriculas.service.MatriculaService;
import com.proyecto.matriculas.dto.MatriculaDTO;
import com.proyecto.matriculas.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/matriculas")
public class MatriculaController {

    private final MatriculaService matriculaService;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<MatriculaDTO>>> getAllMatriculas() {
        List<MatriculaDTO> matriculas = matriculaService.getAllMatriculasDTO();
        ApiResponse<List<MatriculaDTO>> response =
                new ApiResponse<>(200, "Listado de matrículas", matriculas);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MatriculaDTO>> getMatriculaById(@PathVariable Long id) {
        MatriculaDTO matricula = matriculaService.getMatriculaById(id);
        ApiResponse<MatriculaDTO> response =
                new ApiResponse<>(200, "Matrícula encontrada", matricula);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<MatriculaDTO>> createMatricula(
            @Valid @RequestBody MatriculaDTO dto) {
        MatriculaDTO created = matriculaService.createMatricula(dto);
        ApiResponse<MatriculaDTO> response =
                new ApiResponse<>(201, "Matrícula creada", created);
        return ResponseEntity.status(201).body(response);
    }
@PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<MatriculaDTO>> updateMatricula(
            @PathVariable Long id,
            @Valid @RequestBody MatriculaDTO dto) {
        MatriculaDTO updated = matriculaService.updateMatricula(id, dto);
        ApiResponse<MatriculaDTO> response =
                new ApiResponse<>(200, "Matrícula actualizada", updated);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteMatricula(@PathVariable Long id) {
        matriculaService.deleteMatricula(id);
        ApiResponse<Void> response =
                new ApiResponse<>(200, "Matrícula eliminada", null);
        return ResponseEntity.ok(response);
    }
}