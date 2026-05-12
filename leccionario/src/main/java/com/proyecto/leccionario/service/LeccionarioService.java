package com.proyecto.leccionario.service;

import com.proyecto.leccionario.model.Leccionario;
import com.proyecto.leccionario.repository.RepositoryLeccionario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LeccionarioService {

    private final RepositoryLeccionario repository;

    public List<Leccionario> listarTodos() {
        return repository.findAll();
    }

    public Optional<Leccionario> obtenerPorId(Long id) {
        return repository.findById(id);
    }

    public Optional<Leccionario> obtenerPorAsignatura(String asignatura) {
        return repository.findByAsignatura(asignatura);
    }

    public Leccionario crear(Leccionario leccionario) {
        return repository.save(leccionario);
    }

    public Leccionario actualizar(Long id, Leccionario data) {
        return repository.findById(id).map(l -> {
            l.setAsignatura(data.getAsignatura());
            l.setFecha(data.getFecha());
            l.setContenido(data.getContenido());
            l.setIdProfesor(data.getIdProfesor());
            l.setIdCurso(data.getIdCurso());
            return repository.save(l);
        }).orElseThrow(() -> new RuntimeException("No se encontró el leccionario"));
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}