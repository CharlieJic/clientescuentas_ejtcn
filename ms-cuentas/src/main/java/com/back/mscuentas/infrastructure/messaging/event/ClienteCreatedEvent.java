package com.back.mscuentas.infrastructure.messaging.event;

public record ClienteCreatedEvent(
        Long clienteId,
        String nombre,
        String genero,
        Integer edad,
        String identificacion,
        String direccion,
        String telefono,
        Boolean estado
) {}