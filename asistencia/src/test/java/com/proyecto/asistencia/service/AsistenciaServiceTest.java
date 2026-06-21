package com.proyecto.asistencia.service;


import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.proyecto.asistencia.dto.AsistenciaDTO;
import com.proyecto.asistencia.model.Asistencia;
import com.proyecto.asistencia.repository.AsistenciaRepository;
import static org.assertj.core.api.Assertions.assertThat;

class AsistenciaServiceTest {

    @Test
    void testgetAllAsistenciasDto() {
        AsistenciaRepository asistenciaRepository = Mockito.mock(AsistenciaRepository.class);
        AsistenciaService asistenciaService = new AsistenciaService(asistenciaRepository);
        Asistencia asistenciatest = new Asistencia(2L, java.sql.Date.valueOf("2023-10-25"), "Ausente", 87654321);
        Mockito.when(asistenciaRepository.findAll()).thenReturn(java.util.Arrays.asList(asistenciatest));
        List<AsistenciaDTO> result = asistenciaService.getAllAsistenciasDTO();
        assertThat(result).hasSize(1); 
        assertThat(result.get(0).getEstadoAsistencia()).isEqualTo("Ausente");
    }
   
}
