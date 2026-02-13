package com.back.mscuentas.api.controller;

import com.back.mscuentas.application.service.CuentaService;
import com.back.mscuentas.infrastructure.persistence.entity.CuentaEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CuentaControllerWebMvcTest {

    private CuentaService cuentaService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.cuentaService = Mockito.mock(CuentaService.class);
        CuentaController controller = new CuentaController(cuentaService);

        this.mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    void getCuentaById_shouldReturn200_andJson() throws Exception {
        CuentaEntity e = new CuentaEntity();
        e.setCuentaId(1L);
        e.setNumeroCuenta("0000000001");
        e.setTipoCuenta("AHORROS");
        e.setSaldoInicial(new BigDecimal("1000.00"));
        e.setEstado(true);
        e.setClienteId(10L);

        Mockito.when(cuentaService.getById(1L)).thenReturn(e);

        mockMvc.perform(get("/api/cuentas/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.cuentaId").value(1))
                .andExpect(jsonPath("$.numeroCuenta").value("0000000001"))
                .andExpect(jsonPath("$.clienteId").value(10));
    }

    @Test
    void listCuentas_shouldReturn200_andArray() throws Exception {
        CuentaEntity e1 = new CuentaEntity();
        e1.setCuentaId(1L);
        e1.setNumeroCuenta("0000000001");
        e1.setTipoCuenta("AHORROS");
        e1.setSaldoInicial(new BigDecimal("1000.00"));
        e1.setEstado(true);
        e1.setClienteId(10L);

        CuentaEntity e2 = new CuentaEntity();
        e2.setCuentaId(2L);
        e2.setNumeroCuenta("0000000002");
        e2.setTipoCuenta("CORRIENTE");
        e2.setSaldoInicial(new BigDecimal("200.00"));
        e2.setEstado(true);
        e2.setClienteId(11L);

        Mockito.when(cuentaService.list()).thenReturn(List.of(e1, e2));

        mockMvc.perform(get("/api/cuentas"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].cuentaId").value(1))
                .andExpect(jsonPath("$[1].cuentaId").value(2));
    }
}