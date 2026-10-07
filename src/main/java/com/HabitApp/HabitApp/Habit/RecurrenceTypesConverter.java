package com.HabitApp.HabitApp.Habit;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RecurrenceTypesConverter implements AttributeConverter<RecurrenceTypes, String> {

    @Override
    public String convertToDatabaseColumn(RecurrenceTypes attribute) {
        return (attribute == null ? RecurrenceTypes.DEFAULT : attribute).name();
    }

    @Override
    public RecurrenceTypes convertToEntityAttribute(String dbData) {
        return RecurrenceTypes.fromString(dbData);
    }
}
