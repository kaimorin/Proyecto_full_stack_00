package com.proyecto.reuniones.controller;

import com.proyecto.reuniones.model.Reunion;
import com.proyecto.reuniones.service.ReunionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reuniones")
public class ReunionController {

    @Autowired
    private ReunionService reunionService;

    @GetMapping
    public List<Reunion> getAll() {
        return reunionService.getAll();
    }

    @GetMapping("/{id}")
    public Reunion getById(@PathVariable Long id) {
        return reunionService.getById(id);
    }

    @PostMapping
    public Reunion crear(@RequestBody Reunion reunion) {
        return reunionService.crear(reunion);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        reunionService.eliminar(id);
    }
}