package com.proyecto.matriculas.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/matriculas")
public class MatriculaController {

    @GetMapping
    public String hola() {
        return "Microservicio de matrículas funcionando";
    }
}