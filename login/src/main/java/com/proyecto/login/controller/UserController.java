package com.proyecto.login.controller;
import com.proyecto.login.service.UserService;
import com.proyecto.login.dto.ApiResponse;
import com.proyecto.login.dto.RoleDTO;
import com.proyecto.login.dto.UserCreateDTO;
import com.proyecto.login.dto.UserCredentialsDTO;
import com.proyecto.login.dto.UserDTO;
import com.proyecto.login.model.User;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    
    @PostMapping("/register")
    @Operation(summary = "Registrar un nuevo usuario", description = "Crea un nuevo usuario con un rol específico")
    public ResponseEntity<ApiResponse<UserDTO>> register(@Valid @RequestBody UserCreateDTO dto) {
        try {
            User newUser = userService.registerUser(dto.getUsername(), dto.getPassword(), dto.getRoleId());
            UserDTO userDTO = new UserDTO(
                newUser.getId(),
                newUser.getUsername(),
                new RoleDTO(newUser.getRol().getIdrol(), newUser.getRol().getNombre())
            );

            ApiResponse<UserDTO> response = new ApiResponse<>(200, "Usuario registrado correctamente", userDTO);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error al registrar usuario: {}", e.getMessage());
            ApiResponse<UserDTO> response = new ApiResponse<>(400, "Error al registrar usuario: " + e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        }
    }

   
    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Valida las credenciales del usuario y permite el acceso")
    public ResponseEntity<ApiResponse<String>> login(@Valid @RequestBody UserCredentialsDTO dto) {
        String token = userService.login(dto.getUsername(), dto.getPassword());

        if (token != null) {
            ApiResponse<String> response =
                    new ApiResponse<>(200, "Login exitoso", token);
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<String> response =
                    new ApiResponse<>(401, "Credenciales inválidas", null);
            return ResponseEntity.status(401).body(response);
        }
    }

    @GetMapping("/list")
    @Operation(summary = "Listar usuarios", description = "Obtiene una lista de todos los usuarios registrados")
    public ResponseEntity<ApiResponse<List<UserDTO>>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsersDTO();
        ApiResponse<List<UserDTO>> response =
                new ApiResponse<>(200, "Listado de usuarios", users);
        return ResponseEntity.ok(response);
    }
}