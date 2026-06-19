package com.proyecto.notas.dto;

import jakarta.validation.constraints.*;
import lombok.*;

/**
 * DTO para recibir los datos de una nota en las solicitudes HTTP.
 *
 * El DTO solo contiene los campos necesarios para crear o actualizar una nota,
 * y no incluye el identificador generado por la base de datos.
 */
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotasDto {

    private Long id;

    @NotBlank(message = "El nombre del estudiante es obligatorio")
    private String estudiante;

    @NotBlank(message = "La asignatura es obligatoria")
    private String asignatura;

    @NotNull(message = "La nota es obligatoria")
    @DecimalMin(value = "1.0", message = "La nota mínima es 1.0")
    @DecimalMax(value = "7.0", message = "La nota máxima es 7.0")
    private Double valor;
}