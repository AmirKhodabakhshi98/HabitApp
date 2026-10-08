package com.HabitApp.HabitApp.ExternalApi.NasaImages;

import com.HabitApp.HabitApp.ExternalApi.ExternalImage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;

import java.util.Random;

@Slf4j
@Service
public class NasaImagesService {
    private final RestClient restClient;
    private Random randomGenerator;
    public NasaImagesService(@Qualifier("nasaImagesRestClient") RestClient restClient) {
        this.restClient = restClient;
        this.randomGenerator = new Random();
    }


    public ExternalImage getRandom() {
        //count 1 verkar som d bara ger dagens
        return getImage("/?count=2&api_key=DEMO_KEY", true);

    }
    public ExternalImage getTodays() {

        return getImage("/?api_key=DEMO_KEY", false);
    }


    private ExternalImage getImage(String uri, boolean random) {
        try {
            JsonNode response = restClient.get()
                    .uri(uri)
                    .retrieve()
                    .body(JsonNode.class);

            if (response == null) { //or empty urls
                log.warn("NASA returned null");
                return new ExternalImage("");
            }

            int index = random ?
                    randomGenerator.nextInt(response.size()) :0 ;

            JsonNode entry = response.get(index);

            String url = entry.path("hdurl").asString("");

            if (url.isBlank()) {
                log.warn("NASA returned empty hdurl");
            }

            return new ExternalImage(url);

        } catch (Exception e) {
            log.error("Nasa image call failed: ", e);
            return new ExternalImage("");
        }
    }




}
