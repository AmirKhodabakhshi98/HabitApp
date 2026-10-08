package com.HabitApp.HabitApp.View;


import com.HabitApp.HabitApp.ExternalApi.ExternalApiService;
import com.HabitApp.HabitApp.ExternalApi.Quote;
import com.HabitApp.HabitApp.View.HabitView.HabitView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@Route("")
public class MainView extends FlexLayout {


    private final ExternalApiService externalApiService;
    private final Div contentArea;
    private Quote quote; //ev spara i db? hämta dagens om d nt finns?
    private String nasaImageUrl;
    private SideBar sideBar;
    private HabitView habitView;
    private VerticalLayout quoteLayout;
    private double backgroundOverlayAlpha = 0.25;

    private VaadinSession vaadinSession;

    public MainView(ExternalApiService externalApiService, HabitView habitView) {
        this.externalApiService = externalApiService; //flytta sen så den inte hämtas hela tiden(refresh,restart)

        //quote = externalApiService.getTodaysZenQuote();
        quote = externalApiService.getRandomZenQuote();
       // nasaImageUrl = externalApiService.getTodaysNasaImage().url();
        nasaImageUrl = externalApiService.getRandomNasaImage().url();
        setBackgroundImage(nasaImageUrl);


        this.habitView  = habitView;

        contentArea = initContentArea();
        reset();
        quoteLayout = getQuoteLayout();
        sideBar = initSideBar();

        add(quoteLayout,  sideBar, contentArea);
        setWidthFull();
        setHeight("100vh");
        getStyle().set("box-sizing", "border-box");

    }

    private Div initContentArea() {
        Div contentArea = new Div();
        contentArea.getStyle()
                .set("display", "flex")
                .set("justify-content", "center")
                .set("align-items", "center")
                .set("flex", "1")
                .set("min-width", "0")
                .set("align-self", "stretch")
                .set("margin", "clamp(110px, 15vh, 180px) clamp(16px, 4vw, 60px) clamp(16px, 4vh, 50px)")
                .set("box-sizing", "border-box")
                .set("overflow", "auto")          // låta skrolla inne
                .set("background-color", UIConstants.BACKGROUND_COLOR)
        ;
        return contentArea;
    }

    private void setContentArea(VerticalLayout content){
            contentArea.add(content);
            contentArea.setVisible(true);
    }

    private SideBar initSideBar() {
        return new SideBar(page -> {
            reset();
            System.err.println("reset");
           switch (page) {

               case HOME:
                   System.err.println("home");
                   break;
               case HABITS:
                   System.err.println("habits");
                   setContentArea(habitView);
                   habitView.show();
                   break;
           }

        });
    }

    private void reset(){
        contentArea.removeAll();
        contentArea.setVisible(false);
        habitView.hide();
    }


    private VerticalLayout getQuoteLayout(){
        H3 quoteText = new H3(quote.quote());
        quoteText.getStyle().set("font-style", "italic");
            quoteText.getStyle().set("color", UIConstants.TEXT_COLOR_PRIMARY);
        quoteText.getStyle().set("text-align", "center");

        Span authorName = new Span("- "+quote.author());
        authorName.getStyle().set("color", UIConstants.TEXT_COLOR_SECONDARY);

        VerticalLayout verticalLayout = new VerticalLayout(quoteText, authorName);
        verticalLayout.setWidth("fit-content");
        verticalLayout.setPadding(true);
           verticalLayout.getStyle().set("background-color", UIConstants.BACKGROUND_COLOR);
        verticalLayout.setAlignItems(Alignment.CENTER);
        verticalLayout.getStyle()
                .set("position", "absolute")
                .set("top", "20px")
                .set("left", "50%")
                .set("transform", "translateX(-50%)")
                .set("border", "1px solid " + UIConstants.OUTLINE)
        ;
        return verticalLayout;
    }



    //black default
    private void setBackgroundImage(String url) {
        String overlay = "rgba(0,0,0," + backgroundOverlayAlpha +")";

        if (!url.isBlank()) {
            UI.getCurrent().getElement().getStyle()
                    .set("background-image",
                            "linear-gradient(" + overlay + ", " + overlay + "), url('" + url + "')")
                    .set("background-size", "cover")
                    .set("background-position", "center")
                    .set("background-attachment", "fixed")
                    .set("background-repeat", "no-repeat");
        }else {
            UI.getCurrent().getElement().getStyle()
                    .set("background-color", UIConstants.BACKGROUND_COLOR)
                    .set("min-height", "100vh");
        }

    }


}
