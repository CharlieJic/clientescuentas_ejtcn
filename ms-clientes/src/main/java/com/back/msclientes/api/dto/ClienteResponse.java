package com.back.msclientes.api.dto;

import com.back.msclientes.domain.model.Genero;

public record ClienteResponse(
        Long clienteId,
        String nombre,
        Genero genero,
        Integer edad,
        String identificacion,
        String direccion,
        String telefono,
        Boolean estado
) {}