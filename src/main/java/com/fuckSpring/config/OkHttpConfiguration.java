package com.fuckSpring.config;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by upsmart on 17-6-3.
 */
@Configuration
public class OkHttpConfiguration {

    @Bean
    public OkHttpClient getClient() {
        OkHttpClient okHttpClient = new OkHttpClient();
        return okHttpClient;
    }

}
