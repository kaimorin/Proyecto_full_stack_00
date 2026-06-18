package com.proyecto.notas.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proyecto.notas.model.Notas;
import com.proyecto.notas.dto.NotasDto;
import com.proyecto.notas.dto.ApiResponse; // Simplificado el import para limpiar el código
import com.proyecto.notas.service.NotasService;
import com.proyecto.notas.service.AuthService; // Importamos tu AuthService

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

/**
 * Controlador REST para el microservicio de notas.
 *
 * Gestiona las solicitudes HTTP entrantes relacionadas con operaciones CRUD
 * sobre las notas de estudiantes.
 */
@RestController
@RequestMapping("/api/notas")
@Tag(name = "Notas", description = "API para gestionar notas de estudiantes")
public class NotasController {

    @Autowired
    private NotasService notasService;

    @Autowired
    private AuthService authService; // Inyección del servicio de Auth

    /**
     * Método auxiliar privado para validar el token Bearer en cada petición.
     * Esto evita repetir el mismo "if" en todos los endpoints.
     */
    private boolean isTokenInvalid(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return true;
        }
        String token = authHeader.replace("Bearer ", "");
        ApiResponse<String> validationResponse = authService.validateToken(token);
        return validationResponse == null || validationResponse.getCode() != 200;
    }

    /**
     * Lista todas las notas existentes.
     */
    @GetMapping
    @Operation(summary = "Listar todas las notas", description = "Obtiene la lista completa de todas las notas registradas")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Notas obtenidos exitosamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autorizado")
    })
    public ResponseEntity<ApiResponse<List<Notas>>> listar(@RequestHeader("Authorization") String authHeader) {
        if (isTokenInvalid(authHeader)) {
            return ResponseEntity.status(401).body(new ApiResponse<>(401, "Token inválido o no proporcionado", null));
        }

        List<Notas> lista = notasService.listarTodas();
        ApiResponse<List<Notas>> response = new ApiResponse<>(200, "Lista de notas obtenida con éxito", lista);
        return ResponseEntity.ok(response);
    }

    /**
     * Devuelve una nota específica por su ID.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener nota por ID", description = "Obtiene una nota específica por su identificador")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Nota encontrada"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autorizado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Nota no encontrada")
    })
    public ResponseEntity<ApiResponse<Notas>> obtener(
            @RequestHeader("Authorization") String authHeader,
            @Parameter(description = "ID de la nota a obtener", required = true, example = "1")
            @PathVariable Long id) {
        if (isTokenInvalid(authHeader)) {
            return ResponseEntity.status(401).body(new ApiResponse<>(401, "Token inválido o no proporcionado", null));
        }

        return notasService.buscarPorId(id)
                .map(nota -> ResponseEntity.ok(new ApiResponse<>(200, "Nota encontrada", nota)))
                .orElse(ResponseEntity.status(404).body(new ApiResponse<>(404, "Nota no encontrada", null)));
    }

    /**
     * Crea una nueva nota a partir de los datos enviados en el cuerpo de la solicitud.
     */
    @PostMapping
    @Operation(summary = "Crear nueva nota", description = "Registra una nueva nota para un estudiante")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Nota creada exitosamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autorizado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<ApiResponse<Notas>> crear(
            @RequestHeader("Authorization") String authHeader,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la nota a crear",
                    required = true,
                    content = @Content(schema = @Schema(implementation = NotasDto.class))
            )
            @Valid @RequestBody NotasDto notasDto) {
        if (isTokenInvalid(authHeader)) {
            return ResponseEntity.status(401).body(new ApiResponse<>(401, "Token inválido o no proporcionado", null));
        }

        Notas nuevaNota = notasService.crearNota(notasDto);
        ApiResponse<Notas> response = new ApiResponse<>(201, "Nota registrada exitosamente", nuevaNota);
        return ResponseEntity.status(201).body(response);
    }

    /**
     * Actualiza los datos de una nota existente.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar nota", description = "Actualiza los datos de una nota existente")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Nota actualizada correctamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autorizado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Nota no encontrada"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<ApiResponse<Notas>> actualizar(
            @RequestHeader("Authorization") String authHeader,
            @Parameter(description = "ID de la nota a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Nuevos datos de la nota",
                    required = true,
                    content = @Content(schema = @Schema(implementation = NotasDto.class))
            )
            @Valid @RequestBody NotasDto notasDto) {
        if (isTokenInvalid(authHeader)) {
            return ResponseEntity.status(401).body(new ApiResponse<>(401, "Token inválido o no proporcionado", null));
        }

        return notasService.buscarPorId(id).map(notaExistente -> {
            notaExistente.setEstudiante(notasDto.getEstudiante());
            notaExistente.setAsignatura(notasDto.getAsignatura());
            notaExistente.setValor(notasDto.getValor());
            Notas actualizado = notasService.guardar(notaExistente);
            return ResponseEntity.ok(new ApiResponse<>(200, "Nota actualizada correctamente", actualizado));
        }).orElse(ResponseEntity.status(404).body(new ApiResponse<>(404, "Nota no encontrada", null)));
    }

    /**
     * Elimina una nota por su ID.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar nota", description = "Elimina una nota por su ID")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Nota eliminada correctamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autorizado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Nota no encontrada")
    })
    public ResponseEntity<ApiResponse<Void>> eliminar(
            @RequestHeader("Authorization") String authHeader,
            @Parameter(description = "ID de la nota a eliminar", required = true, example = "1")
            @PathVariable Long id) {
        if (isTokenInvalid(authHeader)) {
            return ResponseEntity.status(401).body(new ApiResponse<>(401, "Token inválido o no proporcionado", null));
        }

        if (notasService.buscarPorId(id).isPresent()) {
            notasService.eliminar(id);
            return ResponseEntity.ok(new ApiResponse<>(200, "Nota CLI eliminada correctamente", null));
        }
        return ResponseEntity.status(404).body(new ApiResponse<>(404, "Nota no encontrada", null));
    }
}