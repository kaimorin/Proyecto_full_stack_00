package com.proyecto.matriculas.controller;

import com.proyecto.matriculas.model.Matricula;
import com.proyecto.matriculas.service.MatriculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/matriculas")
public class MatriculaController {

    @Autowired
    private MatriculaService matriculaService;

    @GetMapping
    public List<Matricula> getAll() {
        return matriculaService.getAll();
    }

    @GetMapping("/{id}")
    public Matricula getById(@PathVariable Long id) {
        return matriculaService.getById(id);
    }

    @PostMapping
    public Matricula crear(@RequestBody Matricula matricula) {
        return matriculaService.crear(matricula);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        matriculaService.eliminar(id);
    }
}