package com.proyecto.matriculas.service;

import org.springframework.stereotype.Service;
import com.proyecto.matriculas.dto.MatriculaDTO;
import com.proyecto.matriculas.model.Matricula;
import com.proyecto.matriculas.repository.MatriculaRepository;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatriculaService {

    private final MatriculaRepository matriculaRepository;

    public List<MatriculaDTO> getAllMatriculasDTO() {
        return matriculaRepository.findAll()
                .stream()
                .map(m -> new MatriculaDTO(
                        m.getRunEstudiante(),
                        m.getNombreEstudiante(),
                        m.getCurso(),
                        m.getEstado()
                ))
                .toList();
    }

    public MatriculaDTO getMatriculaById(Long id) {
        Matricula m = matriculaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Matrícula no encontrada con id: " + id));
        return new MatriculaDTO(m.getRunEstudiante(), m.getNombreEstudiante(), m.getCurso(), m.getEstado());
    }

    public MatriculaDTO createMatricula(MatriculaDTO dto) {
        Matricula m = new Matricula(null, dto.getRunEstudiante(), dto.getNombreEstudiante(),
                dto.getCurso(), dto.getEstado());
        Matricula saved = matriculaRepository.save(m);
        return new MatriculaDTO(saved.getRunEstudiante(), saved.getNombreEstudiante(),
                saved.getCurso(), saved.getEstado());
    }
public MatriculaDTO updateMatricula(Long id, MatriculaDTO dto) {
        Matricula m = matriculaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Matrícula no encontrada con id: " + id));
        m.setRunEstudiante(dto.getRunEstudiante());
        m.setNombreEstudiante(dto.getNombreEstudiante());
        m.setCurso(dto.getCurso());
        m.setEstado(dto.getEstado());
        Matricula updated = matriculaRepository.save(m);
        return new MatriculaDTO(updated.getRunEstudiante(), updated.getNombreEstudiante(),
                updated.getCurso(), updated.getEstado());
    }

    public void deleteMatricula(Long id) {
        matriculaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Matrícula no encontrada con id: " + id));
        matriculaRepository.deleteById(id);
    }
}