package com.proyecto.leccionario.controller;

import com.proyecto.leccionario.dto.ApiResponse;
import com.proyecto.leccionario.dto.LeccionarioDto;
import com.proyecto.leccionario.service.LeccionarioService;
import com.proyecto.leccionario.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/api/leccionario")
@RequiredArgsConstructor
public class LeccionarioController {

    private final LeccionarioService service;
    private final AuthService authService;
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
        try {
            List<LeccionarioDto> lista = service.listarTodos();
            ApiResponse<List<LeccionarioDto>> response = new ApiResponse<>(200, "Listado de leccionarios", lista);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<List<LeccionarioDto>> response = new ApiResponse<>(500, "Error al listar leccionarios: " + e.getMessage(), null);
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar leccionario por ID", description = "Permite buscar por ID un leccionario específico")
    public ResponseEntity<ApiResponse<LeccionarioDto>> obtener(@RequestHeader("Authorization") String authHeader,@PathVariable Long id) {
        String token = authHeader.replace("Bearer ", "");
        ApiResponse<String> validationResponse = authService.validateToken(token);

        if (validationResponse == null || validationResponse.getCode() != 200) {
            ApiResponse<LeccionarioDto> errorResponse =
                    new ApiResponse<>(401, "Token inválido", null);
            return ResponseEntity.status(401).body(errorResponse);
        }

        try {
            return service.obtenerPorId(id)
                    .map(leccionario -> ResponseEntity.ok(new ApiResponse<>(200, "Leccionario encontrado", leccionario)))
                    .orElse(ResponseEntity.status(404).body(new ApiResponse<>(404, "Leccionario no encontrado", null)));
        } catch (Exception e) {
            ApiResponse<LeccionarioDto> response = new ApiResponse<>(500, "Error al obtener leccionario: " + e.getMessage(), null);
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar leccionario por asignatura", description = "Permite buscar leccionario por asignatura")
    public ResponseEntity<ApiResponse<LeccionarioDto>> buscar(@RequestHeader("Authorization") String authHeader,@RequestParam String asignatura) {
        String token = authHeader.replace("Bearer ", "");
        ApiResponse<String> validationResponse = authService.validateToken(token);

        if (validationResponse == null || validationResponse.getCode() != 200) {
            ApiResponse<LeccionarioDto> errorResponse =
                    new ApiResponse<>(401, "Token inválido", null);
            return ResponseEntity.status(401).body(errorResponse);
        }
        try {
            return service.obtenerPorAsignatura(asignatura)
                    .map(leccionario -> ResponseEntity.ok(new ApiResponse<>(200, "Leccionario encontrado", leccionario)))
                    .orElse(ResponseEntity.status(404).body(new ApiResponse<>(404, "Leccionario no encontrado", null)));
        } catch (Exception e) {
            ApiResponse<LeccionarioDto> response = new ApiResponse<>(500, "Error al buscar leccionario: " + e.getMessage(), null);
            return ResponseEntity.status(500).body(response);
        }
    }

    @PostMapping("/crear")
    @Operation(summary = "Crear nuevo leccionario", description = "Permite crear un nuevo leccionario")
    public ResponseEntity<ApiResponse<LeccionarioDto>> crear(@RequestHeader("Authorization") String authHeader, @Valid @RequestBody LeccionarioDto leccionario) {
        String token = authHeader.replace("Bearer ", "");
        ApiResponse<String> validationResponse = authService.validateToken(token);

        if (validationResponse == null || validationResponse.getCode() != 200) {
            ApiResponse<LeccionarioDto> errorResponse =
                    new ApiResponse<>(401, "Token inválido", null);
            return ResponseEntity.status(401).body(errorResponse);
        }

        try {
            LeccionarioDto creado = service.crear(leccionario);
            ApiResponse<LeccionarioDto> response = new ApiResponse<>(201, "Leccionario creado", creado);
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            ApiResponse<LeccionarioDto> response = new ApiResponse<>(400, "Error al crear leccionario: " + e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar leccionario", description = "Permite actualizar los datos del leccionario")
    public ResponseEntity<ApiResponse<LeccionarioDto>> actualizar(@RequestHeader("Authorization") String authHeader, @PathVariable Long id, @Valid @RequestBody LeccionarioDto lec) {
        String token = authHeader.replace("Bearer ", "");
        ApiResponse<String> validationResponse = authService.validateToken(token);

        if (validationResponse == null || validationResponse.getCode() != 200) {
            ApiResponse<LeccionarioDto> errorResponse =
                    new ApiResponse<>(401, "Token inválido", null);
            return ResponseEntity.status(401).body(errorResponse);
        }

        try {
            LeccionarioDto actualizado = service.actualizar(id, lec);
            ApiResponse<LeccionarioDto> response = new ApiResponse<>(200, "Leccionario actualizado", actualizado);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            ApiResponse<LeccionarioDto> response = new ApiResponse<>(404, e.getMessage(), null);
            return ResponseEntity.status(404).body(response);
        } catch (Exception e) {
            ApiResponse<LeccionarioDto> response = new ApiResponse<>(500, "Error al actualizar leccionario: " + e.getMessage(), null);
            return ResponseEntity.status(500).body(response);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar leccionario por ID", description = "Permite eliminar por ID un leccionario específico")
    public ResponseEntity<ApiResponse<Void>> eliminar(@RequestHeader("Authorization") String authHeader, @PathVariable Long id) {
        String token = authHeader.replace("Bearer ", "");
        ApiResponse<String> validationResponse = authService.validateToken(token);

        if (validationResponse == null || validationResponse.getCode() != 200) {
            ApiResponse<Void> errorResponse =
                    new ApiResponse<>(401, "Token inválido", null);
            return ResponseEntity.status(401).body(errorResponse);
        }

        try {
            service.eliminar(id);
            ApiResponse<Void> response = new ApiResponse<>(200, "Leccionario eliminado", null);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            ApiResponse<Void> response = new ApiResponse<>(404, e.getMessage(), null);
            return ResponseEntity.status(404).body(response);
        } catch (Exception e) {
            ApiResponse<Void> response = new ApiResponse<>(500, "Error al eliminar leccionario: " + e.getMessage(), null);
            return ResponseEntity.status(500).body(response);
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        ApiResponse<Map<String, String>> response = new ApiResponse<>(400, "Error de validación", errors);
        return ResponseEntity.badRequest().body(response);
    }
}