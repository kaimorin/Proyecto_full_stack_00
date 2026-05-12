package com.proyecto.notas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.proyecto.notas.model.Notas;

@Repository
public interface NotasRepository extends JpaRepository<Notas, Long> {
    
}