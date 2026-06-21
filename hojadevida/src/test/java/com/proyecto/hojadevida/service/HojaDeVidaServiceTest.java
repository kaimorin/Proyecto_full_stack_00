package com.proyecto.hojadevida.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.assertj.core.api.Assertions.assertThat;
import com.proyecto.hojadevida.dto.HojaDeVidaDTO;
import com.proyecto.hojadevida.model.HojaDeVida;
import com.proyecto.hojadevida.repository.HojaDeVidaRepository;

class HojaDeVidaServiceTest {

    @Test
    void testgetAllHojasDeVidaDTO() {
        HojaDeVidaRepository hojaDeVidaRepository = Mockito.mock(HojaDeVidaRepository.class);
        HojaDeVidaService hojaDeVidaService = new HojaDeVidaService(hojaDeVidaRepository);
       HojaDeVida hojaDeVida = new HojaDeVida(
                1L,
                "Juan Pérez",
                java.sql.Date.valueOf("2000-01-01"),
                12345678,
                "Calle Falsa 123",
                98765432,
                "María Pérez",
                "4to Básico"
        );
        Mockito.when(hojaDeVidaRepository.findAll()).thenReturn(java.util.Arrays.asList(hojaDeVida));
        List<HojaDeVidaDTO> result = hojaDeVidaService.getAllHojaDeVidasDTO();
        assertThat(result).hasSize(1); 
        assertThat(result.get(0).getNombreCompleto()).isEqualTo("Juan Pérez");
    }

}
