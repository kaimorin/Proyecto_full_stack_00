package com.proyecto.notificaciones.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de OpenAPI para documentar el microservicio de notificaciones.
 */
@Configuration
public class OpenAPIConfig {

    /**
     * Crea la metadata de la API que se muestra en Swagger UI.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Notificaciones")
                        .version("1.0.0")
                        .description("API REST para la gestión de notificaciones")
                        .contact(new Contact()
                                .name("Proyecto Full Stack")
                                .url("http://localhost:9001")
                        )
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")
                        )
                );
    }
}
