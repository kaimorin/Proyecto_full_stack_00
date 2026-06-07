package com.proyecto.notas.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotasDto {

    @NotBlank(message = "El nombre del estudiante es obligatorio")
    private String estudiante;

    @NotBlank(message = "La asignatura es obligatoria")
    private String asignatura;

    @NotNull(message = "La nota es obligatoria")
    @DecimalMin(value = "1.0", message = "La nota mínima es 1.0")
    @DecimalMax(value = "7.0", message = "La nota máxima es 7.0")
    private Double valor;
}