package com.proyecto.login.Service;
import com.proyecto.login.dto.RoleDTO;
import com.proyecto.login.dto.UserDTO;
import com.proyecto.login.model.Rol;
import com.proyecto.login.model.User;
import com.proyecto.login.repository.RepositoryUser;
import com.proyecto.login.repository.RolRepository;
import com.proyecto.login.security.JwtUtil;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final RepositoryUser repositoryUser;
    private final RolRepository rolRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final JwtUtil jwtUtil;

    public User registerUser(String username, String rawPassword, Long roleId) {
        if (repositoryUser.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("El usuario con este user: " + username + " ya existe actualmente");
        }

        String encodedPassword = passwordEncoder.encode(rawPassword);

        Rol databaseRol = rolRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("El rol especificado no existe"));

        User user = new User(null, username, encodedPassword, databaseRol);
        return repositoryUser.save(user);
    }

    public String login(String username, String rawPassword) {
        Optional<User> userOpt = repositoryUser.findByUsername(username);
        if (userOpt.isPresent() && passwordEncoder.matches(rawPassword, userOpt.get().getPassword())) {
            return jwtUtil.generateToken(username);
        }
        return null;
    }
    public boolean validateToken(String token) {
        return jwtUtil.validateToken(token);
    }

    public List<UserDTO> getAllUsersDTO() {
        return repositoryUser.findAll()
                .stream()
                .map(user -> new UserDTO(
                        user.getId(),
                        user.getUsername(),
                        new RoleDTO(user.getRol().getIdrol(), user.getRol().getNombre())
                ))
                .toList();
    }
}