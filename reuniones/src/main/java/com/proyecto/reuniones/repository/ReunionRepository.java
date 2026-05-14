package com.proyecto.reuniones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.proyecto.reuniones.model.Reunion;

public interface ReunionRepository extends JpaRepository<Reunion, Long> {


    
}