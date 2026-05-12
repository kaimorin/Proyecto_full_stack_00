package com.proyecto.notas.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proyecto.notas.model.Notas;
import com.proyecto.notas.service.NotasService;

import java.util.List;

@RestController
@RequestMapping("/api/notas")
public class NotasController {

    @Autowired
    private NotasService notasService;

    @GetMapping
    public List<Notas> listar() {
        return notasService.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notas> obtener(@PathVariable Long id) {
        return notasService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Notas> crear(@Valid @RequestBody Notas notas) {
        return ResponseEntity.ok(notasService.guardar(notas));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Notas> actualizar(@PathVariable Long id, @Valid @RequestBody Notas nuevasNotas) {
        return notasService.buscarPorId(id).map(notaExistente -> {
            notaExistente.setAlumno(nuevasNotas.getAlumno());
            notaExistente.setAsignatura(nuevasNotas.getAsignatura());
            notaExistente.setValor(nuevasNotas.getValor());
            return ResponseEntity.ok(notasService.guardar(notaExistente));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (notasService.buscarPorId(id).isPresent()) {
            notasService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}