package com.proyecto.notificaciones.controller;

import com.proyecto.notificaciones.model.Notificacion;
import com.proyecto.notificaciones.service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService service;

    @GetMapping
    public List<Notificacion> obtenerTodas() {
        return service.listar();
    }

    @PostMapping
    public Notificacion crear(@RequestBody Notificacion notificacion) {
        return service.guardar(notificacion);
    }

    @PatchMapping("/{id}/leer")
    public void leer(@PathVariable Long id) {
        service.marcarComoLeida(id);
    }
}
