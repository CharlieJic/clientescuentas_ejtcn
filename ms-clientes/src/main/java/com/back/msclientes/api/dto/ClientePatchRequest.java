package com.back.msclientes.api.dto;

import com.back.msclientes.domain.model.Genero;
import jakarta.validation.constraints.*;

public record ClientePatchRequest(

        @Size(min = 2, max = 80, message = "nombre debe tener entre 2 y 80 caracteres")
        @Pattern(
                regexp = "^[A-Za-zÁÉÍÓÚÜÑáéíóúüñ]+([ .'-][A-Za-zÁÉÍÓÚÜÑáéíóúüñ]+)*$",
                message = "nombre solo puede contener letras y espacios"
        )
        String nombre,

        Genero genero,

        @Min(value = 0, message = "edad no puede ser negativa")
        @Max(value = 120, message = "edad no puede ser mayor a 120")
        Integer edad,

        @Size(min = 6, max = 20, message = "identificacion debe tener entre 6 y 20 dígitos")
        @Pattern(regexp = "^[0-9]+$", message = "identificacion solo puede contener números")
        String identificacion,

        @Size(min = 5, max = 120, message = "direccion debe tener entre 5 y 120 caracteres")
        String direccion,

        @Size(min = 7, max = 15, message = "telefono debe tener entre 7 y 15 dígitos")
        @Pattern(regexp = "^[0-9]+$", message = "telefono solo puede contener números")
        String telefono,

        @Size(min = 4, max = 60, message = "contrasena debe tener entre 4 y 60 caracteres")
        String contrasena,

        Boolean estado
) {}