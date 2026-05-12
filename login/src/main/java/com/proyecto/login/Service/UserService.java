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
@RequiredArgsConstructor
public class UserService {

    private final RepositoryUser repositoryUser;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    
    public User registerUser(String username, String rawPassword) {
       
        if (repositoryUser.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("El usuario con este user: " + username + " ya existe actualmente");
        }

        String encodedPassword = passwordEncoder.encode(rawPassword);
        User user = new User(null, username, encodedPassword,null);
        return repositoryUser.save(user);
    }

   
    public boolean login(String username, String rawPassword) {
        Optional<User> userOpt = repositoryUser.findByUsername(username);
        return userOpt.isPresent() && passwordEncoder.matches(rawPassword, userOpt.get().getPassword());
    }

    
    public List<UserDTO> getAllUsersDTO() {
        return repositoryUser.findAll()
                .stream()
                .map(user -> new UserDTO(user.getId(), user.getUsername()))
                .toList();
    }
}