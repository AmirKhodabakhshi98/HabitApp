package com.HabitApp.HabitApp.HabitTracker;

import com.HabitApp.HabitApp.Habit.Habit;
import com.HabitApp.HabitApp.Habit.RecurrenceTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "/api/habittracker")
public class HabitTrackerController {

    private final HabitTrackerService habitTrackerService;

    @Autowired
    public HabitTrackerController(HabitTrackerService habitTrackerService) {
        this.habitTrackerService = habitTrackerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<HabitTracker> getHabitTracker(@PathVariable Long id) {
        return habitTrackerService.findById(id)
                .map(ht -> new ResponseEntity<>(ht, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/completed/day")
    public List<HabitTracker> getCompletedDay(

            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return habitTrackerService.completedDay(date != null ? date : LocalDate.now(), RecurrenceTypes.DAILY);
    }

    @GetMapping("/completed/week")
    public List<HabitTracker> getCompletedWeek(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return habitTrackerService.completedWeek(date != null ? date : LocalDate.now(), RecurrenceTypes.WEEKLY);
    }

    @GetMapping("/completed/month")
    public List<HabitTracker> getCompletedMonth(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return habitTrackerService.completedMonth(date != null ? date : LocalDate.now(), RecurrenceTypes.MONTHLY);
    }

    @PostMapping
    public ResponseEntity<HabitTracker> addHabitTracker(@RequestBody HabitTracker habitTracker) {
        HabitTracker created = habitTrackerService.addHabitTracker(habitTracker);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<HabitTracker> updateHabitTracker(@RequestBody HabitTracker habitTracker) {
        HabitTracker updated = habitTrackerService.updateHabitTracker(habitTracker);
        if (updated != null) {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HabitTracker> deleteHabitTracker(@PathVariable Long id) {
        habitTrackerService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public List<HabitTracker> getHabitTrackersByHabitName(@RequestParam String name) {
        return habitTrackerService.findByHabitName(name);
    }



    @GetMapping("/notcompleted/day")
    public List<Habit> getNotCompletedDay(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return habitTrackerService.notCompletedDay(date != null ? date : LocalDate.now(), RecurrenceTypes.DAILY);
    }

    @GetMapping("/notcompleted/week")
    public List<Habit> getNotCompletedWeek(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return habitTrackerService.notCompletedWeek(date != null ? date : LocalDate.now(), RecurrenceTypes.WEEKLY);
    }

    @GetMapping("/notcompleted/month")
    public List<Habit> getNotCompletedMonth(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return habitTrackerService.notCompletedMonth(date != null ? date : LocalDate.now(), RecurrenceTypes.MONTHLY);
    }

}