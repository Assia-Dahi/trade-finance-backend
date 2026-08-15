package com.tradefinance.trade_finance_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AiWebClientConfig {

    @Bean
    public WebClient aiWebClient(AIConfig aiConfig) {
        return WebClient.builder()
                .baseUrl(aiConfig.getApiUrl())
                .defaultHeader("x-api-key", aiConfig.getApiKey())
                .defaultHeader("anthropic-version", "2023-06-01")
                .defaultHeader("content-type", "application/json")
                .build();
    }
}