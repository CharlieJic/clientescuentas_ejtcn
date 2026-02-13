package com.back.msclientes.infrastructure.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String CLIENTES_EXCHANGE = "clientes.exchange";
    public static final String CLIENTE_CREATED_QUEUE = "cliente.created.queue";
    public static final String CLIENTE_CREATED_ROUTING_KEY = "cliente.created";

    @Bean
    public TopicExchange clientesExchange() {
        return new TopicExchange(CLIENTES_EXCHANGE);
    }

    @Bean
    public Queue clienteCreatedQueue() {
        return QueueBuilder.durable(CLIENTE_CREATED_QUEUE).build();
    }

    @Bean
    public Binding clienteCreatedBinding() {
        return BindingBuilder
                .bind(clienteCreatedQueue())
                .to(clientesExchange())
                .with(CLIENTE_CREATED_ROUTING_KEY);
    }
}