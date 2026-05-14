package com.proyecto.reuniones.service;

import com.proyecto.reuniones.model.Reunion;
import com.proyecto.reuniones.repository.ReunionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReunionService {

    @Autowired
    private ReunionRepository reunionRepository;

    public List<Reunion> getAll() {
        return reunionRepository.findAll();
    }

    public Reunion getById(Long id) {
        return reunionRepository.findById(id).orElse(null);
    }

    public Reunion crear(Reunion reunion) {
        return reunionRepository.save(reunion);
    }

    public void eliminar(Long id) {
        reunionRepository.deleteById(id);
    }
}