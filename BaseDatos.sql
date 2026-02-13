-- BaseDatos.sql
-- Crea bases + esquema mínimo para ms-clientes y ms-cuentas

-- 1) Bases
CREATE DATABASE clientes_db;
CREATE DATABASE cuentas_db;

-- 2) ms-clientes
\connect clientes_db;

CREATE TABLE IF NOT EXISTS cliente (
    cliente_id      BIGSERIAL PRIMARY KEY,
    identificacion  VARCHAR(20) NOT NULL UNIQUE,
    nombre          VARCHAR(100) NOT NULL,
    genero          CHAR(1) NOT NULL,
    edad            INTEGER NOT NULL CHECK (edad >= 0),
    direccion       VARCHAR(200),
    telefono        VARCHAR(20),
    contrasena      VARCHAR(255) NOT NULL,
    estado          BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE INDEX IF NOT EXISTS idx_cliente_identificacion
    ON cliente (identificacion);

-- 3) ms-cuentas
\connect cuentas_db;

CREATE TABLE IF NOT EXISTS cliente_view (
    cliente_id      BIGINT PRIMARY KEY,
    nombre          VARCHAR(80) NOT NULL,
    genero          CHAR(1) NOT NULL,
    edad            INTEGER NOT NULL,
    identificacion  VARCHAR(20) NOT NULL UNIQUE,
    direccion       VARCHAR(120) NOT NULL,
    telefono        VARCHAR(15) NOT NULL,
    estado          BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS cuenta (
    cuenta_id       BIGSERIAL PRIMARY KEY,
    numero_cuenta   VARCHAR(20) NOT NULL UNIQUE,
    tipo_cuenta     VARCHAR(30) NOT NULL,
    saldo_inicial   NUMERIC(18,2) NOT NULL,
    estado          BOOLEAN NOT NULL,
    cliente_id      BIGINT NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_cuenta_numero ON cuenta (numero_cuenta);
CREATE INDEX IF NOT EXISTS idx_cuenta_cliente ON cuenta (cliente_id);

CREATE TABLE IF NOT EXISTS movimiento (
    movimiento_id   BIGSERIAL PRIMARY KEY,
    cuenta_id       BIGINT NOT NULL REFERENCES cuenta(cuenta_id),
    fecha           TIMESTAMP NOT NULL,
    tipo_movimiento VARCHAR(15) NOT NULL,
    valor           NUMERIC(18,2) NOT NULL,
    saldo           NUMERIC(18,2) NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_mov_cuenta_fecha ON movimiento (cuenta_id, fecha);
CREATE INDEX IF NOT EXISTS idx_mov_fecha ON movimiento (fecha);
