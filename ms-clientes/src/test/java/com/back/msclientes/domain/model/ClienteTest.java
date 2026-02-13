package com.back.msclientes.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    @Test
    void constructor_shouldMapAllFieldsCorrectly() {
        Cliente c = new Cliente(
                10L,
                "Jose Lema",
                Genero.M,
                30,
                "0101",
                "Otavalo",
                "098254785",
                "1234",
                true
        );

        // Campos de Cliente
        assertEquals(10L, c.getClienteId());
        assertEquals("1234", c.getContrasena());
        assertTrue(c.getEstado());

        // Campos heredados de Persona
        assertEquals("Jose Lema", c.getNombre());
        assertEquals(Genero.M, c.getGenero());
        assertEquals(30, c.getEdad());
        assertEquals("0101", c.getIdentificacion());
        assertEquals("Otavalo", c.getDireccion());
        assertEquals("098254785", c.getTelefono());
    }
}