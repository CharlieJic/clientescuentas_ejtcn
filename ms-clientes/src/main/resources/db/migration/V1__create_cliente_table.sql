CREATE TABLE cliente (
                         cliente_id      BIGSERIAL PRIMARY KEY,
                         identificacion  VARCHAR(20) NOT NULL UNIQUE,
                         nombre           VARCHAR(100) NOT NULL,
                         genero           CHAR(1) NOT NULL,
                         edad             INTEGER NOT NULL CHECK (edad >= 0),
                         direccion        VARCHAR(200),
                         telefono         VARCHAR(20),
                         contrasena       VARCHAR(255) NOT NULL,
                         estado           BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE INDEX idx_cliente_identificacion
    ON cliente (identificacion);