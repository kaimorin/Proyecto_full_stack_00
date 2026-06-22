package com.proyecto.notificaciones.service;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.time.LocalDateTime;

import com.proyecto.notificaciones.dto.NotificacionDto;
import com.proyecto.notificaciones.model.Notificacion;
import com.proyecto.notificaciones.repository.NotificacionRepository;

import static org.assertj.core.api.Assertions.assertThat;
class NotificacionesServiceTest {

    @Test
    void testgetAllNotificacionesDTO() {
        NotificacionRepository notificacionRepository = Mockito.mock(NotificacionRepository.class);
        NotificacionService notificacionesService = new NotificacionService(notificacionRepository);
        Notificacion notificacion = new Notificacion(
                1L,
                "Tienes una nueva notificación",
                "Sistema",
                LocalDateTime.now(),
                false
               
            );
    
        Mockito.when(notificacionRepository.findAll()).thenReturn(java.util.Arrays.asList(notificacion));
        List<NotificacionDto> result = notificacionesService.listarTodos();
        assertThat(result).hasSize(1); 
        assertThat(result.get(0).getId()).isEqualTo(1L);
    }

}
