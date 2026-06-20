package com.proyecto.leccionario.controller;

import com.proyecto.leccionario.dto.ApiResponse;
import com.proyecto.leccionario.dto.LeccionarioDto;
import com.proyecto.leccionario.service.LeccionarioService;
import com.proyecto.leccionario.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/leccionario")
@RequiredArgsConstructor
public class LeccionarioController {

    private final LeccionarioService service;
    private final AuthService authService;

    // * Método Listar Leccionarios Existentes * //

    @GetMapping("/list")

    @Operation(summary = "Listado de leccionarios", description = "Permite listar los leccionarios existentes")
    public ResponseEntity<ApiResponse<List<LeccionarioDto>>> listar(@RequestHeader("Authorization") String authHeader) {

        String token = authHeader.replace("Bearer ", "");
        ApiResponse<String> validationResponse = authService.validateToken(token);

        if (validationResponse == null || validationResponse.getCode() != 200) {
            ApiResponse<List<LeccionarioDto>> errorResponse =
                    new ApiResponse<>(401, "Token inválido", null);
            return ResponseEntity.status(401).body(errorResponse);
        }

        List<LeccionarioDto> lista = service.listarTodos();
        ApiResponse<List<LeccionarioDto>> response = new ApiResponse<>(200, "Listado de leccionarios", lista);
        return ResponseEntity.ok(response);
    }

    // * Método Obtener Leccionario por ID * //

    @GetMapping("/{id}")
    @Operation(summary = "Buscar leccionario por ID", description = "Permite buscar por ID un leccionario específico")

    public ResponseEntity<ApiResponse<LeccionarioDto>> obtener(@RequestHeader("Authorization") String authHeader, @PathVariable Long id) {
        String token = authHeader.replace("Bearer ", "");
        ApiResponse<String> validationResponse = authService.validateToken(token);

        if (validationResponse == null || validationResponse.getCode() != 200) {
            ApiResponse<LeccionarioDto> errorResponse =
                new ApiResponse<>(401, "Token inválido", null);
            return ResponseEntity.status(401).body(errorResponse);
        }

        return service.obtenerPorId(id)
                .map(leccionario -> ResponseEntity.ok(new ApiResponse<>(200, "Leccionario encontrado", leccionario)))
                .orElse(ResponseEntity.status(404).body(new ApiResponse<>(404, "Leccionario no encontrado", null)));
    }

    // * Método Buscar Leccionario por Asignatura * //

    @GetMapping("/buscar")
    @Operation(summary = "Buscar leccionario por asignatura", description = "Permite buscar leccionario por asignatura")

    public ResponseEntity<ApiResponse<LeccionarioDto>> buscar(@RequestHeader("Authorization") String authHeader, @RequestParam String asignatura) {
        String token = authHeader.replace("Bearer ", "");
        ApiResponse<String> validationResponse = authService.validateToken(token);

        if (validationResponse == null || validationResponse.getCode() != 200) {
            ApiResponse<LeccionarioDto> errorResponse =
                new ApiResponse<>(401, "Token inválido", null);
            return ResponseEntity.status(401).body(errorResponse);
        }

        return service.obtenerPorAsignatura(asignatura)
                .map(leccionario -> ResponseEntity.ok(new ApiResponse<>(200, "Leccionario encontrado", leccionario)))
                .orElse(ResponseEntity.status(404).body(new ApiResponse<>(404, "Leccionario no encontrado", null)));
    }

    // * Método Crear Leccionario Nuevo * //

    @PostMapping("/crear")
    @Operation(summary = "Crear nuevo leccionario", description = "Permite crear un nuevo leccionario")

    public ResponseEntity<ApiResponse<LeccionarioDto>> crear(@RequestHeader("Authorization") String authHeader, @Valid @RequestBody LeccionarioDto leccionario){
        String token = authHeader.replace("Bearer ", "");
        ApiResponse<String> validationResponse = authService.validateToken(token);

        if (validationResponse == null || validationResponse.getCode() != 200) {
            ApiResponse<LeccionarioDto> errorResponse =
                new ApiResponse<>(401, "Token inválido", null);
            return ResponseEntity.status(401).body(errorResponse);
        }

        LeccionarioDto creado = service.crear(leccionario);
        ApiResponse<LeccionarioDto> response = new ApiResponse<>(201, "Leccionario creado", creado);
        return ResponseEntity.status(201).body(response);

    }

    // * Método Actualizar Leccionario * //

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar leccionario", description = "Permite actualizar los datos del leccionario")

    public ResponseEntity<ApiResponse<LeccionarioDto>> actualizar(@RequestHeader("Authorization") String authHeader, @Valid @RequestBody LeccionarioDto lec, @PathVariable Long id){

        String token = authHeader.replace("Bearer ", "");
        ApiResponse<String> validationResponse = authService.validateToken(token);

        if (validationResponse == null || validationResponse.getCode() != 200) {
            ApiResponse<LeccionarioDto> errorResponse =
                new ApiResponse<>(401, "Token inválido", null);
            return ResponseEntity.status(401).body(errorResponse);
        }

        LeccionarioDto actualizado = service.actualizar(id, lec);
        ApiResponse<LeccionarioDto> response = new ApiResponse<>(200, "Leccionario actualizado", actualizado);
        return ResponseEntity.ok(response);
    }

    // * Método Eliminar Leccionario * //

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar leccionario por ID", description = "Permite eliminar por ID un leccionario específico")

    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        ApiResponse<String> validationResponse = authService.validateToken(token);

        if (validationResponse == null || validationResponse.getCode() != 200) {
            ApiResponse<Void> errorResponse =
                new ApiResponse<>(401, "Token inválido", null);
            return ResponseEntity.status(401).body(errorResponse);
        }

        service.eliminar(id);
        ApiResponse<Void> response = new ApiResponse<>(200, "Leccionario eliminado", null);
        return ResponseEntity.ok(response);
    }
}