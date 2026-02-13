package com.back.msclientes.infrastructure.messaging.event;

import com.back.msclientes.domain.model.Genero;

public record ClienteCreatedEvent(
        Long clienteId,
        String nombre,
        Genero genero,
        Integer edad,
        String identificacion,
        String direccion,
        String telefono,
        Boolean estado
) {}