CREATE TABLE reuniones (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    hora VARCHAR(10) NOT NULL,
    motivo VARCHAR(255) NOT NULL,
    curso VARCHAR(50) NOT NULL,
    nombre_encargado VARCHAR(255) NOT NULL
);