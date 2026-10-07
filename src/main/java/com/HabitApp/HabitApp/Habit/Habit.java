package com.HabitApp.HabitApp.Habit;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="habits")
public class Habit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(name = "min_duration_minutes")
    private int minDurationMinutes;
    @Column(name = "max_duration_minutes")
    private int maxDurationMinutes;
    @Column(name = "normal_duration_minutes")
    private int normalDurationMinutes;
    private String category;
    private String location;
    private String equipment;
    @Column(name = "recurrence_type")
    private String recurrenceType;
    @Column(name = "recurrence_value")
    private int recurrenceValue;

    public Habit() {
        this.name = name;
    }

    public Habit(String name) {
        this.name = name;
    }

    public Habit(String equipment, String name, int minDurationMinutes, int maxDurationMinutes, int normalDurationMinutes, String category, String location, String recurrenceType, int recurrenceValue) {
        this.equipment = equipment;
        this.name = name;
        this.minDurationMinutes = minDurationMinutes;
        this.maxDurationMinutes = maxDurationMinutes;
        this.normalDurationMinutes = normalDurationMinutes;
        this.category = category;
        this.location = location;
        this.recurrenceType = recurrenceType;
        this.recurrenceValue = recurrenceValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinDurationMinutes() {
        return minDurationMinutes;
    }

    public void setMinDurationMinutes(int minDurationMinutes) {
        this.minDurationMinutes = minDurationMinutes;
    }

    public int getMaxDurationMinutes() {
        return maxDurationMinutes;
    }

    public void setMaxDurationMinutes(int maxDurationMinutes) {
        this.maxDurationMinutes = maxDurationMinutes;
    }

    public int getNormalDurationMinutes() {
        return normalDurationMinutes;
    }

    public void setNormalDurationMinutes(int normalDurationMinutes) {
        this.normalDurationMinutes = normalDurationMinutes;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getRecurrenceType() {
        return recurrenceType;
    }

    public void setRecurrenceType(String recurrenceType) {
        this.recurrenceType = recurrenceType;
    }

    public int getRecurrenceValue() {
        return recurrenceValue;
    }

    public void setRecurrenceValue(int recurrenceValue) {
        this.recurrenceValue = recurrenceValue;
    }
}
