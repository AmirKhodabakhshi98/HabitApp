package com.HabitApp.HabitApp;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.component.page.ColorScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@ColorScheme(ColorScheme.Value.DARK)
public class HabitAppApplication implements AppShellConfigurator {

	public static void main(String[] args) {
		SpringApplication.run(HabitAppApplication.class, args);
	}

}
