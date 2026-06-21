package com.proyecto.notificaciones.service;

import com.proyecto.notificaciones.dto.NotificacionDto;
import com.proyecto.notificaciones.model.Notificacion;
import com.proyecto.notificaciones.repository.NotificacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificacionService {

    private final NotificacionRepository repository;

    public List<NotificacionDto> listarTodos() {
        return repository.findAll().stream()
                .map(this::entityToDto)
                .toList();
    }

    public Optional<NotificacionDto> obtenerPorId(Long id) {
        return repository.findById(id).map(this::entityToDto);
    }

    public Optional<NotificacionDto> obtenerPorOrigen(String origen) {
        return repository.findByOrigen(origen).map(this::entityToDto);
    }

    public NotificacionDto crear(NotificacionDto notificacionDto) {
        Notificacion notificacion = dtoToEntity(notificacionDto);
        return entityToDto(repository.save(notificacion));
    }

    public NotificacionDto actualizar(Long id, NotificacionDto data) {
        return repository.findById(id).map(n -> {
            n.setMensaje(data.getMensaje());
            n.setOrigen(data.getOrigen());
            n.setLeida(data.isLeida());
            return entityToDto(repository.save(n));
        }).orElseThrow(() -> new RuntimeException("No se encontró la notificación"));
    }

    public void eliminar(Long id) {
        repository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró la notificación"));
        repository.deleteById(id);
    }

    private Notificacion dtoToEntity(NotificacionDto dto) {
        return Notificacion.builder()
                .id(dto.getId())
                .mensaje(dto.getMensaje())
                .origen(dto.getOrigen())
                .fechaCreacion(dto.getFechaCreacion())
                .leida(dto.isLeida())
                .build();
    }

    private NotificacionDto entityToDto(Notificacion entity) {
        return NotificacionDto.builder()
                .id(entity.getId())
                .mensaje(entity.getMensaje())
                .origen(entity.getOrigen())
                .fechaCreacion(entity.getFechaCreacion())
                .leida(entity.isLeida())
                .build();
    }
}