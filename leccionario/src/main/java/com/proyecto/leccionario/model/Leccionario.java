package com.proyecto.leccionario.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "leccionarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Leccionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "La asignatura no puede estar vacía")
    private String asignatura;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    @Size(max = 2000, message = "El contenido no puede exceder 2000 caracteres")
    private String contenido;

    @Column(name = "id_profesor")
    private Long idProfesor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_curso")
    private Curso curso;
}
