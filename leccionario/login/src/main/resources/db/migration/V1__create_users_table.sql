CREATE TABLE roles (
    idrol BIGINT NOT NULL AUTO_INCREMENT,
    nombreRol VARCHAR(100) NOT NULL UNIQUE,
    PRIMARY KEY (idrol)
);

CREATE TABLE users (
    id BIGINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
   idrol_user BIGINT,
    FOREIGN KEY (idrol_user) REFERENCES roles(idrol),
    PRIMARY KEY (id)
);

