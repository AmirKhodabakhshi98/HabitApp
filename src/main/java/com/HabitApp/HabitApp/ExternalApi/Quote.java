package com.HabitApp.HabitApp.ExternalApi;


import jakarta.validation.constraints.NotNull;

public record Quote (String quote, String author) {

    @Override
    public  String toString(){
        return "\"%s\" — %s".formatted(quote, author);
    }
}
