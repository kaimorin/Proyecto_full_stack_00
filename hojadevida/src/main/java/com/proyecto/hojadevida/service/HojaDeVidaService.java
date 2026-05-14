package com.proyecto.hojadevida.service;

import org.springframework.stereotype.Service;
import com.proyecto.hojadevida.dto.HojaDeVidaDTO;
import com.proyecto.hojadevida.model.HojaDeVida;
import com.proyecto.hojadevida.repository.HojaDeVidaRepository;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HojaDeVidaService {

    private final HojaDeVidaRepository hojaDeVidaRepository;

    public List<HojaDeVidaDTO> getAllHojaDeVidasDTO() {
        return hojaDeVidaRepository.findAll()
                .stream()
                .map(h -> new HojaDeVidaDTO(
                        h.getNombreCompleto(),
                        h.getFechaNacimiento(),
                        h.getRutAlumno(),
                        h.getDireccion(),
                        h.getTelefonoApoderado(),
                        h.getNombreApoderado(),
                        h.getCurso()
                ))
                .toList();
    }

    public HojaDeVidaDTO getHojaDeVidaById(Long id) {
        HojaDeVida h = hojaDeVidaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hoja de vida no encontrada con id: " + id));
        return new HojaDeVidaDTO(h.getNombreCompleto(), h.getFechaNacimiento(), h.getRutAlumno(),
                h.getDireccion(), h.getTelefonoApoderado(), h.getNombreApoderado(), h.getCurso());
    }

    public HojaDeVidaDTO createHojaDeVida(HojaDeVidaDTO dto) {
        HojaDeVida h = new HojaDeVida(null, dto.getNombreCompleto(), dto.getFechaNacimiento(),
                dto.getRutAlumno(), dto.getDireccion(), dto.getTelefonoApoderado(),
                dto.getNombreApoderado(), dto.getCurso());
        HojaDeVida saved = hojaDeVidaRepository.save(h);
        return new HojaDeVidaDTO(saved.getNombreCompleto(), saved.getFechaNacimiento(), saved.getRutAlumno(),
                saved.getDireccion(), saved.getTelefonoApoderado(), saved.getNombreApoderado(), saved.getCurso());
    }

    public HojaDeVidaDTO updateHojaDeVida(Long id, HojaDeVidaDTO dto) {
        HojaDeVida h = hojaDeVidaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se pudo encontrar la hoja de vida del estudiante con id: " + id));
        h.setNombreCompleto(dto.getNombreCompleto());
        h.setFechaNacimiento(dto.getFechaNacimiento());
        h.setRutAlumno(dto.getRutAlumno());
        h.setDireccion(dto.getDireccion());
        h.setTelefonoApoderado(dto.getTelefonoApoderado());
        h.setNombreApoderado(dto.getNombreApoderado());
        h.setCurso(dto.getCurso());
        HojaDeVida updated = hojaDeVidaRepository.save(h);
        return new HojaDeVidaDTO(updated.getNombreCompleto(), updated.getFechaNacimiento(), updated.getRutAlumno(),
                updated.getDireccion(), updated.getTelefonoApoderado(), updated.getNombreApoderado(), updated.getCurso());
    }

    public void deleteHojaDeVida(Long id) {
        hojaDeVidaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se pudo encontrar la hoja de vida del estudiante con id: " + id));
        hojaDeVidaRepository.deleteById(id);
    }
}