package com.proyecto.asistencia.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.proyecto.asistencia.dto.AsistenciaDTO;
import com.proyecto.asistencia.model.Asistencia;
import com.proyecto.asistencia.repository.AsistenciaRepository;
import com.proyecto.asistencia.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AsistenciaService {

    private final AsistenciaRepository asistenciaRepository;

    // Listar todo el registro de asistencia
    public List<AsistenciaDTO> getAllAsistenciasDTO() {
        return asistenciaRepository.findAll()
                .stream()
                .map(a -> new AsistenciaDTO(
                        a.getFecha(),
                        a.getEstadoAsistencia(),
                        a.getRutAlumno()
                ))
                .toList();
    }

    // Obtener registro de asistencia por ID
    public AsistenciaDTO getAsistenciaById(Long id) {
        Asistencia a = asistenciaRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Asistencia no encontrada con id: " + id));
        return new AsistenciaDTO(a.getFecha(), a.getEstadoAsistencia(), a.getRutAlumno());
    }

    // Crear nuevo registro de asistencia
    public AsistenciaDTO createAsistencia(AsistenciaDTO dto) {
        Asistencia a = new Asistencia(null, dto.getFecha(), dto.getEstadoAsistencia(), dto.getRutAlumno());
        Asistencia saved = asistenciaRepository.save(a);
        return new AsistenciaDTO(saved.getFecha(), saved.getEstadoAsistencia(), saved.getRutAlumno());
    }

    // Actualizar registro de asistencia
    public AsistenciaDTO updateAsistencia(Long id, AsistenciaDTO dto) {
        Asistencia a = asistenciaRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Asistencia no encontrada con id: " + id));
        a.setFecha(dto.getFecha());
        a.setEstadoAsistencia(dto.getEstadoAsistencia());
        a.setRutAlumno(dto.getRutAlumno());
        Asistencia updated = asistenciaRepository.save(a);
        return new AsistenciaDTO(updated.getFecha(), updated.getEstadoAsistencia(), updated.getRutAlumno());
    }

}