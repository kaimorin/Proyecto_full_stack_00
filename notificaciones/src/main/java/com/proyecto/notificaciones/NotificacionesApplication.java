package com.proyecto.notificaciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto de entrada del microservicio de notificaciones.
 *
 * Esta clase arranca el contexto de Spring Boot y registra los beans
 * necesarios para atender las solicitudes HTTP.
 */
@SpringBootApplication
public class NotificacionesApplication {

	/**
	 * Método principal que inicia la aplicación.
	 *
	 * @param args Argumentos de línea de comandos.
	 */
	public static void main(String[] args) {
		SpringApplication.run(NotificacionesApplication.class, args);
	}

}
