package com.proyecto.notas.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proyecto.notas.model.Notas;
import com.proyecto.notas.dto.NotasDto;
import com.proyecto.notas.dto.ApiResponse;
import com.proyecto.notas.service.NotasService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/notas")
@Tag(name = "Notas", description = "API para gestionar notas de estudiantes")
public class NotasController {

    @Autowired
    private NotasService notasService;

    @GetMapping
    @Operation(summary = "Listar todas las notas", description = "Obtiene la lista completa de todas las notas registradas")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Notas obtenidas exitosamente"),
        @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    public ResponseEntity<com.proyecto.notas.dto.ApiResponse<List<Notas>>> listar() {
        List<Notas> lista = notasService.listarTodas();
        com.proyecto.notas.dto.ApiResponse<List<Notas>> response = new com.proyecto.notas.dto.ApiResponse<>(200, "Lista de notas obtenida con éxito", lista);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener nota por ID", description = "Obtiene una nota específica por su identificador")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Nota encontrada"),
        @ApiResponse(responseCode = "404", description = "Nota no encontrada")
    })
    public ResponseEntity<com.proyecto.notas.dto.ApiResponse<Notas>> obtener(
            @Parameter(description = "ID de la nota a obtener", required = true, example = "1")
            @PathVariable Long id) {
        return notasService.buscarPorId(id)
                .map(nota -> ResponseEntity.ok(new com.proyecto.notas.dto.ApiResponse<>(200, "Nota encontrada", nota)))
                .orElse(ResponseEntity.status(404).body(new com.proyecto.notas.dto.ApiResponse<>(404, "Nota no encontrada", null)));
    }

    @PostMapping
    @Operation(summary = "Crear nueva nota", description = "Registra una nueva nota para un estudiante")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Nota creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<com.proyecto.notas.dto.ApiResponse<Notas>> crear(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la nota a crear",
                    required = true,
                    content = @Content(schema = @Schema(implementation = NotasDto.class))
            )
            @Valid @RequestBody NotasDto notasDto) {
        Notas nuevaNota = notasService.crearNota(notasDto);
        com.proyecto.notas.dto.ApiResponse<Notas> response = new com.proyecto.notas.dto.ApiResponse<>(201, "Nota registrada exitosamente", nuevaNota);
        return ResponseEntity.status(201).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar nota", description = "Actualiza los datos de una nota existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Nota actualizada correctamente"),
        @ApiResponse(responseCode = "404", description = "Nota no encontrada"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<com.proyecto.notas.dto.ApiResponse<Notas>> actualizar(
            @Parameter(description = "ID de la nota a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Nuevos datos de la nota",
                    required = true,
                    content = @Content(schema = @Schema(implementation = NotasDto.class))
            )
            @Valid @RequestBody NotasDto notasDto) {
        return notasService.buscarPorId(id).map(notaExistente -> {
            notaExistente.setEstudiante(notasDto.getEstudiante());
            notaExistente.setAsignatura(notasDto.getAsignatura());
            notaExistente.setValor(notasDto.getValor());
            Notas actualizado = notasService.guardar(notaExistente);
            return ResponseEntity.ok(new com.proyecto.notas.dto.ApiResponse<>(200, "Nota actualizada correctamente", actualizado));
        }).orElse(ResponseEntity.status(404).body(new com.proyecto.notas.dto.ApiResponse<>(404, "Nota no encontrada", null)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar nota", description = "Elimina una nota por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Nota eliminada correctamente"),
        @ApiResponse(responseCode = "404", description = "Nota no encontrada")
    })
    public ResponseEntity<com.proyecto.notas.dto.ApiResponse<Void>> eliminar(
            @Parameter(description = "ID de la nota a eliminar", required = true, example = "1")
            @PathVariable Long id) {
        if (notasService.buscarPorId(id).isPresent()) {
            notasService.eliminar(id);
            return ResponseEntity.ok(new com.proyecto.notas.dto.ApiResponse<>(200, "Nota eliminada correctamente", null));
        }
        return ResponseEntity.status(404).body(new com.proyecto.notas.dto.ApiResponse<>(404, "Nota no encontrada", null));
    }
}

