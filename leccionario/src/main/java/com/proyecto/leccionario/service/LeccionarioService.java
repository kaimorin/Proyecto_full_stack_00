package com.proyecto.leccionario.service;

import com.proyecto.leccionario.model.Curso;
import com.proyecto.leccionario.model.Leccionario;
import com.proyecto.leccionario.repository.CursoRepository;
import com.proyecto.leccionario.repository.RepositoryLeccionario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LeccionarioService {

    private final RepositoryLeccionario repository;
    private final CursoRepository cursoRepository;

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
        // Resolver curso si se proporciona solo el id en la petición
        Curso c = null;
        if (leccionario.getCurso() != null && leccionario.getCurso().getId() != null) {
            c = cursoRepository.findById(leccionario.getCurso().getId()).orElse(null);
            leccionario.setCurso(c);
        }
        return repository.save(leccionario);
    }

    public Leccionario actualizar(Long id, Leccionario data) {
        return repository.findById(id).map(l -> {
            l.setAsignatura(data.getAsignatura());
            l.setFecha(data.getFecha());
            l.setContenido(data.getContenido());
            l.setIdProfesor(data.getIdProfesor());
            if (data.getCurso() != null && data.getCurso().getId() != null) {
                Curso c = cursoRepository.findById(data.getCurso().getId()).orElse(null);
                l.setCurso(c);
            }
            return repository.save(l);
        }).orElseThrow(() -> new RuntimeException("No se encontró el leccionario"));
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}