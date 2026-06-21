package com.proyecto.reuniones.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.proyecto.reuniones.service.AuthService;
import com.proyecto.reuniones.service.ReunionService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
class ReunionesControllerStandAloneTest {
    private MockMvc mockMvc;
    private ReunionService reunionService;
    private AuthService authService;
    @BeforeEach
    void setup() { 
     reunionService = org.mockito.Mockito.mock(ReunionService.class); // crea un mock de ReunionService para simular su comportamiento durante las pruebas sin necesidad de una implementación real
     authService = org.mockito.Mockito.mock(AuthService.class); // crea un mock de AuthService para simular la validación de tokens durante las pruebas sin necesidad de una implementación real

     ReunionController reunionesController = new ReunionController(reunionService, authService); // crea una instancia de ReunionesController pasando los mocks de ReunionService y AuthService como dependencias
     mockMvc = MockMvcBuilders.standaloneSetup(reunionesController).build(); // configura MockMvc para usar la instancia de ReunionesController creada, lo que permite simular solicitudes HTTP a los endpoints del controlador durante las pruebas
    }
    
    @Test // prueba para verificar que el endpoint de listar autos devuelve un error 401 Unauthorized cuando se proporciona un token inválido
    void testUnauthorizedAccess() throws Exception { // prueba para verificar que el endpoint de listar autos requiere autenticación y devuelve un error 401 Unauthorized cuando se proporciona un token inválido
     mockMvc.perform(get("/api/v1/matriculas/list") // simula una solicitud GET al endpoint "/api/v1/matriculas/list" con un encabezado de autorización que contiene un token inválido
        .header("Authorization", "Bearer invalid")) // simula una solicitud GET al endpoint "/api/v1/matriculas/list" con un encabezado de autorización que contiene un token inválido
        .andExpect(status().isUnauthorized()); // verifica que la respuesta de la solicitud tenga un estado HTTP 401 Unauthorized, lo que indica que el acceso fue denegado debido a un token inválido
    }


}
