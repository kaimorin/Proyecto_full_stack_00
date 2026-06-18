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

    /**
     * Devuelve todas las notas almacenadas en la base de datos.
     */
    public List<Notas> listarTodas() {
        return notasRepository.findAll();
    }

    /**
     * Busca una nota por su identificador.
     *
     * @param id El ID de la nota buscada.
     * @return Optional con la nota si existe, o vacío si no existe.
     */
    public Optional<Notas> buscarPorId(Long id) {
        return notasRepository.findById(id);
    }

    /**
     * Guarda o actualiza una entidad de nota directamente.
     *
     * @param nota Entidad de nota a persistir.
     * @return La nota persistida con el ID generado.
     */
    public Notas guardar(Notas nota) {
        return notasRepository.save(nota);
    }

    /**
     * Crea una nueva nota a partir de un DTO y la persiste en la base de datos.
     *
     * @param dto Datos de la nota recibidos desde el controlador.
     * @return La nota creada y persistida.
     */
    public Notas crearNota(NotasDto dto) {
        Notas nota = new Notas();
        nota.setEstudiante(dto.getEstudiante());
        nota.setAsignatura(dto.getAsignatura());
        nota.setValor(dto.getValor());
        
        // Aquí es donde en el futuro llamaremos al microservicio de notificaciones justo antes del return
        return notasRepository.save(nota);
    }

    /**
     * Elimina una nota por su identificador.
     *
     * @param id El ID de la nota a eliminar.
     */
    public void eliminar(Long id) {
        notasRepository.deleteById(id);
    }
}