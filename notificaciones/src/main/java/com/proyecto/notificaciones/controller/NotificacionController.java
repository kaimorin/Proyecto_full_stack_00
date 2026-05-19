package com.proyecto.notificaciones.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.notificaciones.model.Notificacion;
import com.proyecto.notificaciones.service.NotificacionService;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService service;

    @GetMapping("/list")
    public List<Notificacion> obtenerTodas() {
        return service.listar();
    }

    @PostMapping("/create")
    public Notificacion crear(@RequestBody Notificacion notificacion) {
        return service.guardar(notificacion);
    }

    @PatchMapping("/leer/{id}")
    public void leer(@PathVariable Long id) {
        service.marcarComoLeida(id);
    }
}
