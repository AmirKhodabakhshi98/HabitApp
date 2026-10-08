package com.HabitApp.HabitApp.View;
import com.HabitApp.HabitApp.ExternalApi.Quote;
import com.HabitApp.HabitApp.ExternalApi.externalApiService;
import com.HabitApp.HabitApp.Habit.Habit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.Objects;

@Route("")
public class HabitView extends VerticalLayout {

    private final HabitApiClient api;
    private final externalApiService externalApiService;
    private Grid<Habit> grid;
    private HabitForm editForm;
    private Quote quote; //ev spara i db? hämta dagens om d nt finns?
    private Habit current;
    String nasaImageUrl;

    //quote och img borde sen decentraliseras uppåt till nån main view när jag gör fler sidor

    public HabitView(HabitApiClient api, externalApiService externalApiService) {
        this.api = api;
        this.grid = initGrid();

        this.externalApiService = externalApiService;
        quote = externalApiService.getTodaysZenQuote();
        nasaImageUrl = externalApiService.getNasaImage().url(); // adjust to your ExternalImage accessor
        if (!nasaImageUrl.isBlank()) {
            getStyle()
                    .set("background-image", "url('" + nasaImageUrl + "')")
                    .set("background-size", "cover")
                    .set("background-position", "center")
                    .set("background-repeat", "no-repeat");
        }
        updateGrid();


        this.editForm = new HabitForm();
        editForm.setSaveListener(this::saveHabit);
        editForm.setCancelListener(editForm::hide);

        Button newHabitButton = initNewHabitButton();

        VerticalLayout quoteLayout = getQuoteLayout();
        quoteLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        add(quoteLayout, newHabitButton, grid, editForm);
    }




    private Button initNewHabitButton(){
        Button newHabitButton = new Button("New Habit");
        newHabitButton.addClickListener(event -> {
            editForm.edit(new Habit());
        });
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

    private VerticalLayout getQuoteLayout(){
        H3 quoteText = new H3(quote.quote());
        quoteText.getStyle().set("font-style", "italic");
        Span authorName = new Span("- "+quote.author());
        return new VerticalLayout(quoteText, authorName);

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
