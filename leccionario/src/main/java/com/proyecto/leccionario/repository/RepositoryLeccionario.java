package com.proyecto.leccionario.repository;

import com.proyecto.leccionario.model.Leccionario;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryLeccionario extends JpaRepository<Leccionario, Long> {
    Optional<Leccionario> findByAsignatura(String asignatura);
    
   
    List<Leccionario> findByIdProfesor(Long idProfesor);
}