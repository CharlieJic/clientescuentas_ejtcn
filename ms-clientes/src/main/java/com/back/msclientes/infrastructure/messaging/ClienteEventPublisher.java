package com.back.msclientes.infrastructure.messaging;

import com.back.msclientes.infrastructure.config.RabbitConfig;
import com.back.msclientes.infrastructure.messaging.event.ClienteCreatedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class ClienteEventPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public ClienteEventPublisher(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void publishClienteCreated(ClienteCreatedEvent event) {
        try {
            String json = objectMapper.writeValueAsString(event);
            rabbitTemplate.convertAndSend(
                    RabbitConfig.CLIENTES_EXCHANGE,
                    RabbitConfig.CLIENTE_CREATED_ROUTING_KEY,
                    json
            );
        } catch (Exception e) {
            throw new IllegalStateException("No se pudo serializar ClienteCreatedEvent a JSON", e);
        }
    }
}