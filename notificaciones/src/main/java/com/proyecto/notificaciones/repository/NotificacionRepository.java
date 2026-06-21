package com.proyecto.notificaciones.repository;

import com.proyecto.notificaciones.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    
    
    Optional<Notificacion> findByOrigen(String origen);
}