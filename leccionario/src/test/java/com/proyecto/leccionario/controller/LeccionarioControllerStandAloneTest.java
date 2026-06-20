package com.proyecto.leccionario.controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.proyecto.leccionario.service.AuthService;
import com.proyecto.leccionario.service.LeccionarioService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
class LeccionarioControllerStandAloneTest {
    private MockMvc mockMvc;
    private LeccionarioService leccionarioService;
    private AuthService authService;

    @BeforeEach
    void setup() {
        leccionarioService = org.mockito.Mockito.mock(LeccionarioService.class); // crea un mock de LeccionarioService para simular su comportamiento durante las pruebas sin necesidad de una implementación real
        authService = org.mockito.Mockito.mock(AuthService.class); // crea un mock de AuthService para simular la validación de tokens durante las pruebas sin necesidad de una implementación real

        LeccionarioController leccionarioController = new LeccionarioController(leccionarioService, authService); // crea una instancia de LeccionarioController pasando los mocks de LeccionarioService y AuthService como dependencias
        mockMvc = MockMvcBuilders.standaloneSetup(leccionarioController).build(); // configura MockMvc para usar la instancia de LeccionarioController creada, lo que permite simular solicitudes HTTP a los endpoints del controlador durante las pruebas
    }

    @Test // prueba para verificar que el endpoint de listar autos devuelve un error 401 Unauthorized cuando se proporciona un token inválido
    void testUnauthorizedAccess() throws Exception { // prueba para verificar que el endpoint de listar autos requiere autenticación y devuelve un error 401 Unauthorized cuando se proporciona un token inválido
        mockMvc.perform(get("/api/v1/leccionarios/list") // simula una solicitud GET al endpoint "/api/v1/leccionarios/list" con un encabezado de autorización que contiene un token inválido
                .header("Authorization", "Bearer invalid")) // simula una solicitud GET al endpoint "/api/v1/leccionarios/list" con un encabezado de autorización que contiene un token inválido
                .andExpect(status().isUnauthorized()); // verifica que la respuesta de la solicitud tenga un estado HTTP 401 Unauthorized, lo que indica que el acceso fue denegado debido a un token inválido
    }

}
