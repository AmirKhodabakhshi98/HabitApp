package com.HabitApp.HabitApp.View;

import com.HabitApp.HabitApp.Habit.Habit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class HabitApiClient {
    private final RestClient client;

    public HabitApiClient(@Value("${api.base-url:http://localhost:8080}") String baseUrl) {
        this.client = RestClient.create(baseUrl);
    }

    public List<Habit> getAll() {
        return client.get().uri("/api/habit/all")
                .retrieve()
                .body(new ParameterizedTypeReference<List<Habit>>() {});
    }

    public Habit create(Habit habit) {
        return client.post().uri("/api/habit")
                .body(habit)
                .retrieve()
                .body(Habit.class);
    }

    public Habit update(Habit habit) {
        return client.put().uri("/api/habit")
                .body(habit)
                .retrieve()
                .body(Habit.class);
    }

    public void delete(Habit habit) {
        client.delete().uri("/api/habit/{name}", habit.getName())
                .retrieve()
                .toBodilessEntity();
    }
}