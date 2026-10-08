package com.HabitApp.HabitApp.View;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.function.Consumer;

public class SideBar extends VerticalLayout {

    public SideBar(Consumer<Pages> onMenuClick) {
        Button homeButton = new Button("Home");
        Button habitsButton = new Button("Habits");

        H2 title = new H2("Habits App");
        title.getStyle().set("color", UIConstants.TEXT_COLOR_PRIMARY);
        homeButton.setWidthFull();
        habitsButton.setWidthFull();

        homeButton.addClickListener(e -> {onMenuClick.accept(Pages.HOME);});
        habitsButton.addClickListener(e -> onMenuClick.accept(Pages.HABITS));

        add(title, homeButton, habitsButton);

        setWidth("clamp(180px, 16vw, 280px");
        setHeight(null);
        setPadding(false);
        setSpacing(true);

        getStyle()
                .set("height", "fit-content")
                .set("padding-top", "clamp(2px, 0.5vh, 8px)")                 // content starts near the top
                .set("padding-bottom", "clamp(30px, 5vh, 64px)")              // takes up the freed space
                .set("padding-left", "clamp(12px, 1.2vw, 24px)")
                .set("padding-right", "clamp(12px, 1.2vw, 24px)")
                .set("gap", "clamp(8px, 1.2vh, 16px)")
                .set("align-self", "center")
                .set("flex-shrink", "0")
                .set("margin", "0 0 0 1.5vw")
                .set("box-sizing", "border-box")
                .set("background-color", UIConstants.BACKGROUND_COLOR)
                .set("border-radius", "12px")
                .set("border", "1px solid " + UIConstants.OUTLINE)
        ;

        title.getStyle().set("margin-top", "5");
    }


}
