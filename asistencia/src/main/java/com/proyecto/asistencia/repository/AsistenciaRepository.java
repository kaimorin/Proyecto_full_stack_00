package com.proyecto.asistencia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.proyecto.asistencia.model.Asistencia;

public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {
    
}