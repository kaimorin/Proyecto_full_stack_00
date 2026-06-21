package com.proyecto.notificaciones.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionDto {

    private Long id;

    @NotBlank(message = "El mensaje es obligatorio")
    private String mensaje;

    @NotBlank(message = "El origen es obligatorio")
    private String origen;

    private LocalDateTime fechaCreacion;

    private boolean leida;
}
