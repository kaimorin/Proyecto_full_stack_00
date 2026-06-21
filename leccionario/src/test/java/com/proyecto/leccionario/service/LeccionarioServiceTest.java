package com.proyecto.leccionario.service;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.proyecto.leccionario.dto.LeccionarioDto;
import com.proyecto.leccionario.model.Leccionario;
import com.proyecto.leccionario.repository.RepositoryLeccionario;

import static org.assertj.core.api.Assertions.assertThat;
class LeccionarioServiceTest {

    @Test
    void testgetAllLeccionarioDTO() {
       RepositoryLeccionario leccionarioRepository = Mockito.mock(RepositoryLeccionario.class);
        LeccionarioService leccionarioService = new LeccionarioService(leccionarioRepository);
        Leccionario leccionarioConConstructor = new Leccionario(
                null, // Pasamos null porque el ID se genera automáticamente al persistir
                "Historia del Arte",
                LocalDate.of(2023, 10, 15), // Un LocalDate específico
                "Análisis del Renacimiento italiano, enfoque en Miguel Ángel.",
                202L
            );
    
        Mockito.when(leccionarioRepository.findAll()).thenReturn(java.util.Arrays.asList(leccionarioConConstructor));
        List<LeccionarioDto> result = leccionarioService.listarTodos();
        assertThat(result).hasSize(1); 
        assertThat(result.get(0).getContenido()).isEqualTo("Análisis del Renacimiento italiano, enfoque en Miguel Ángel.");
    }
}
