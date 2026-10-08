package com.HabitApp.HabitApp.ExternalApi;


import com.HabitApp.HabitApp.ExternalApi.NasaImages.NasaImagesService;
import com.HabitApp.HabitApp.ExternalApi.ZenQuotes.ZenQuotesService;
import org.springframework.stereotype.Service;


@Service
public class ExternalApiService {

    private final ZenQuotesService zenQuotesService;
    private final NasaImagesService nasaImagesService;
    private final Quote defaultQuote = new Quote("No more zero days", "u/ryans01");


    //if no quote found returns default quote instead.
    //kom ihåg rensa checks från senare delar sen, då denna ska alltid returnera giltig.

    public ExternalApiService(ZenQuotesService zenQuotesService, NasaImagesService nasaImagesService) {
        this.zenQuotesService = zenQuotesService;
        this.nasaImagesService = nasaImagesService;
    }

    public Quote getRandomZenQuote() {
        return validate(zenQuotesService.getRandomQuote());
    }

    //kanske today kalla random v behov?
    public Quote getTodaysZenQuote(){
        return validate(zenQuotesService.getTodaysQuote());
    }

    private Quote validate(Quote quote){
        if (quote == null || quote.quote() == null || quote.quote().isBlank()){
            return defaultQuote;
        }

        if (quote.author() == null || quote.author().isBlank()){
            return new Quote(quote.quote(), "Unknown");
        }
        return quote;
    }


    //lägg default/validate sen
    public ExternalImage getTodaysNasaImage() {
        return nasaImagesService.getTodays();
    }

    public ExternalImage getRandomNasaImage() {
        return nasaImagesService.getRandom();
    }

}
