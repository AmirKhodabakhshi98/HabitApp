package com.HabitApp.HabitApp.Habit;

public enum RecurrenceTypes {
    WHENEVER("Whenever"),
    ROLLING("Rolling"),
    DAILY("Daily"),
    WEEKLY("Weekly"),
    MONTHLY("Monthly"),
    YEARLY("Yearly");

    public static final RecurrenceTypes DEFAULT = WHENEVER;
    private final String label;
    RecurrenceTypes(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static RecurrenceTypes fromString(String value) {
        if (value == null || value.isBlank()) return DEFAULT;

        try {
            return RecurrenceTypes.valueOf(value.trim().toUpperCase());
        }catch (IllegalArgumentException e) {
            return DEFAULT;
        }

    }
}
