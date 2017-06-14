package com.fuckSpring.config;

import com.fuckSpring.domain.pollRelated.IpInfoDO;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * Created by upsmart on 17-6-3.
 */
@Configuration
public class OkHttpConfiguration {

    @Bean
    public OkHttpClient getClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .connectTimeout(5, TimeUnit.SECONDS)
//                .readTimeout(5,TimeUnit.SECONDS)
                .build();
        return okHttpClient;
    }

    @Bean
    public OkHttpClient getProxyClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .build();
        return okHttpClient;
    }
}
