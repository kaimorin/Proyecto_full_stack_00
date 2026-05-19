CREATE TABLE asistencia (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    estado_asistencia VARCHAR(10) NOT NULL,
    rut_alumno INT(9) NOT NULL
);