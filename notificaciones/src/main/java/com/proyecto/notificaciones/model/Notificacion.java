package com.proyecto.notificaciones.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad JPA que representa una notificación en la base de datos.
 */
@Entity
@Table(name = "notificaciones")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notificacion {

    /** Identificador único generado automáticamente. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Mensaje que se desea notificar. */
    @Column(nullable = false)
    private String mensaje;

    /** Origen de la notificación (p. ej. servicio o módulo emisor). */
    @Column(nullable = false)
    private String origen; 

    /** Fecha y hora de creación de la notificación. */
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    /** Indica si la notificación ya fue leída. */
    @Column(nullable = false)
    private boolean leida = false;

    /**
     * Inicializa la fecha de creación justo antes de persistir la entidad.
     */
    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
    }
}