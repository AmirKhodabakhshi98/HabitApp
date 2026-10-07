package com.HabitApp.HabitApp.ExternalApi.ZenQuotes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;


@Configuration
public class ZenQuotesConfig {

    @Bean
    public RestClient zenQuotesRestClient(RestClient.Builder builder) {
        return builder
                .baseUrl("https://zenquotes.io/api")
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
