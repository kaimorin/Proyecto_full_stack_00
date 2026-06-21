package com.proyecto.matriculas.controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.proyecto.matriculas.service.AuthService;
import com.proyecto.matriculas.service.MatriculaService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
class MatriculasControllerStandAloneTest {

    private MockMvc mockMvc;
    private MatriculaService matriculasService;
    private AuthService authService;

    @BeforeEach
    void setup() {
        matriculasService = org.mockito.Mockito.mock(MatriculaService.class); // crea un mock de MatriculaService para simular su comportamiento durante las pruebas sin necesidad de una implementación real
        authService = org.mockito.Mockito.mock(AuthService.class); // crea un mock de AuthService para simular la validación de tokens durante las pruebas sin necesidad de una implementación real

        MatriculaController matriculasController = new MatriculaController(matriculasService, authService); // crea una instancia de MatriculaController pasando los mocks de MatriculaService y AuthService como dependencias
        mockMvc = MockMvcBuilders.standaloneSetup(matriculasController).build(); // configura MockMvc para usar la instancia de MatriculaController creada, lo que permite simular solicitudes HTTP a los endpoints del controlador durante las pruebas
    }

    @Test // prueba para verificar que el endpoint de listar autos devuelve un error 401 Unauthorized cuando se proporciona un token inválido
    void testUnauthorizedAccess() throws Exception { // prueba para verificar que el endpoint de listar autos requiere autenticación y devuelve un error 401 Unauthorized cuando se proporciona un token inválido
        mockMvc.perform(get("/api/v1/matriculas/list") // simula una solicitud GET al endpoint "/api/v1/matriculas/list" con un encabezado de autorización que contiene un token inválido
                .header("Authorization", "Bearer invalid")) // simula una solicitud GET al endpoint "/api/v1/matriculas/list" con un encabezado de autorización que contiene un token inválido
                .andExpect(status().isUnauthorized()); // verifica que la respuesta de la solicitud tenga un estado HTTP 401 Unauthorized, lo que indica que el acceso fue denegado debido a un token inválido
    }

}
