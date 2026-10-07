package com.HabitApp.HabitApp.Habit;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path ="api/habit")
public class HabitController {

    private final HabitService habitService;

    @Autowired
    public HabitController(HabitService habitService) {
        this.habitService = habitService;
    }

    @GetMapping
    public Habit getHabit(@RequestParam String name) {
        return habitService.findByName(name);
    }

    @GetMapping("/all")
    public List<Habit> getAllHabits() {
        return habitService.getAllHabits();
    }


    @PostMapping
    public ResponseEntity<Habit> addHabit(@RequestBody Habit habit){
        Habit createdHabit = habitService.addHabit(habit);
        return new ResponseEntity<>(createdHabit, HttpStatus.CREATED);
    }


    @PutMapping
    public ResponseEntity<Habit> updateHabit(@RequestBody Habit habit){
        Habit updatedHabit = habitService.updateHabit(habit);
        if(updatedHabit != null){
            return new ResponseEntity<>(updatedHabit, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("/{habitName}")
    public ResponseEntity<Habit> deleteHabit(@PathVariable String habitName){
        habitService.deleteByName(habitName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
