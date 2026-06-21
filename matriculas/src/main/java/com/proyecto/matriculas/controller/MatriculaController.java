package com.proyecto.matriculas.controller;

import com.proyecto.matriculas.service.MatriculaService;
import com.proyecto.matriculas.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;

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
    private final AuthService authService;

    // * Método Listar Matrículas Existentes * //

    @GetMapping("/list")
    @Operation(summary = "Obtener todas las matrículas")
    public ResponseEntity<ApiResponse<List<MatriculaDTO>>> getAllMatriculas(@RequestHeader("Authorization") String authHeader) {

        String token = authHeader.replace("Bearer ", "");
        ApiResponse<String> validationResponse = authService.validateToken(token);

        if (validationResponse == null || validationResponse.getCode() != 200) {
            ApiResponse<List<MatriculaDTO>> errorResponse =
                    new ApiResponse<>(401, "Token inválido", null);
            return ResponseEntity.status(401).body(errorResponse);
        }

        List<MatriculaDTO> matriculas = matriculaService.getAllMatriculasDTO();
        ApiResponse<List<MatriculaDTO>> response =
                new ApiResponse<>(200, "Listado de matrículas", matriculas);
        return ResponseEntity.ok(response);
    }

    // * Método Obtener Matrícula por ID * //

    @GetMapping("/{id}")
    @Operation(summary = "Obtener matrícula por ID")

    public ResponseEntity<ApiResponse<MatriculaDTO>> getMatriculaById(@RequestHeader("Authorization") String authHeader, @PathVariable Long id) {
        String token = authHeader.replace("Bearer ", "");
        ApiResponse<String> validationResponse = authService.validateToken(token);

        if (validationResponse == null || validationResponse.getCode() != 200) {
            ApiResponse<MatriculaDTO> errorResponse =
                new ApiResponse<>(401, "Token inválido", null);
            return ResponseEntity.status(401).body(errorResponse);
        }

        MatriculaDTO matricula = matriculaService.getMatriculaById(id);
        ApiResponse<MatriculaDTO> response =
                new ApiResponse<>(200, "Matrícula encontrada", matricula);
        return ResponseEntity.ok(response);
    }

    // * Método Crear Matrícula Nueva * //

    @PostMapping("/create")
    @Operation(summary = "Crear una nueva matrícula")

    public ResponseEntity<ApiResponse<MatriculaDTO>> createMatricula(@RequestHeader("Authorization") String authHeader, @Valid @RequestBody MatriculaDTO dto){
        String token = authHeader.replace("Bearer ", "");
        ApiResponse<String> validationResponse = authService.validateToken(token);

        if (validationResponse == null || validationResponse.getCode() != 200) {
            ApiResponse<MatriculaDTO> errorResponse =
                new ApiResponse<>(401, "Token inválido", null);
            return ResponseEntity.status(401).body(errorResponse);
        }

        MatriculaDTO created = matriculaService.createMatricula(dto);
        ApiResponse<MatriculaDTO> response =
            new ApiResponse<>(201, "Matrícula creada", created);
        return ResponseEntity.status(201).body(response);

    }

    // * Método Actualizar Matrícula * //

    @PutMapping("/update/{id}")
    @Operation(summary = "Actualizar una matrícula existente")

    public ResponseEntity<ApiResponse<MatriculaDTO>> updateMatricula(@RequestHeader("Authorization") String authHeader, @Valid @RequestBody MatriculaDTO dto, @PathVariable Long id){

        String token = authHeader.replace("Bearer ", "");
        ApiResponse<String> validationResponse = authService.validateToken(token);

        if (validationResponse == null || validationResponse.getCode() != 200) {
            ApiResponse<MatriculaDTO> errorResponse =
                new ApiResponse<>(401, "Token inválido", null);
            return ResponseEntity.status(401).body(errorResponse);
        }

        MatriculaDTO updated = matriculaService.updateMatricula(id, dto);
        ApiResponse<MatriculaDTO> response =
                new ApiResponse<>(200, "Matrícula actualizada", updated);
        return ResponseEntity.ok(response);
    }

    // * Método Eliminar Matrícula * //

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Eliminar una matrícula")

    public ResponseEntity<ApiResponse<Void>> deleteMatricula(@PathVariable Long id, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        ApiResponse<String> validationResponse = authService.validateToken(token);

        if (validationResponse == null || validationResponse.getCode() != 200) {
            ApiResponse<Void> errorResponse =
                new ApiResponse<>(401, "Token inválido", null);
            return ResponseEntity.status(401).body(errorResponse);
        }

        matriculaService.deleteMatricula(id);
        ApiResponse<Void> response =
                new ApiResponse<>(200, "Matrícula eliminada", null);
        return ResponseEntity.ok(response);
    }
}