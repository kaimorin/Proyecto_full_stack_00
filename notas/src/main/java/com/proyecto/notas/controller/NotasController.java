package com.proyecto.notas.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proyecto.notas.model.Notas;
import com.proyecto.notas.dto.NotasDto;
import com.proyecto.notas.dto.ApiResponse;
import com.proyecto.notas.service.NotasService;
import com.proyecto.notas.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notas")
public class NotasController {

    private final NotasService notasService;
    private final AuthService authService;

    public NotasController(NotasService notasService, AuthService authService) {
        this.notasService = notasService;
        this.authService = authService;
    }

    // * Método Listar Notas Existentes * //

    @GetMapping

    @Operation(summary = "Listar todas las notas", description = "Obtiene la lista completa de todas las notas registradas")
    public ResponseEntity<ApiResponse<List<Notas>>> listar(@RequestHeader("Authorization") String authHeader) {

        String token = authHeader.replace("Bearer ", "");
        ApiResponse<String> validationResponse = authService.validateToken(token);

        if (validationResponse == null || validationResponse.getCode() != 200) {
            ApiResponse<List<Notas>> errorResponse =
                    new ApiResponse<>(401, "Token inválido o no proporcionado", null);
            return ResponseEntity.status(401).body(errorResponse);
        }

        List<Notas> lista = notasService.listarTodas();
        ApiResponse<List<Notas>> response =
                new ApiResponse<>(200, "Lista de notas obtenida con éxito", lista);
        return ResponseEntity.ok(response);
    }

    // * Método Obtener Nota por ID * //

    @GetMapping("/{id}")
    @Operation(summary = "Obtener nota por ID", description = "Obtiene una nota específica por su identificador")

    public ResponseEntity<ApiResponse<Notas>> obtener(@RequestHeader("Authorization") String authHeader, @PathVariable Long id) {
        String token = authHeader.replace("Bearer ", "");
        ApiResponse<String> validationResponse = authService.validateToken(token);

        if (validationResponse == null || validationResponse.getCode() != 200) {
            ApiResponse<Notas> errorResponse =
                new ApiResponse<>(401, "Token inválido o no proporcionado", null);
            return ResponseEntity.status(401).body(errorResponse);
        }

        return notasService.buscarPorId(id)
                .map(nota -> ResponseEntity.ok(new ApiResponse<>(200, "Nota encontrada", nota)))
                .orElse(ResponseEntity.status(404).body(new ApiResponse<>(404, "Nota no encontrada", null)));
    }

    // * Método Crear Nota Nueva * //

    @PostMapping
    @Operation(summary = "Crear nueva nota", description = "Registra una nueva nota para un estudiante")

    public ResponseEntity<ApiResponse<Notas>> crear(@RequestHeader("Authorization") String authHeader, @Valid @RequestBody NotasDto notasDto){
        String token = authHeader.replace("Bearer ", "");
        ApiResponse<String> validationResponse = authService.validateToken(token);

        if (validationResponse == null || validationResponse.getCode() != 200) {
            ApiResponse<Notas> errorResponse =
                new ApiResponse<>(401, "Token inválido o no proporcionado", null);
            return ResponseEntity.status(401).body(errorResponse);
        }

        Notas nuevaNota = notasService.crearNota(notasDto);
        ApiResponse<Notas> response =
            new ApiResponse<>(201, "Nota registrada exitosamente", nuevaNota);
        return ResponseEntity.status(201).body(response);

    }

    // * Método Actualizar Nota * //

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar nota", description = "Actualiza los datos de una nota existente")

    public ResponseEntity<ApiResponse<Notas>> actualizar(@RequestHeader("Authorization") String authHeader, @Valid @RequestBody NotasDto notasDto, @PathVariable Long id){

        String token = authHeader.replace("Bearer ", "");
        ApiResponse<String> validationResponse = authService.validateToken(token);

        if (validationResponse == null || validationResponse.getCode() != 200) {
            ApiResponse<Notas> errorResponse =
                new ApiResponse<>(401, "Token inválido o no proporcionado", null);
            return ResponseEntity.status(401).body(errorResponse);
        }

        return notasService.buscarPorId(id).map(notaExistente -> {
            notaExistente.setEstudiante(notasDto.getEstudiante());
            notaExistente.setAsignatura(notasDto.getAsignatura());
            notaExistente.setValor(notasDto.getValor());
            Notas actualizado = notasService.guardar(notaExistente);
            return ResponseEntity.ok(new ApiResponse<>(200, "Nota actualizada correctamente", actualizado));
        }).orElse(ResponseEntity.status(404).body(new ApiResponse<>(404, "Nota no encontrada", null)));
    }

    // * Método Eliminar Nota * //

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar nota", description = "Elimina una nota por su ID")

    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        ApiResponse<String> validationResponse = authService.validateToken(token);

        if (validationResponse == null || validationResponse.getCode() != 200) {
            ApiResponse<Void> errorResponse =
                new ApiResponse<>(401, "Token inválido o no proporcionado", null);
            return ResponseEntity.status(401).body(errorResponse);
        }

        if (notasService.buscarPorId(id).isPresent()) {
            notasService.eliminar(id);
            return ResponseEntity.ok(new ApiResponse<>(200, "Nota eliminada correctamente", null));
        }
        return ResponseEntity.status(404).body(new ApiResponse<>(404, "Nota no encontrada", null));
    }
}