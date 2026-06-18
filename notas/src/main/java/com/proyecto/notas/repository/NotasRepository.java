package com.proyecto.notas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.proyecto.notas.model.Notas;

/**
 * Repositorio JPA para la entidad Notas.
 *
 * Proporciona operaciones CRUD básicas sobre la tabla de notas,
 * incluyendo búsqueda por ID y listado completo.
 */
@Repository
public interface NotasRepository extends JpaRepository<Notas, Long> {
    
}