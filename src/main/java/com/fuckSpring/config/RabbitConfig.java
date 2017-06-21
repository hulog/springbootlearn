package com.fuckSpring.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by upsmart on 17-6-7.
 */
@Configuration
public class RabbitConfig {

    private String queueName;
    private String routingKey;
    private String exchangeName;

    @Bean
    public Queue getQueue() {
        return new Queue("Ip_Proxy_Queue", true);
    }

    @Bean
    public DirectExchange getExchange() {
        return new DirectExchange(exchangeName, false, false);
    }

    @Bean
    public Binding getBinding() {
        return new Binding(queueName, Binding.DestinationType.EXCHANGE, exchangeName, routingKey, null);
    }
}
