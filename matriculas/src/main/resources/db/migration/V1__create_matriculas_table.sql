CREATE TABLE matriculas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    run_estudiante VARCHAR(9) NOT NULL UNIQUE,
    nombre_estudiante VARCHAR(255) NOT NULL,
    curso VARCHAR(50) NOT NULL,
    estado VARCHAR(50) NOT NULL
);