package com.HabitApp.HabitApp.Habit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HabitRepository extends JpaRepository<Habit, Long> {
    //get by name
    //get by category
    //get by location
    //get by min max normal duration
    //get by equipment

    //optional for when looking for one thing


    Optional<Habit> findByName(String name);
    List<Habit> findByCategory(String category);
    List<Habit> findByLocation(String location);
  //  List<Habit> findByMinTime(int minTime);
 //   List<Habit> findByMaxTime(int maxTime);
 //   List<Habit> findByNormalTime(int normalTime);
 //   List<Habit> findByMinMaxDurationMinutes(int minDurationMinutes, int maxDurationMinutes);
    List<Habit> findByEquipment(String equipment);


    Optional<Habit> findById(Long id);

    long deleteByName(String name);


}
