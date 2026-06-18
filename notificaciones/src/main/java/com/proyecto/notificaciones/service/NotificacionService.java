package com.proyecto.notificaciones.service;

import com.proyecto.notificaciones.model.Notificacion;
import com.proyecto.notificaciones.dto.NotificacionDto;
import com.proyecto.notificaciones.repository.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Servicio de negocios para las notificaciones.
 *
 * Encapsula la lógica de persistencia y las operaciones CRUD para
 * la entidad Notificacion.
 */
@Service
public class NotificacionService {

    @Autowired
    private NotificacionRepository repository;

    /**
     * Recupera todas las notificaciones almacenadas.
     */
    public List<Notificacion> listar() {
        return repository.findAll();
    }

    /**
     * Busca una notificación por su identificador.
     *
     * @param id ID de la notificación.
     * @return Optional con la notificación si existe.
     */
    public Optional<Notificacion> buscarPorId(Long id) {
        return repository.findById(id);
    }

    /**
     * Guarda o actualiza una notificación en la base de datos.
     */
    public Notificacion guardar(Notificacion notificacion) {
        return repository.save(notificacion);
    }

    /**
     * Crea una nueva notificación a partir de un DTO y la persiste.
     */
    public Notificacion crear(NotificacionDto dto) {
        Notificacion notificacion = new Notificacion();
        notificacion.setMensaje(dto.getMensaje());
        notificacion.setOrigen(dto.getOrigen());
        notificacion.setLeida(false);
        return repository.save(notificacion);
    }

    /**
     * Marca una notificación como leída.
     */
    public void marcarComoLeida(Long id) {
        repository.findById(id).ifPresent(n -> {
            n.setLeida(true);
            repository.save(n);
        });
    }

    /**
     * Elimina una notificación por su ID.
     */
    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
