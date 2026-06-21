package com.proyecto.reuniones.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.proyecto.reuniones.dto.ReunionDTO;
import com.proyecto.reuniones.model.Reunion;
import com.proyecto.reuniones.repository.ReunionRepository;

import static org.assertj.core.api.Assertions.assertThat;
class ReunionesServiceTest {
    @Test
    void testgetAllReunionesDTO() {
        ReunionRepository reunionesRepository = Mockito.mock(ReunionRepository.class);
        ReunionService reunionesService = new ReunionService(reunionesRepository);
        Reunion reuniones= new Reunion(
                null,
                java.sql.Date.valueOf("2023-10-30"),
                "hora", 
                "Discusión sobre la planificación del próximo trimestre.",
                "Reunión de Coordinación",
                "Sala de Conferencias A"
            );
    
        Mockito.when(reunionesRepository.findAll()).thenReturn(java.util.Arrays.asList(reuniones));
        List<ReunionDTO> result = reunionesService.getAllReunionesDTO();
        assertThat(result).hasSize(1); 
        assertThat(result.get(0).getMotivo()).isEqualTo("Reunión de Coordinación");
    }

}
