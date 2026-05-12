package com.proyecto.notas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.notas.model.Notas;
import com.proyecto.notas.repository.NotasRepository;

import java.util.List;
import java.util.Optional;

@Service
public class NotasService {

    @Autowired
    private NotasRepository notasRepository;

    public List<Notas> listarTodas() {
        return notasRepository.findAll();
    }

    public Optional<Notas> buscarPorId(Long id) {
        return notasRepository.findById(id);
    }

    public Notas guardar(Notas nota) {
        return notasRepository.save(nota);
    }

    public void eliminar(Long id) {
        notasRepository.deleteById(id);
    }
}
