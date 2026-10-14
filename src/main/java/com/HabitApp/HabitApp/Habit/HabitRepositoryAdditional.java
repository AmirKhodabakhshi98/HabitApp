package com.HabitApp.HabitApp.Habit;

import java.util.List;

public interface HabitRepositoryAdditional {
    List<Object> findByDistinctColValues(String colName);

}
