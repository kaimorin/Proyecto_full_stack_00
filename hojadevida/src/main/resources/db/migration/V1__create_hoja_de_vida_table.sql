CREATE TABLE hoja_de_vida (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_completo VARCHAR(255) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    rut_alumno INT(9) NOT NULL UNIQUE,
    direccion VARCHAR(255) NOT NULL,
    telefono_apoderado VARCHAR(9) NOT NULL,
    nombre_apoderado VARCHAR(255) NOT NULL,
    curso VARCHAR(50) NOT NULL
);