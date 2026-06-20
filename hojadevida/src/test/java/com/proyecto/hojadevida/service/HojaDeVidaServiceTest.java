package com.proyecto.hojadevida.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.proyecto.hojadevida.dto.HojaDeVidaDTO;
import com.proyecto.hojadevida.model.HojaDeVida;
import com.proyecto.hojadevida.repository.HojaDeVidaRepository;
import static org.assertj.core.api.Assertions.assertThat;
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
         HojaDeVida hojaDeVidatest = new HojaDeVida(2L, "Ana Gómez", java.sql.Date.valueOf("2001-02-02"), 87654321, "Avenida Siempre Viva 456", 87654321, "Carlos Gómez", "5to Básico");
        Mockito.when(hojaDeVidaRepository.findAll()).thenReturn(java.util.Arrays.asList(hojaDeVidatest));
        List<HojaDeVidaDTO> result = hojaDeVidaService.getAllHojaDeVidasDTO();
        assertThat(result).hasSize(1); 
        assertThat(result.get(0).getNombreCompleto()).isEqualTo("Juan Pérez");
    }

}
