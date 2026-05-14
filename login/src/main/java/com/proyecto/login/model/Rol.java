package com.proyecto.login.model;


import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import lombok.*;

@Entity
@Table (name="roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rol {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY )
    private Long idrol;
    @NotBlank
    private String nombreRol;

 
    
    @OneToMany(mappedBy = "rol", cascade = CascadeType.ALL)
    @Builder.Default
    private Set<User> users = new HashSet<>();
      
    
    
/* MTO
    @Builder.Default
    @ManyToMany(mappedBy = "roles") // Debe ser el nombre exacto del atributo en la clase User
    private Set<User> users = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rol)) return false;
        Rol rol = (Rol) o;
        return idrol != null && idrol.equals(rol.getIdrol());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    */
}



