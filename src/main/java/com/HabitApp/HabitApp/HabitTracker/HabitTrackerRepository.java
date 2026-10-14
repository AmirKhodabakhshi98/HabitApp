package com.HabitApp.HabitApp.HabitTracker;

import com.HabitApp.HabitApp.Habit.Habit;
import com.HabitApp.HabitApp.Habit.RecurrenceTypes;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HabitTrackerRepository
extends JpaRepository<HabitTracker, Long> {

    //ev. find för värden i habit?

    @Query("""
    SELECT h
    FROM Habit h
    WHERE h.recurrenceType = :type
      AND NOT EXISTS (
          SELECT 1
          FROM HabitTracker ht
          WHERE ht.habit = h
            AND ht.completedDate >= :start
            AND ht.completedDate < :end
      )
    """)
    List<Habit> findNotCompletedBetween(@Param("start") LocalDateTime start,
                                        @Param("end") LocalDateTime end,
                                        @Param("type") RecurrenceTypes type);

    //eg DAILY --> kolla idag vilka completed med daily rec
    @EntityGraph(attributePaths = "habit")
    List<HabitTracker> findByCompletedDateGreaterThanEqualAndCompletedDateLessThanAndHabit_RecurrenceType(
            LocalDateTime start,
            LocalDateTime end,
            RecurrenceTypes type);

    @EntityGraph(attributePaths = "habit")
    List<HabitTracker> findByHabit_Name(String name);

    long deleteById(long habitTrackerId);

}
