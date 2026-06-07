package com.proyecto.notas.service;

import com.proyecto.notas.model.Notas;
import com.proyecto.notas.dto.NotasDto;
import com.proyecto.notas.repository.NotasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    // Método para guardar una entidad directa (usado en el Update)
    public Notas guardar(Notas nota) {
        return notasRepository.save(nota);
    }

    //  Recibe el DTO, lo mapea a la Entidad y lo persiste
    public Notas crearNota(NotasDto dto) {
        Notas nota = new Notas();
        nota.setEstudiante(dto.getEstudiante());
        nota.setAsignatura(dto.getAsignatura());
        nota.setValor(dto.getValor());
        
        // Aquí es donde en el futuro llamaremos al microservicio de notificaciones justo antes del return
        return notasRepository.save(nota);
    }

    public void eliminar(Long id) {
        notasRepository.deleteById(id);
    }
}