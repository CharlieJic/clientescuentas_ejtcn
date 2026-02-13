# Ejercicio Técnico – Backend Java (Microservicios)

## 1. Descripción general

Este repositorio contiene la solución al ejercicio técnico backend, implementada bajo una arquitectura de microservicios, utilizando Java 21, Spring Boot, PostgreSQL, RabbitMQ y Docker.

La solución está compuesta por dos microservicios independientes:

- **ms-clientes**: gestión de clientes.
- **ms-cuentas**: gestión de cuentas y movimientos, incluyendo la sincronización de datos de clientes mediante eventos.

La comunicación entre microservicios se realiza de forma asíncrona, desacoplada, a través de RabbitMQ.

## 2. Arquitectura

### 2.1 Microservicios

| Servicio     | Puerto | Base de datos |
|--------------|--------|---------------|
| ms-clientes  | 8081   | clientes_db  |
| ms-cuentas   | 8082   | cuentas_db   |

Cada microservicio mantiene su propia base de datos y no accede directamente a la base de datos del otro servicio.

### 2.2 Comunicación

- ms-clientes publica eventos cuando se crea o actualiza un cliente.
- ms-cuentas consume estos eventos y mantiene una tabla local `cliente_view`.

## 3. Tecnologías

- Java 21
- Spring Boot
- Spring Data JPA
- Spring Validation
- PostgreSQL
- RabbitMQ
- Docker y Docker Compose
- Maven
- JUnit 5 / Mockito

## 4. Estructura del repositorio

```
.
├── ms-clientes/
│   ├── src/
│   ├── Dockerfile
│   └── pom.xml
├── ms-cuentas/
│   ├── src/
│   ├── Dockerfile
│   └── pom.xml
├── BaseDatos.sql
├── docker-compose.yml
└── README.md
```

## 5. Modelo de datos

El archivo `BaseDatos.sql` contiene la creación de las bases de datos `clientes_db` y `cuentas_db`, así como las tablas `cliente`, `cliente_view`, `cuenta` y `movimiento`, incluyendo índices y restricciones.

## 6. Endpoints principales

### ms-clientes

```
POST   /api/clientes
GET    /api/clientes
GET    /api/clientes/{id}
PUT    /api/clientes/{id}
PATCH  /api/clientes/{id}
DELETE /api/clientes/{id}
```

### ms-cuentas

```
POST   /api/cuentas
GET    /api/cuentas
GET    /api/cuentas/{id}
PUT    /api/cuentas/{id}
PATCH  /api/cuentas/{id}
DELETE /api/cuentas/{id}
```

El borrado de cuentas se maneja mediante el campo `estado`, evitando la eliminación física.

## 7. Manejo de errores

Se implementa un manejador global de excepciones que traduce errores de dominio a respuestas HTTP consistentes (400, 404, 409 y 500).

## 8. Pruebas

Se incluyen pruebas unitarias y de integración.  
Para ejecutarlas:

```
mvn clean test
```

## 9. Ejecución con Docker

### Requisitos

- Docker
- Docker Compose

### Levantar el entorno

Desde la raíz del proyecto:

```
docker compose up --build
```

Esto levanta PostgreSQL, RabbitMQ y ambos microservicios.

### Verificación

- RabbitMQ: http://localhost:15672 (guest / guest)
- ms-clientes: http://localhost:8081
- ms-cuentas: http://localhost:8082

## 10. Flujo recomendado

1. Crear un cliente en ms-clientes.
2. ms-clientes publica el evento.
3. ms-cuentas actualiza `cliente_view`.
4. Crear cuentas.
5. Registrar movimientos.
6. Consultar información.

## 11. Postman

Se incluye una colección Postman con ejemplos de uso de los endpoints.

## 12. Consideraciones finales

La solución prioriza desacoplamiento, mantenibilidad, buenas prácticas REST y preparación para despliegues productivos mediante contenedores.