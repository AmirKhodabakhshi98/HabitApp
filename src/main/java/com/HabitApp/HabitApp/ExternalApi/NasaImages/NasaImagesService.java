package com.HabitApp.HabitApp.ExternalApi.NasaImages;

import com.HabitApp.HabitApp.ExternalApi.ExternalImage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;

@Slf4j
@Service
public class NasaImagesService {
    private final RestClient restClient;

    public NasaImagesService(@Qualifier("nasaImagesRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    public ExternalImage getNasaImage() {
        try {
            JsonNode response = restClient.get()
                    .uri("?api_key=DEMO_KEY")
                    .retrieve()
                    .body(JsonNode.class);

            if (response == null) { //or empty urls
                log.warn("NASA returned null");
                return new ExternalImage("");
            }
            //response = response.path(0);
            JsonNode entry = response.path(0);
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
