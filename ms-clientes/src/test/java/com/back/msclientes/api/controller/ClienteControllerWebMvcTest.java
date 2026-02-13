package com.back.msclientes.api.controller;

import com.back.msclientes.application.service.ClienteService;
import com.back.msclientes.domain.model.Cliente;
import com.back.msclientes.domain.model.Genero;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ClienteControllerWebMvcTest {

    private ClienteService clienteService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.clienteService = Mockito.mock(ClienteService.class);

        ClienteController controller = new ClienteController(clienteService);

        this.mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    void getClienteById_shouldReturn200_andJson() throws Exception {
        Mockito.when(clienteService.getById(1L)).thenReturn(
                new Cliente(1L, "Jose", Genero.M, 30, "0101", "Dir", "099", "1234", true)
        );

        mockMvc.perform(get("/api/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.clienteId").value(1))
                .andExpect(jsonPath("$.nombre").value("Jose"));
    }

    @Test
    void getAllClientes_shouldReturn200_andArray() throws Exception {
        Mockito.when(clienteService.list()).thenReturn(List.of(
                new Cliente(1L, "Jose", Genero.M, 30, "0101", "Dir", "099", "1234", true),
                new Cliente(2L, "Maria", Genero.F, 25, "0202", "Dir2", "088", "abcd", true)
        ));

        mockMvc.perform(get("/api/clientes"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].clienteId").value(1))
                .andExpect(jsonPath("$[1].clienteId").value(2));
    }
}