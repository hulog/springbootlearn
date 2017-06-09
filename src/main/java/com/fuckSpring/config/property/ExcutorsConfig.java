package com.fuckSpring.config.property;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by upsmart on 17-6-8.
 */
@Configuration
public class ExcutorsConfig {

    @Bean
    public ExecutorService getES() {
        ExecutorService es = Executors.newFixedThreadPool(8);
        return es;
    }
}
