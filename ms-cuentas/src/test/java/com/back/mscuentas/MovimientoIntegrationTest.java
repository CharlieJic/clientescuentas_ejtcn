package com.back.mscuentas;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class MovimientoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    // Evita que el contexto intente conectarse a RabbitMQ
    @MockBean private ConnectionFactory connectionFactory;
    @MockBean private RabbitTemplate rabbitTemplate;
    @MockBean private AmqpAdmin amqpAdmin;

    @Test
    void deposito_shouldReturn201_andSaldoAumenta() throws Exception {
        // Cuenta 1 existe por data.sql con saldoInicial=1000.00
        String body = """
            {
              "cuentaId": 1,
              "tipoMovimiento": "DEPOSITO",
              "valor": 100.00
            }
        """;

        mockMvc.perform(post("/api/movimientos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.cuentaId").value(1))
                .andExpect(jsonPath("$.tipoMovimiento").value("DEPOSITO"))
                .andExpect(jsonPath("$.valor").value(100.0))
                .andExpect(jsonPath("$.saldo").value(1100.0))
                .andExpect(jsonPath("$.movimientoId").exists())
                .andExpect(jsonPath("$.fecha").exists());
    }

    @Test
    void retiro_conSaldoInsuficiente_shouldReturn400_yMensaje() throws Exception {
        // Cuenta 1 existe por data.sql con saldoInicial=1000.00
        // Retiro 2000 => saldoNuevo < 0 => BusinessException("Saldo no disponible") => 400
        String body = """
            {
              "cuentaId": 1,
              "tipoMovimiento": "RETIRO",
              "valor": 2000.00
            }
        """;

        mockMvc.perform(post("/api/movimientos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Saldo no disponible"));
    }
}