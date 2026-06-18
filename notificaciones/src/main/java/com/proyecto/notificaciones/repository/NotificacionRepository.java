package com.proyecto.notificaciones.repository;

import com.proyecto.notificaciones.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para la entidad Notificacion.
 *
 * Proporciona operaciones de base de datos para gestionar notificaciones,
 * incluyendo búsqueda, guardado y eliminación.
 */
@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
}