package com.proyecto.notas.service;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.proyecto.notas.dto.NotasDto;
import com.proyecto.notas.model.Notas;
import com.proyecto.notas.repository.NotasRepository;

import static org.assertj.core.api.Assertions.assertThat;
class NotasServiceTest {
    @Test
    void testgetAllNotasDTO() {
        NotasRepository notasRepository = Mockito.mock(NotasRepository.class);
        NotasService notasService = new NotasService(notasRepository);
        Notas nota = new Notas(
                null,"Juan Pérez", // Pasamos null porque el ID se genera automáticamente al persistir
                "Matemáticas",
                6.5
            );
    
        Mockito.when(notasRepository.findAll()).thenReturn(java.util.Arrays.asList(nota));
        List<NotasDto> result = notasService.listarTodas();
        assertThat(result).hasSize(1); 
        assertThat(result.get(0).getEstudiante()).isEqualTo("Juan Pérez");
    }

}
