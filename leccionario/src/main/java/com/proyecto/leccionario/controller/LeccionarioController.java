package com.proyecto.leccionario.controller;

import com.proyecto.leccionario.dto.LeccionarioDto;
import com.proyecto.leccionario.service.LeccionarioService;
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

    @GetMapping
    public List<LeccionarioDto> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeccionarioDto> obtener(@PathVariable Long id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar")
    public ResponseEntity<LeccionarioDto> buscar(@RequestParam String asignatura) {
        return service.obtenerPorAsignatura(asignatura)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<LeccionarioDto> crear(@Valid @RequestBody LeccionarioDto leccionario) {
        LeccionarioDto creado = service.crear(leccionario);
        return ResponseEntity.ok(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeccionarioDto> actualizar(@PathVariable Long id, @Valid @RequestBody LeccionarioDto lec) {
        try {
            return ResponseEntity.ok(service.actualizar(id, lec));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }
}