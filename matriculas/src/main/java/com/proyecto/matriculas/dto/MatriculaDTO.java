package com.proyecto.matriculas.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatriculaDTO {

    @NotBlank(message = "El RUN del estudiante no puede estar vacío")
    @Size(min = 8, max = 9, message = "El RUN debe tener entre 8 y 9 dígitos")
    private String runEstudiante;

    @NotBlank(message = "El nombre del estudiante no puede estar vacío")
    private String nombreEstudiante;

    @NotBlank(message = "El curso no puede estar en blanco")
    private String curso;

    @NotBlank(message = "El estado no puede estar vacío")
    private String estado;
}