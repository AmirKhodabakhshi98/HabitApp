package com.HabitApp.HabitApp.View;

import com.HabitApp.HabitApp.Habit.Habit;
import com.HabitApp.HabitApp.View.HabitApiClient;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route("")
public class HabitView extends VerticalLayout {

    private final HabitApiClient api;
    private final Grid<Habit> grid = new Grid<>(Habit.class, false);
    private final Binder<Habit> binder = new Binder<>(Habit.class);

    private final TextField name = new TextField("Name");
    private final TextField category = new TextField("Category");
    private final TextField location = new TextField("Location");
    private final TextField equipment = new TextField("Equipment");
    private final IntegerField minDuration = new IntegerField("Min duration (min)");
    private final IntegerField normalDuration = new IntegerField("Normal duration (min)");
    private final IntegerField maxDuration = new IntegerField("Max duration (min)");
    private final ComboBox<String> recurrenceType = new ComboBox<>("Recurrence type");
    private final IntegerField recurrenceValue = new IntegerField("Recurrence value");

    private Habit current = new Habit();
    private boolean editingExisting = false;

    public HabitView(HabitApiClient api) {
        this.api = api;

        configureGrid();
        configureForm();

        Button save = new Button("Save", e -> save());
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button delete = new Button("Delete", e -> delete());
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        Button clear = new Button("New", e -> {
            grid.deselectAll();
            edit(null);
        });

        FormLayout form = new FormLayout(
                name, category, location, equipment,
                minDuration, normalDuration, maxDuration,
                recurrenceType, recurrenceValue);
        form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("600px", 3));

        add(new H2("Habits"), grid, form, new HorizontalLayout(save, delete, clear));
        setSizeFull();

        refresh();
        edit(null);
    }

    private void configureGrid() {
        grid.addColumn(Habit::getName).setHeader("Name").setSortable(true);
        grid.addColumn(Habit::getCategory).setHeader("Category").setSortable(true);
        grid.addColumn(Habit::getLocation).setHeader("Location");
        grid.addColumn(Habit::getEquipment).setHeader("Equipment");
        grid.addColumn(h -> h.getNormalDurationMinutes() + " min").setHeader("Duration");
        grid.addColumn(h -> h.getRecurrenceType() + " x" + h.getRecurrenceValue())
                .setHeader("Recurrence");
        grid.asSingleSelect().addValueChangeListener(e -> edit(e.getValue()));
        grid.setHeight("320px");
    }

    private void configureForm() {
        recurrenceType.setItems("DAILY", "WEEKLY", "MONTHLY"); // adjust to your values
        recurrenceType.setAllowCustomValue(false);

        for (IntegerField f : List.of(minDuration, normalDuration, maxDuration, recurrenceValue)) {
            f.setMin(0);
            f.setStepButtonsVisible(true);
        }

        binder.forField(name)
                .asRequired("Name is required")
                .bind(Habit::getName, Habit::setName);
        binder.forField(category).bind(Habit::getCategory, Habit::setCategory);
        binder.forField(location).bind(Habit::getLocation, Habit::setLocation);
        binder.forField(equipment).bind(Habit::getEquipment, Habit::setEquipment);
        binder.forField(minDuration).withNullRepresentation(0)
                .bind(Habit::getMinDurationMinutes, Habit::setMinDurationMinutes);
        binder.forField(normalDuration).withNullRepresentation(0)
                .bind(Habit::getNormalDurationMinutes, Habit::setNormalDurationMinutes);
        binder.forField(maxDuration).withNullRepresentation(0)
                .bind(Habit::getMaxDurationMinutes, Habit::setMaxDurationMinutes);
        binder.forField(recurrenceType).bind(Habit::getRecurrenceType, Habit::setRecurrenceType);
        binder.forField(recurrenceValue).withNullRepresentation(0)
                .bind(Habit::getRecurrenceValue, Habit::setRecurrenceValue);
    }

    private void save() {
        try {
            binder.writeBean(current);
            if (editingExisting) api.update(current);
            else api.create(current);
            Notification.show("Saved");
            refresh();
            grid.deselectAll();
            edit(null);
        } catch (ValidationException ex) {
            Notification.show("Please fix the highlighted fields");
        } catch (Exception ex) {
            Notification.show("Could not save: " + ex.getMessage());
        }
    }

    private void delete() {
        if (!editingExisting) return;
        try {
            api.delete(current.getName());
            Notification.show("Deleted");
            refresh();
            grid.deselectAll();
            edit(null);
        } catch (Exception ex) {
            Notification.show("Could not delete: " + ex.getMessage());
        }
    }

    private void edit(Habit h) {
        editingExisting = (h != null);
        current = editingExisting ? h : new Habit();
      //  name.setReadOnly(editingExisting); // name identifies the habit in your API
        binder.readBean(current);
    }

    private void refresh() {
        grid.setItems(api.findAll());
    }
}