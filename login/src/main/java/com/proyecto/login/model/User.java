package com.proyecto.login.model;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "idrol_user") 
    private long rol_id_user;



















    /* OTM
    //@Builder.Default
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "users_roles", 
        joinColumns = @JoinColumn(name = "user_id"), // Apunta al 'id' de esta clase
        inverseJoinColumns = @JoinColumn(name = "rol_id") // Apunta al 'idrol' de Rol
    )
    private Set<Rol> roles = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id != null && id.equals(user.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
    public void addRol(Rol rol) {
        this.roles.add(rol);
        rol.getUsers().add(this);
    }

    public void removeRol(Rol rol) {
        this.roles.remove(rol);
        rol.getUsers().remove(this);
    }
        */
}