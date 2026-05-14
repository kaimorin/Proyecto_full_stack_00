package com.proyecto.reuniones.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReunionDTO {

    @NotNull(message = "La fecha no puede ser nula")
    private Date fecha;

    @NotBlank(message = "La hora no puede estar vacía")
    private String hora;

    @NotBlank(message = "El motivo no puede estar vacío")
    private String motivo;

    @NotBlank(message = "El curso no puede estar vacío")
    private String curso;

    @NotBlank(message = "El nombre del encargado no puede estar vacío")
    private String nombreEncargado;
}