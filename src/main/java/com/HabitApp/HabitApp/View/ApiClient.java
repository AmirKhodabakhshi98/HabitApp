package com.HabitApp.HabitApp.View;

import com.HabitApp.HabitApp.Habit.Habit;
import com.HabitApp.HabitApp.Habit.RecurrenceTypes;
import com.HabitApp.HabitApp.HabitTracker.HabitTracker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Component
public class ApiClient {
    private final RestClient client;
    private final String habitApiUrl = "/api/habit/";
    private final String trackerApiUrl = "/api/habittracker/";

    public ApiClient(@Value("${api.base-url:http://localhost:8080}") String baseUrl) {
        this.client = RestClient.create(baseUrl);
        System.out.println("Base URL: " + baseUrl);
        System.out.println("Endpoint: " + trackerApiUrl);
        System.out.println(baseUrl+trackerApiUrl);
        System.err.println();
    }

    public List<Habit> getAllHabits() {
        return client.get().uri(habitApiUrl + "all")
                .retrieve()
                .body(new ParameterizedTypeReference<List<Habit>>() {});
    }

    public void createHabit(Habit habit) {
        client.post().uri(habitApiUrl)
                .body(habit)
                .retrieve()
                .body(Habit.class);
    }

    public void updateHabit(Habit habit) {
        client.put().uri(habitApiUrl)
                .body(habit)
                .retrieve()
                .body(Habit.class);
    }

    public void delete(Habit habit) {
        client.delete().uri(habitApiUrl + "{name}", habit.getName())
                .retrieve()
                .toBodilessEntity();

    }



    private List<Habit> getHabitsCompletionType(boolean completed, RecurrenceTypes type){
        String completionS = completed ? "completed" : "notcompleted";

        String periodS = switch (type) {
            case DAILY -> "day";
            case WEEKLY -> "week";
            case MONTHLY -> "month";
            default -> "";
        };

        return client.get().uri(trackerApiUrl + completionS + "/" + periodS)
                .retrieve()
                .body(new ParameterizedTypeReference<List<Habit>>(){});

    }

    public List<Habit> getHabitCompleted(RecurrenceTypes type){
        return getHabitsCompletionType(true, type);
    }

    public List<Habit> getHabitNotCompleted(RecurrenceTypes type){
        return getHabitsCompletionType(false, type);
    }

    public void habitCompleted(HabitTracker habit) {

        System.out.println("Sending POST request...");

        try {
            ResponseEntity<String> response = client.post()
                    .uri(trackerApiUrl)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(habit)
                    .retrieve()
                    .toEntity(String.class);

            System.out.println("Status: " + response.getStatusCode());
            System.out.println("Body: " + response.getBody());

        } catch (RestClientResponseException e) {
            System.out.println("Status: " + e.getStatusCode());
            System.out.println("Body: " + e.getResponseBodyAsString());
            e.printStackTrace();
        }

        System.err.println();
    }

    public void deleteCompletedHabit(HabitTracker habit) {
        client.delete().uri(trackerApiUrl +"{id}", habit.getId());
    }

}