package com.proyecto.leccionario.service;

import com.proyecto.leccionario.dto.LeccionarioDto;
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

    public List<LeccionarioDto> listarTodos() {
        return repository.findAll().stream()
                .map(this::entityToDto)
                .toList();
    }

    public Optional<LeccionarioDto> obtenerPorId(Long id) {
        return repository.findById(id).map(this::entityToDto);
    }

    public Optional<LeccionarioDto> obtenerPorAsignatura(String asignatura) {
        return repository.findByAsignatura(asignatura).map(this::entityToDto);
    }

    public LeccionarioDto crear(LeccionarioDto leccionarioDto) {
        Leccionario leccionario = dtoToEntity(leccionarioDto);
        return entityToDto(repository.save(leccionario));
    }

    public LeccionarioDto actualizar(Long id, LeccionarioDto data) {
        return repository.findById(id).map(l -> {
            l.setAsignatura(data.getAsignatura());
            l.setFecha(data.getFecha());
            l.setContenido(data.getContenido());
            l.setIdProfesor(data.getIdProfesor());
            return entityToDto(repository.save(l));
        }).orElseThrow(() -> new RuntimeException("No se encontró el leccionario"));
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    private Leccionario dtoToEntity(LeccionarioDto dto) {
        return Leccionario.builder()
                .id(dto.getId())
                .asignatura(dto.getAsignatura())
                .fecha(dto.getFecha())
                .contenido(dto.getContenido())
                .idProfesor(dto.getIdProfesor())
                .build();
    }

    private LeccionarioDto entityToDto(Leccionario entity) {
        return LeccionarioDto.builder()
                .id(entity.getId())
                .asignatura(entity.getAsignatura())
                .fecha(entity.getFecha())
                .contenido(entity.getContenido())
                .idProfesor(entity.getIdProfesor())
                .build();
    }
}