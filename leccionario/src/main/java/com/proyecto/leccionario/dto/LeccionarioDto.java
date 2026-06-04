package com.proyecto.leccionario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeccionarioDto {
    private Long id;

    @NotBlank(message = "La asignatura no puede estar vacía")
    private String asignatura;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    @Size(max = 2000, message = "El contenido no puede exceder 2000 caracteres")
    private String contenido;

    private Long idProfesor;
}
