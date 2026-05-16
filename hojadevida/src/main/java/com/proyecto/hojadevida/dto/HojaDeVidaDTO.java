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

    @Min(value = 8, message = "El RUT debe tener mínimo 8 dígitos y no debe tener guión")
    @Max(value = 9, message = "El RUT debe tener máximo 9 dígitos y no debe tener guión")
    private int rutAlumno;

    @NotBlank(message = "La dirección no puede estar vacía")
    private String direccion;

    @Min(value = 9, message = "El número de teléfono debe tener mínimo 9 dígitos")
    @Max(value = 9, message = "El número de teléfono debe tener máximo 9 dígitos")
    private int telefonoApoderado;

    @NotBlank(message = "El nombre del apoderado no puede estar vacío")
    private String nombreApoderado;

    @NotBlank(message = "El curso no puede estar vacío")
    private String curso;
}