package com.proyecto.matriculas.service;

import com.proyecto.matriculas.model.Matricula;
import com.proyecto.matriculas.repository.MatriculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatriculaService {

    @Autowired
    private MatriculaRepository matriculaRepository;

    public List<Matricula> getAll() {
        return matriculaRepository.findAll();
    }

    public Matricula getById(Long id) {
        return matriculaRepository.findById(id).orElse(null);
    }

    public Matricula crear(Matricula matricula) {
        return matriculaRepository.save(matricula);
    }

    public void eliminar(Long id) {
        matriculaRepository.deleteById(id);
    }
}