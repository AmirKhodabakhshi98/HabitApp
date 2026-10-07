package com.HabitApp.HabitApp.Habit;


import jakarta.persistence.*;

@Entity
@Table(name="habits")
public class Habit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true) //throwa/visa nånstans namnet finns
    private String name;

    private String category;
    private String location;
    private String equipment;

    @Column(name = "normal_duration_minutes")
    private Integer normalDurationMinutes;
    @Column(name = "min_duration_minutes")
    private Integer minDurationMinutes;
    @Column(name = "max_duration_minutes")
    private Integer maxDurationMinutes;


    @Column(name = "recurrence_type")
    private RecurrenceTypes recurrenceType = RecurrenceTypes.DEFAULT;
    @Column(name = "recurrence_value")
    private Integer recurrenceValue;

    public Habit() {}

    public Habit(String name) {
        this.name = name;
    }

    public Habit(String name, String category, String location, String equipment, Integer normalDurationMinutes, Integer minDurationMinutes, Integer maxDurationMinutes,  RecurrenceTypes recurrenceType, Integer recurrenceValue) {
        setName(name);
        setCategory(category);
        setLocation(location);
        setEquipment(equipment);
        setNormalDurationMinutes(normalDurationMinutes);
        setMinDurationMinutes(minDurationMinutes);
        setMaxDurationMinutes(maxDurationMinutes);
        setRecurrenceType(recurrenceType);
        setRecurrenceValue(recurrenceValue);
    }

    private String emptyToNull(String value){
        return value == null || value.isBlank()
                ? null
                : value;
    }

    private Integer negativeToNull(Integer value){
        return value == null || value < 0
                ? null
                : value;
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

    public Integer getMinDurationMinutes() {
        return minDurationMinutes;
    }

    public void setMinDurationMinutes(Integer minDurationMinutes) {
        this.minDurationMinutes = negativeToNull(minDurationMinutes);
    }

    public Integer getMaxDurationMinutes() {
        return maxDurationMinutes;
    }

    public void setMaxDurationMinutes(Integer maxDurationMinutes) {
        this.maxDurationMinutes = negativeToNull(maxDurationMinutes);
    }

    public Integer getNormalDurationMinutes() {
        return normalDurationMinutes;
    }

    public void setNormalDurationMinutes(Integer normalDurationMinutes) {
        this.normalDurationMinutes = negativeToNull(normalDurationMinutes);
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = emptyToNull(category);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = emptyToNull(location);
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = emptyToNull(equipment);
    }

    public RecurrenceTypes getRecurrenceType() {
        return recurrenceType;
    }

    public void setRecurrenceType(RecurrenceTypes recurrenceType) {
        this.recurrenceType = recurrenceType;
    }

    public Integer getRecurrenceValue() {
        return recurrenceValue;
    }

    public void setRecurrenceValue(Integer recurrenceValue) {
        this.recurrenceValue = negativeToNull(recurrenceValue);
    }
}
