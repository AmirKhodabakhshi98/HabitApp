package com.HabitApp.HabitApp.View.HabitTrackerView;


import com.HabitApp.HabitApp.Habit.Habit;
import com.HabitApp.HabitApp.Habit.RecurrenceTypes;
import com.HabitApp.HabitApp.HabitTracker.HabitTracker;
import com.HabitApp.HabitApp.View.ApiClient;
import com.HabitApp.HabitApp.View.UIConstants;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class ToDoView extends VerticalLayout {
    private final ApiClient api;
    private Grid<Habit> grid;
   // private Map<Habit, String> habitNoteMap = new HashMap<>();
   // private Map<Habit, Double> habitValueMap = new HashMap<>();
    //private HabitForm editForm;

    private Habit current;
    //för sen o spara calls etc
    private HashMap<RecurrenceTypes, List<Habit>> toDoHabits = new HashMap<>();
    //en vacker dag kmr nt behövas uwu
    private final List<RecurrenceTypes> recurrencesImplemented = List.of(
            RecurrenceTypes.DAILY,
            RecurrenceTypes.WEEKLY,
            RecurrenceTypes.MONTHLY
    );

    private ComboBox<RecurrenceTypes> comboBox;
    public ToDoView(ApiClient api) {
        this.api = api;
        comboBox = initRecurrenceComboBox();
        this.grid = initGrid();
        setWidthFull();
        setHeightFull();
        getStyle().set("border", "1px solid " + UIConstants.OUTLINE);
        add(comboBox,grid);
    }


    private void updateGrid(RecurrenceTypes recurrenceType) {
        grid.setItems(api.getHabitNotCompleted(recurrenceType));
    }

    private ComboBox<RecurrenceTypes> initRecurrenceComboBox() {
        ComboBox<RecurrenceTypes> comboBox = new ComboBox<>();

        comboBox.setItems(recurrencesImplemented);
        comboBox.setItemLabelGenerator(RecurrenceTypes::getLabel);
        comboBox.setClearButtonVisible(false);
        comboBox.setRequired(true);
        comboBox.setValue(recurrencesImplemented.getFirst());


        comboBox.addValueChangeListener(event -> {
                updateGrid(event.getValue());
        });
        comboBox.getStyle().set("cursor", "pointer");
        return comboBox;
    }

    //sen så ba hämtar 1 gång
    private void refreshGrid() {}


    private Grid<Habit> initGrid() {
        Grid<Habit> grid = new Grid<>();
        Map<Habit, TextField> habitNoteMap = new HashMap<>();
        Map<Habit, NumberField> habitValueMap = new HashMap<>();


        grid.addColumn(habit -> Objects.requireNonNullElse(habit.getName(),""))
                .setHeader("Name");
        grid.addColumn(habit -> Objects.requireNonNullElse(habit.getCategory(),""))
                .setHeader("Category");
        grid.addColumn(habit -> Objects.requireNonNullElse(habit.getLocation(),""))
                .setHeader("Location");
        grid.addColumn(habit -> Objects.requireNonNullElse(habit.getEquipment(),""))
                .setHeader("Equipment");
        grid.addColumn(this::formatDuration).setHeader("Duration");//:: säger till den att sen hämta värden från den gettern


        grid.addComponentColumn(habit-> {
            TextField textField = new TextField();
            habitNoteMap.put(habit, textField);
            return textField;
        }).setHeader("Notes");


        grid.addComponentColumn(habit -> {
            NumberField numberField = new NumberField();
            numberField.setStep(0.1);
            habitValueMap.put(habit, numberField);
            return numberField;
        }).setHeader("Value");

        grid.addComponentColumn(habit -> new Button("Completed", event -> {
            completeHabit(habit, habitNoteMap.get(habit).getValue(), habitValueMap.get(habit).getValue());
            updateGrid(comboBox.getValue());
        })).setHeader("Action");


        return grid;

    }


    //spelar nt roll  var tiden sätts egent. d lokalt...
    private void completeHabit(Habit habit, String note, Double value) {
        HabitTracker habitTracker = new HabitTracker();
        habitTracker.setHabit(habit);
        if (note!=null && !note.isBlank()){
            habitTracker.setNote(note);
        }
        if (value != null) {
            habitTracker.setValue(value);
        }
        habitTracker.setCompletedDate(LocalDateTime.now());
        api.habitCompleted(habitTracker);
    }




    public void show(){
        updateGrid(recurrencesImplemented.getFirst());
        setVisible(true);
    }

    public void hide(){
        setVisible(false);
    }


    //dupl men kolla sen hur d funkar med this:: osv
    public String formatDuration(Habit habit) {
        Integer normal = habit.getNormalDurationMinutes();
        Integer min = habit.getMinDurationMinutes();
        Integer max = habit.getMaxDurationMinutes();
        if (normal == null && min ==null && max ==null){
            return "";
        }

        StringBuilder sb = new StringBuilder();
        if (normal != null){
            sb.append(normal).append("m");
        }

        if (min !=null && max == null){
            sb.append(" (").append(min).append("+)");
        }else if(min == null && max != null){
            sb.append(" (up to ").append(max).append(")");
        }else if (min !=null && max != null){
            sb.append(" (").append(min).append("-").append(max).append(")");
        }

        return sb.toString();
    }

}
