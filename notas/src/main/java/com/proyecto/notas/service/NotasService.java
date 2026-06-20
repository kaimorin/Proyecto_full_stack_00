package com.proyecto.notas.service;

import com.proyecto.notas.dto.NotasDto;
import com.proyecto.notas.model.Notas;
import com.proyecto.notas.repository.NotasRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor 
public class NotasService {

    private final NotasRepository notasRepository;

    public List<NotasDto> listarTodas() {
        
        return notasRepository.findAll().stream()
                .map(this::entityToDto)
                .toList();
    }

    public Optional<NotasDto> buscarPorId(Long id) {
        // Busca en la base de datos y mapea el resultado a DTO si existe
        return notasRepository.findById(id).map(this::entityToDto);
    }

    public NotasDto crearNota(NotasDto dto) {
        // Convierte el DTO de entrada a Entidad para guardarlo en la BD
        Notas nota = dtoToEntity(dto);
        
        // Aquí es donde en el futuro llamaremos al microservicio de notificaciones justo antes del return
        return entityToDto(notasRepository.save(nota));
    }

    public NotasDto actualizar(Long id, NotasDto data) {
        
        return notasRepository.findById(id).map(nota -> {
            nota.setEstudiante(data.getEstudiante());
            nota.setAsignatura(data.getAsignatura());
            nota.setValor(data.getValor());
            return entityToDto(notasRepository.save(nota));
        }).orElseThrow(() -> new RuntimeException("No se encontró la nota"));
    }

    public void eliminar(Long id) {
        
        notasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró la nota"));

        notasRepository.deleteById(id);
    }


    private Notas dtoToEntity(NotasDto dto) {
        return Notas.builder()
                .id(dto.getId())
                .estudiante(dto.getEstudiante())
                .asignatura(dto.getAsignatura())
                .valor(dto.getValor())
                .build();
    }

    private NotasDto entityToDto(Notas entity) {
        return NotasDto.builder()
                .id(entity.getId())
                .estudiante(entity.getEstudiante())
                .asignatura(entity.getAsignatura())
                .valor(entity.getValor())
                .build();
    }
}