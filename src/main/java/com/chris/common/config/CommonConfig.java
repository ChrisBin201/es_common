package com.chris.common.config;

import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfig {
    @Bean
    public WebClient webClient() {
        return WebClient.create(Vertx.vertx());
    }
}
