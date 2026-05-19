CREATE TABLE notificacion (
    id BIGINT NOT NULL AUTO_INCREMENT,
    mensaje VARCHAR(500) NOT NULL,
    origen VARCHAR(100) NOT NULL,
    fecha_creacion DATETIME,
    leida BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (id)
);