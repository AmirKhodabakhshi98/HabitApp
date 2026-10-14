package com.HabitApp.HabitApp.HabitTracker;


import com.HabitApp.HabitApp.Habit.Habit;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Table(name = "habit_tracker")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class HabitTracker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "habits_id", nullable = false)
    private Habit habit;

    //kanske borde varit LocalDate ist
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdDate;

    @Column(nullable = false)
    private LocalDateTime completedDate;


    private Double value;


    private String note;


}
