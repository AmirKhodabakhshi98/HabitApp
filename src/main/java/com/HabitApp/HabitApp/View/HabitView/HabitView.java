package com.HabitApp.HabitApp.View.HabitView;
import com.HabitApp.HabitApp.ExternalApi.Quote;
import com.HabitApp.HabitApp.ExternalApi.ExternalApiService;
import com.HabitApp.HabitApp.Habit.Habit;
import com.HabitApp.HabitApp.View.UIConstants;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class HabitView extends VerticalLayout {

    private final HabitApiClient api;
    private Grid<Habit> grid;
    private HabitForm editForm;

    private Habit current;
    private final String componentBackgroundColor = "black";


    public HabitView(HabitApiClient api) {
        this.api = api;
        this.grid = initGrid();
        setWidthFull();
        setHeightFull();
        initEditForm();

        Button newHabitButton = initNewHabitButton();
        getStyle().set("border", "1px solid " + UIConstants.OUTLINE);
        add(newHabitButton, grid, editForm);
    }

    public void show(){
        updateGrid();
        setVisible(true);
    }

    public void hide(){
        setVisible(false);
    }

    private void initEditForm(){
        this.editForm = new HabitForm();
     //   editForm.getStyle().set("background-color", "white");
        editForm.setSaveListener(this::saveHabit);
        editForm.setCancelListener(editForm::hide);
    }



    private Button initNewHabitButton(){
        Button newHabitButton = new Button("New Habit");
        newHabitButton.addClickListener(event -> {
            editForm.edit(new Habit());
        });
       // newHabitButton.addThemeVariants();
        return newHabitButton;
    }

    private Grid<Habit> initGrid() {
        Grid<Habit> grid = new Grid<>();

        grid.addColumn(habit -> Objects.requireNonNullElse(habit.getName(),""))
                .setHeader("Name");
        grid.addColumn(habit -> Objects.requireNonNullElse(habit.getCategory(),""))
                .setHeader("Category");
        grid.addColumn(habit -> Objects.requireNonNullElse(habit.getLocation(),""))
                .setHeader("Location");
        grid.addColumn(habit -> Objects.requireNonNullElse(habit.getEquipment(),""))
                .setHeader("Equipment");

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


    private void updateGrid(){
        grid.setItems(api.getAll());
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
