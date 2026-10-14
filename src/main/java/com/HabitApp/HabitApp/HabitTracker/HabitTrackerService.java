package com.HabitApp.HabitApp.HabitTracker;

import com.HabitApp.HabitApp.Habit.Habit;
import com.HabitApp.HabitApp.Habit.RecurrenceTypes;
import jakarta.persistence.PrePersist;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class HabitTrackerService {
    private final HabitTrackerRepository habitTrackerRepository;

    public HabitTrackerService(HabitTrackerRepository habitTrackerRepository) {
        this.habitTrackerRepository = habitTrackerRepository;
    }


    public Optional<HabitTracker> findById(Long id) {
        return habitTrackerRepository.findById(id);
    }

    @Transactional
    public HabitTracker addHabitTracker(HabitTracker habitTracker){
       return habitTrackerRepository.save(habitTracker);
    }

    @Transactional
    public void deleteById(Long id){
        habitTrackerRepository.deleteById(id);
    }

    @Transactional
    public HabitTracker updateHabitTracker(HabitTracker updated){
            Optional<HabitTracker> optionalHabitTracker = habitTrackerRepository.findById(updated.getId());

            if(optionalHabitTracker.isPresent()){
                HabitTracker habitTracker = optionalHabitTracker.get();
                habitTracker.setHabit(updated.getHabit());
                habitTracker.setCompletedDate(updated.getCompletedDate());
                habitTracker.setNote(updated.getNote());
                habitTracker.setValue(updated.getValue());
                habitTrackerRepository.save(habitTracker);
                return habitTracker;
            }
            return null;
    }


    public List<HabitTracker> completedDay(LocalDate ld, RecurrenceTypes type) {
        return completedByTypeBetween(ld, ld.plusDays(1), type);
    }

    public List<HabitTracker> completedDay(LocalDateTime ldt, RecurrenceTypes type) {
        return completedDay(ldt.toLocalDate(), type);
    }


    public List<HabitTracker> completedWeek(LocalDateTime ldt, RecurrenceTypes type) {
        return completedWeek(ldt.toLocalDate(), type);
    }

    public List<HabitTracker> completedWeek(LocalDate ld, RecurrenceTypes type) {
        LocalDate monday =  ld.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        return completedByTypeBetween(monday, monday.plusWeeks(1), type);
    }

    public List<HabitTracker> completedMonth(LocalDateTime ldt, RecurrenceTypes type) {

        return completedMonth(ldt.toLocalDate(), type);
    }

    public List<HabitTracker> completedMonth(LocalDate ld, RecurrenceTypes type) {
        LocalDate monthStart = ld.withDayOfMonth(1);
        return completedByTypeBetween(monthStart, monthStart.plusMonths(1), type);
    }

    private List<HabitTracker> completedByTypeBetween(LocalDate start, LocalDate end, RecurrenceTypes type) {

        return habitTrackerRepository.findByCompletedDateGreaterThanEqualAndCompletedDateLessThanAndHabit_RecurrenceType(
                start.atStartOfDay(), end.atStartOfDay() , type
        );

    }
    public List<HabitTracker> findByHabitName(String name) {
        return habitTrackerRepository.findByHabit_Name(name);
    }




    public List<Habit> notCompletedDay(LocalDate ld, RecurrenceTypes type) {
        return notCompletedBetween(ld, ld.plusDays(1), type);
    }

    public List<Habit> notCompletedDay(LocalDateTime ldt, RecurrenceTypes type) {
        return notCompletedDay(ldt.toLocalDate(), type);
    }

    public List<Habit> notCompletedWeek(LocalDate ld, RecurrenceTypes type) {
        LocalDate monday = ld.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        return notCompletedBetween(monday, monday.plusWeeks(1), type);
    }

    public List<Habit> notCompletedWeek(LocalDateTime ldt, RecurrenceTypes type) {
        return notCompletedWeek(ldt.toLocalDate(), type);
    }

    public List<Habit> notCompletedMonth(LocalDate ld, RecurrenceTypes type) {
        LocalDate monthStart = ld.withDayOfMonth(1);
        return notCompletedBetween(monthStart, monthStart.plusMonths(1), type);
    }

    public List<Habit> notCompletedMonth(LocalDateTime ldt, RecurrenceTypes type) {
        return notCompletedMonth(ldt.toLocalDate(), type);
    }

    private List<Habit> notCompletedBetween(LocalDate start, LocalDate end, RecurrenceTypes type) {
        return habitTrackerRepository.findNotCompletedBetween(
                start.atStartOfDay(), end.atStartOfDay(), type);
    }

}
