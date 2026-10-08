package com.HabitApp.HabitApp.ExternalApi;


import com.HabitApp.HabitApp.ExternalApi.NasaImages.NasaImagesService;
import com.HabitApp.HabitApp.ExternalApi.ZenQuotes.ZenQuotesService;
import org.springframework.stereotype.Service;


@Service
public class externalApiService {

    private final ZenQuotesService zenQuotesService;
    private final NasaImagesService nasaImagesService;

    public externalApiService(ZenQuotesService zenQuotesService, NasaImagesService nasaImagesService) {
        this.zenQuotesService = zenQuotesService;
        this.nasaImagesService = nasaImagesService;
    }

    public Quote getRandomZenQuote() {
        return zenQuotesService.getRandomQuote();
    }

    //kanske today kalla random v behov?
    public Quote getTodaysZenQuote(){
        return zenQuotesService.getTodaysQuote();
    }

    public ExternalImage getNasaImage() {
        return nasaImagesService.getNasaImage();
    }

}
