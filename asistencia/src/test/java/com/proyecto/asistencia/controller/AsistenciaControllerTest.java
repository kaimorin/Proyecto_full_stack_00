package com.proyecto.asistencia.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.proyecto.asistencia.service.AsistenciaService;
import com.proyecto.asistencia.service.AuthService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class AsistenciaControllerTest {
    private MockMvc mockMvc; 
    private AsistenciaService asistenciaService;
    private AuthService authService;

    @BeforeEach
    void setUp() {
        asistenciaService = org.mockito.Mockito.mock(AsistenciaService.class);
        authService = org.mockito.Mockito.mock(AuthService.class);
        AsistenciaController asistenciaController = new AsistenciaController(asistenciaService,authService);
        mockMvc = MockMvcBuilders.standaloneSetup(asistenciaController).build();
    }
    @Test 
    void testUnauthorizedAccess() throws Exception { 
        mockMvc.perform(get("/api/v1/asistencia/list") 
                .header("Authorization", "Bearer invalid")) 
                .andExpect(status().isUnauthorized()); 
    }

}
