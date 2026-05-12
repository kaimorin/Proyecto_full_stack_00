package com.proyecto.login.Service;
import com.proyecto.login.dto.*;
import com.proyecto.login.model.*;
import com.proyecto.login.repository.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor // Lombok genera constructor con los campos final
public class UserService {

    private final RepositoryUser repositoryUser;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Registrar usuario con contraseña encriptada
    public User registerUser(String username, String rawPassword) {
        // Verificar si el usuario ya existe
        if (repositoryUser.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("El usuario '" + username + "' ya existe");
        }

        String encodedPassword = passwordEncoder.encode(rawPassword);
        User user = new User(null, username, encodedPassword);
        return repositoryUser.save(user);
    }

    // Validar login
    public boolean login(String username, String rawPassword) {
        Optional<User> userOpt = repositoryUser.findByUsername(username);
        return userOpt.isPresent() && passwordEncoder.matches(rawPassword, userOpt.get().getPassword());
    }

    // Listar todos los usuarios
    public List<UserDTO> getAllUsersDTO() {
        return repositoryUser.findAll()
                .stream()
                .map(user -> new UserDTO(user.getId(), user.getUsername()))
                .toList();
    }
}