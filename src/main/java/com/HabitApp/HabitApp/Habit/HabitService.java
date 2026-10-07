package com.HabitApp.HabitApp.Habit;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class HabitService {

    @Autowired
    private final HabitRepository habitRepository;
    public HabitService(HabitRepository habitRepository) {
        this.habitRepository = habitRepository;
    }

    public List<Habit> getAllHabits() {
        return habitRepository.findAll();
    }
    //get by name
    //get by category
    //get by location
    //get by min max normal duration
    //get by equipment

    //optional for when looking for one thing

    public Habit findByName(String name){
        return habitRepository.findByName(name).orElse(null);
    }
    public List<Habit> findByCategory(String category) {
        return habitRepository.findByCategory(category);
    }

    public List<Habit> findByLocation(String location) {
        return habitRepository.findByLocation(location);
    }
    public List<Habit> findByEquipment(String equipment) {
        return habitRepository.findByEquipment(equipment);
    }

  //  public List<Habit> findByMinMaxDurationMinutes(int minDurationMinutes, int maxDurationMinutes) {
    //    return habitRepository.findByMinMaxDurationMinutes(minDurationMinutes, maxDurationMinutes);
  //  }

    @Transactional
    public boolean deleteByName(String name) {
        return habitRepository.deleteByName(name) > 0;
    }

    public Habit addHabit(Habit habit){
        habitRepository.save(habit);
        return habit;
    }

    public Habit updateHabit(Habit updatedHabit){
        Optional<Habit> optionalHabit = habitRepository.findById(updatedHabit.getId());

        if(optionalHabit.isPresent()){
            Habit habit = optionalHabit.get();
            habit.setName(updatedHabit.getName());
            habit.setCategory(updatedHabit.getCategory());
            habit.setLocation(updatedHabit.getLocation());
            habit.setEquipment(updatedHabit.getEquipment());
            habit.setMinDurationMinutes(updatedHabit.getMinDurationMinutes());
            habit.setMaxDurationMinutes(updatedHabit.getMaxDurationMinutes());
            habit.setNormalDurationMinutes(updatedHabit.getNormalDurationMinutes());
            habit.setRecurrenceType(updatedHabit.getRecurrenceType());
            habit.setRecurrenceValue(updatedHabit.getRecurrenceValue());
            habitRepository.save(habit);
            return habit;
        }
        return null;
    }


}
