package com.fuckSpring.config.property;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by upsmart on 17-6-7.
 */
@Configuration
public class RabbitConfig {

    @Bean
    public Queue getQueue() {
        return new Queue("Ip_Proxy_Queue");
    }
}
