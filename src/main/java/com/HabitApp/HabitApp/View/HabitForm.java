package com.HabitApp.HabitApp.View;

import com.HabitApp.HabitApp.Habit.Habit;
import com.HabitApp.HabitApp.Habit.RecurrenceTypes;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

public class HabitForm extends FormLayout {

    private final TextField nameCol = new TextField("Name"); //flytta till init sen, samma med andra new kolla o dela
    private final TextField categoryCol = new TextField("Category");
    private final TextField locationCol = new TextField("Location");
    private final TextField equipmentCol = new TextField("Equipment");

    private final IntegerField durationMinCol =  new IntegerField("Minimum Duration");
    private final IntegerField durationMaxCol =  new IntegerField("Maximum Duration");
    private final IntegerField durationCol = new IntegerField("Duration");

    private final IntegerField recurrenceValueCol = new IntegerField("Recurrence Value");
    private final Select <RecurrenceTypes> recurrenceTypeCol = new Select<>("Recurrence Type");

    private Button saveButton;// = new Button("Save");
    private Button cancelButton;// = new Button("Save");
    private Consumer<Habit> saveListener;
    private Runnable cancelListener;
    private Habit habit;

    public HabitForm(){
        setResponsiveSteps(
                new FormLayout.ResponsiveStep("0px", 1),
                new FormLayout.ResponsiveStep("500px", 2),
                new FormLayout.ResponsiveStep("800px", 3),
                new FormLayout.ResponsiveStep("1200px", 4)
        );
        hide();

        nameCol.setRequired(true);
        nameCol.setErrorMessage("Please enter a name");
        recurrenceTypeCol.setItems(RecurrenceTypes.values());
        recurrenceTypeCol.setItemLabelGenerator(RecurrenceTypes::getLabel);
        saveButton = initSaveButton();
        cancelButton = initCancelButton();
        add(nameCol,categoryCol,locationCol,equipmentCol,
                durationMinCol,durationMaxCol,durationCol,
                recurrenceValueCol, recurrenceTypeCol,
                cancelButton, saveButton);
    }


    private void saveHabit(){
        habit.setName(nameCol.getValue());
        habit.setCategory(categoryCol.getValue());
        habit.setLocation(locationCol.getValue());
        habit.setEquipment(equipmentCol.getValue());

        habit.setMinDurationMinutes(durationMinCol.getValue());
        habit.setMaxDurationMinutes(durationMaxCol.getValue());
        habit.setNormalDurationMinutes(durationCol.getValue());

        habit.setRecurrenceValue(recurrenceValueCol.getValue());
        habit.setRecurrenceType(recurrenceTypeCol.getValue());

    }


    public void edit(Habit habit){
        this.habit = habit;
        clear();

        setTextValue(nameCol,habit.getName());
        setTextValue(categoryCol,habit.getCategory());
        setTextValue(locationCol,habit.getLocation());
        setTextValue(equipmentCol,habit.getEquipment());
        setIntegerValue(durationCol, habit.getNormalDurationMinutes());
        setIntegerValue(durationMinCol, habit.getMinDurationMinutes());
        setIntegerValue(durationMaxCol, habit.getMaxDurationMinutes());
        setIntegerValue(recurrenceValueCol, habit.getRecurrenceValue());
        recurrenceTypeCol.setValue(
                habit.getRecurrenceType() == null
                ? RecurrenceTypes.DEFAULT
                : habit.getRecurrenceType());

        show();

    }
    

    private boolean validate(){
        clearErrors();

        if (nameCol.isEmpty()){
            nameCol.setInvalid(true);
            return false;
        }

        Integer min = durationMinCol.getValue();
        Integer duration = durationCol.getValue();
        Integer max = durationMaxCol.getValue();

        if (min != null && duration != null && min > duration){
            durationCol.setInvalid(true);
            durationCol.setErrorMessage("Duration cannot be less than minimum");
            return false;
        }
        if (duration != null && max != null && duration > max){
            durationCol.setInvalid(true);
            durationCol.setErrorMessage("Duration cannot be greater than maximum");
            return false;
        }

        if (duration == null && max!= null && min!= null){
            if (max<=min){
                durationMinCol.setInvalid(true);
                durationMaxCol.setInvalid(true);
                durationMinCol.setErrorMessage("min time must be less than max");
            }
            return false;
        }

        //bara whatever får va null value
        if (recurrenceTypeCol.getValue() != RecurrenceTypes.DEFAULT && recurrenceValueCol.getValue() == null){
            recurrenceValueCol.setInvalid(true);
            recurrenceValueCol.setErrorMessage("Only \"" + RecurrenceTypes.DEFAULT.getLabel() + "\" " +
                    "can have no recurrence value");
            return false;
        }
        return true;
    }


    private Button initSaveButton() {
        Button saveButton = new Button("Save");
        saveButton.addClickListener
                (e -> {
                    if (validate()) {
                        saveHabit();
                        saveListener.accept(habit);
                        hide();
                    }
                });
        return saveButton;
    }

    private Button initCancelButton(){
        Button cancelButton = new Button("Cancel");
        cancelButton.addClickListener(event -> {
            habit = null;
            hide();
            cancelListener.run();
        });
        return cancelButton;
    }

    public void setSaveListener(Consumer<Habit> saveListener) {
        this.saveListener = saveListener;
    }

    public void setCancelListener(Runnable cancelListener) {
        this.cancelListener = cancelListener;
    }

    private void clearErrors(){
        nameCol.setInvalid(false);
        categoryCol.setInvalid(false);
        locationCol.setInvalid(false);
        equipmentCol.setInvalid(false);

        durationMinCol.setInvalid(false);
        durationMaxCol.setInvalid(false);
        durationCol.setInvalid(false);
        recurrenceValueCol.setInvalid(false);
    }

    private void clear(){
        clearErrors();
        nameCol.clear();
        categoryCol.clear();
        locationCol.clear();
        equipmentCol.clear();

        durationMinCol.clear();
        durationMaxCol.clear();
        durationCol.clear();

        recurrenceValueCol.clear();
        recurrenceTypeCol.setValue(RecurrenceTypes.DEFAULT);
    }

    private void show(){//venne ba för symmetrin förmodligen ta bort sen
        setVisible(true);
    }

    public void hide(){
        //   clear(); //potentiellt redundant
        setVisible(false);
    }

    private void setTextValue(TextField field, String value){
        if (value!=null){field.setValue(value);}
    }
    private void setIntegerValue(IntegerField field, Integer value){
        if (value!=null){field.setValue(value);}
    }
    /*

     */


}
