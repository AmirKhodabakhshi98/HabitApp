package com.HabitApp.HabitApp.ExternalApi.ZenQuotes;

import com.HabitApp.HabitApp.ExternalApi.Quote;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;

@Slf4j
@Service
public class ZenQuotesService {
    private final RestClient restClient;

    public ZenQuotesService(@Qualifier("zenQuotesRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    public Quote getRandomQuote() {
        return getQuote("/random");
    }

    public Quote getTodaysQuote() {
        return getQuote("/today");
    }


    //returns blank if call fails
    private Quote getQuote(String uri) {
        try {
            JsonNode response = restClient
                    .get()
                    .uri(uri)
                    .retrieve()
                    .body(JsonNode.class);

            //fundera/kolla upp bättre sätt sen
            if (response == null || response.isEmpty()) {
                log.warn("ZenQuotes returned no quote for URI: {}", uri);
                return new Quote("","");
            }
            JsonNode quote = response.path(0);
            return new Quote(quote.get("q").asString(""),
                    quote.get("a").asString(""));
        }catch (Exception e){
            log.error("ZenQuotes retrival failed: ", e);
            return new Quote("","");
        }
    }


}
