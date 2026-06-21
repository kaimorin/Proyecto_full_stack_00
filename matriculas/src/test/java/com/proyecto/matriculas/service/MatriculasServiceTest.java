package com.proyecto.matriculas.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.proyecto.matriculas.dto.MatriculaDTO;
import com.proyecto.matriculas.model.Matricula;
import com.proyecto.matriculas.repository.MatriculaRepository;

import static org.assertj.core.api.Assertions.assertThat;
class MatriculasServiceTest {
    @Test
    void testgetAllMatriculasDTO() {
        MatriculaRepository matriculasRepository = Mockito.mock(MatriculaRepository.class);
        MatriculaService matriculasService = new MatriculaService(matriculasRepository);
        Matricula matriculasConConstructor = new Matricula(
                null, // Pasamos null porque el ID se genera automáticamente al persistir
                "12345678",
                "Juan Pérez",
                "Matemáticas",
                "Activo"
            );
    
        Mockito.when(matriculasRepository.findAll()).thenReturn(java.util.Arrays.asList(matriculasConConstructor));
        List<MatriculaDTO> result = matriculasService.getAllMatriculasDTO();
        assertThat(result).hasSize(1); 
        assertThat(result.get(0).getNombreEstudiante()).isEqualTo("Juan Pérez");
    }
}
