package com.proyecto.reuniones.service;

import org.springframework.stereotype.Service;
import com.proyecto.reuniones.dto.ReunionDTO;
import com.proyecto.reuniones.model.Reunion;
import com.proyecto.reuniones.repository.ReunionRepository;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReunionService {

    private final ReunionRepository reunionRepository;

    public List<ReunionDTO> getAllReunionesDTO() {
        return reunionRepository.findAll()
                .stream()
                .map(r -> new ReunionDTO(
                        r.getFecha(),
                        r.getHora(),
                        r.getMotivo(),
                        r.getCurso(),
                        r.getNombreEncargado()
                ))
                .toList();
    }

    public ReunionDTO getReunionById(Long id) {
        Reunion r = reunionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reunión no encontrada con id: " + id));
        return new ReunionDTO(r.getFecha(), r.getHora(), r.getMotivo(), r.getCurso(), r.getNombreEncargado());
    }

    public ReunionDTO createReunion(ReunionDTO dto) {
        Reunion r = new Reunion(null, dto.getFecha(), dto.getHora(), dto.getMotivo(),
                dto.getCurso(), dto.getNombreEncargado());
        Reunion saved = reunionRepository.save(r);
        return new ReunionDTO(saved.getFecha(), saved.getHora(), saved.getMotivo(),
                saved.getCurso(), saved.getNombreEncargado());
    }
public ReunionDTO updateReunion(Long id, ReunionDTO dto) {
        Reunion r = reunionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reunión no encontrada con id: " + id));
        r.setFecha(dto.getFecha());
        r.setHora(dto.getHora());
        r.setMotivo(dto.getMotivo());
        r.setCurso(dto.getCurso());
        r.setNombreEncargado(dto.getNombreEncargado());
        Reunion updated = reunionRepository.save(r);
        return new ReunionDTO(updated.getFecha(), updated.getHora(), updated.getMotivo(),
                updated.getCurso(), updated.getNombreEncargado());
    }

    public void deleteReunion(Long id) {
        reunionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reunión no encontrada con id: " + id));
        reunionRepository.deleteById(id);
    }
}