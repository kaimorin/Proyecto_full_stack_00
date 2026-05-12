package com.proyecto.leccionario.controller;

import com.proyecto.leccionario.model.Leccionario;
import com.proyecto.leccionario.service.LeccionarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/leccionario")
@RequiredArgsConstructor
public class LeccionarioController {

    private final LeccionarioService service;

    @GetMapping
    public List<Leccionario> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Leccionario> obtener(@PathVariable Long id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar")
    public ResponseEntity<Leccionario> buscar(@RequestParam String asignatura) {
        return service.obtenerPorAsignatura(asignatura)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Leccionario crear(@RequestBody Leccionario leccionario) {
        return service.crear(leccionario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Leccionario> actualizar(@PathVariable Long id, @RequestBody Leccionario lec) {
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
}