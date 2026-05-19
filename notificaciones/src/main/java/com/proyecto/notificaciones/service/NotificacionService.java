package com.proyecto.notificaciones.service;

import com.proyecto.notificaciones.model.Notificacion;
import com.proyecto.notificaciones.repository.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotificacionService {

    @Autowired
    private NotificacionRepository repository;

    public List<Notificacion> listar() {
        return repository.findAll();
    }

    public Notificacion guardar(Notificacion notificacion) {
        return repository.save(notificacion);
    }

    public void marcarComoLeida(Long id) {
        repository.findById(id).ifPresent(n -> {
            n.setLeida(true);
            repository.save(n);
        });
    }
}