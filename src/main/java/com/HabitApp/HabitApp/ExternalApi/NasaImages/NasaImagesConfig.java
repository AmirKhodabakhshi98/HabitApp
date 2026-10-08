package com.HabitApp.HabitApp.ExternalApi.NasaImages;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Configuration
class NasaImagesConfig {

    @Bean
    protected RestClient nasaImagesRestClient(RestClient.Builder builder) {
        return builder
                .baseUrl("https://science.nasa.gov/wp-json/wp/v2/apod-basic/")
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}

