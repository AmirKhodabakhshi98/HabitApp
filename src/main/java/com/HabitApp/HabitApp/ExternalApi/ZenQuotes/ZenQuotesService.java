package com.HabitApp.HabitApp.ExternalApi.ZenQuotes;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;

@Service
public class ZenQuotesService {
    private final RestClient zenQuotesClient;

    public ZenQuotesService(RestClient zenQuotesClient) {
        this.zenQuotesClient = zenQuotesClient;
    }

    public String getRandomQuote() {
        JsonNode response = zenQuotesClient
                .get()
                .uri("/random")
                .retrieve()
                .body(JsonNode.class);
        return responseToString(response);

    }

    public String getTodaysQuote() {
        JsonNode response = zenQuotesClient
                .get()
                .uri("/today")
                .retrieve()
                .body(JsonNode.class);
        return responseToString(response);
    }

    private String responseToString(JsonNode response) {
        assert response != null;
        return response.get(0).get("q").asString();
    }


}
