package com.proyecto.notificaciones.service;

import com.proyecto.notificaciones.model.Notificacion;
import com.proyecto.notificaciones.dto.NotificacionDto;
import com.proyecto.notificaciones.repository.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class NotificacionService {

    @Autowired
    private NotificacionRepository repository;

    public List<Notificacion> listar() {
        return repository.findAll();
    }

    public Optional<Notificacion> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Notificacion guardar(Notificacion notificacion) {
        return repository.save(notificacion);
    }

    public Notificacion crear(NotificacionDto dto) {
        Notificacion notificacion = new Notificacion();
        notificacion.setMensaje(dto.getMensaje());
        notificacion.setOrigen(dto.getOrigen());
        notificacion.setLeida(false);
        return repository.save(notificacion);
    }

    public void marcarComoLeida(Long id) {
        repository.findById(id).ifPresent(n -> {
            n.setLeida(true);
            repository.save(n);
        });
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
