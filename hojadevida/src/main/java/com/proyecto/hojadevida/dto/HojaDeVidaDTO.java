package com.proyecto.hojadevida.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HojaDeVidaDTO {

    @NotBlank(message = "El nombre completo no puede estar vacío")
    private String nombreCompleto;

    @NotNull(message = "La fecha de nacimiento no puede ser nula")
    private Date fechaNacimiento;

    @NotNull(message = "El RUT del alumno no puede ser nulo")
    @Min(value = 1000000, message = "El RUT debe ser válido")
    @Max(value = 99999999, message = "El RUT debe ser válido")
    private Integer rutAlumno;

    @NotBlank(message = "La dirección no puede estar vacía")
    private String direccion;

    @NotBlank(message = "El teléfono del apoderado no puede estar vacío")
    private String telefonoApoderado;

    @NotBlank(message = "El nombre del apoderado no puede estar vacío")
    private String nombreApoderado;

    @NotBlank(message = "El curso no puede estar vacío")
    private String curso;
}