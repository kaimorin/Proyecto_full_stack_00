package com.proyecto.reuniones.model;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Date;

@Entity
@Table(name = "reuniones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reunion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date fecha;

    @Column(nullable = false)
    private String hora;

    @Column(nullable = false)
    private String motivo;

    @Column(nullable = false)
    private String curso;

    @Column(nullable = false)
    private String nombreEncargado;
}