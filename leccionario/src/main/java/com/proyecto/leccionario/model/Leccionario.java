package com.proyecto.leccionario.model;


import jakarta.persistence.*;
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
    private String asignatura;
    private LocalDate fecha;
    private String contenido;

    @Column(name = "id_profesor")
    private Long idProfesor;

    @Column(name = "id_curso")
    private Long idCurso;
}
