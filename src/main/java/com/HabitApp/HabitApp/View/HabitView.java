package com.HabitApp.HabitApp.View;
import com.HabitApp.HabitApp.Habit.Habit;
import com.HabitApp.HabitApp.Habit.RecurrenceTypes;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.formlayout.FormLayout;

import java.util.Objects;

@Route("")
public class HabitView extends VerticalLayout {

    private final HabitApiClient api;
    private Grid<Habit> grid;
    private HabitForm editForm;

    private Habit current;

    public HabitView(HabitApiClient api) {
        this.api = api;
        this.grid = initGrid();
        updateGrid();
        this.editForm = new HabitForm();
        editForm.setSaveListener(this::saveHabit);
        editForm.setCancelListener(editForm::hide);
        Button newHabitButton = initNewHabitButton();
        add(newHabitButton, grid, editForm);
    }


    private void updateGrid(){
        grid.setItems(api.getAll());
    }

    private Button initNewHabitButton(){
        Button newHabitButton = new Button("New Habit");
        newHabitButton.addClickListener(event -> {
            editForm.edit(new Habit());
        });
        return newHabitButton;
    }

    private void saveHabit(Habit habit) {
        if (habit.getId() == null) {
            api.create(habit);
        } else {
            api.update(habit);
        }
        updateGrid();
        editForm.setVisible(false);
    }




    private Grid<Habit> initGrid() {
        Grid<Habit> grid = new Grid<>();

        grid.addColumn(habit -> Objects.requireNonNullElse(habit.getName(),""));
        grid.addColumn(habit -> Objects.requireNonNullElse(habit.getCategory(),""));
        grid.addColumn(habit -> Objects.requireNonNullElse(habit.getLocation(),""));
        grid.addColumn(habit -> Objects.requireNonNullElse(habit.getEquipment(),""));

        grid.addColumn(this::formatDuration).setHeader("Duration");//:: säger till den att sen hämta värden från den gettern

        grid.addColumn(this::formatRecurrence).setHeader("Recurrence");

        grid.addComponentColumn(habit -> {
            Button editButton = new Button("Edit");
            editButton.addClickListener(event -> {
                current = habit;
                editForm.edit(current);
            });

            Button deleteButton = new Button("Delete");
            deleteButton.addClickListener(event -> {
                api.delete(habit);
                updateGrid();
            });
            return new HorizontalLayout(editButton, deleteButton);
        }).setHeader("Actions");

        return grid;
    }

    private String formatRecurrence(Habit habit) {
        Integer recurrenceValue = habit.getRecurrenceValue();
        if (recurrenceValue != null){
            return recurrenceValue + "x/" +  habit.getRecurrenceType().getLabel();
        }
        return habit.getRecurrenceType().getLabel();
    }

    private String formatDuration(Habit habit) {
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
