package com.proyecto.notas.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de OpenAPI / Swagger para documentar la API del microservicio de notas.
 */
@Configuration
public class OpenAPIConfig {

    /**
     * Crea el objeto principal de OpenAPI con la información de la API.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Notas")
                        .version("1.0.0")
                        .description("API REST para la gestión de notas de estudiantes")
                        .contact(new Contact()
                                .name("Proyecto Full Stack")
                                .url("http://localhost:9000")
                        )
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")
                        )
                );
    }
}
