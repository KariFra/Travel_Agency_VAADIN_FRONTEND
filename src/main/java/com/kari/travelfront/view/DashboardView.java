package com.kari.travelfront.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route(value="dashboard")
@StyleSheet(value= "/css/style.css")
public class DashboardView extends AppLayout {

    //elements
    private Image logo = new Image("https://image.flaticon.com/icons/svg/2990/2990507.svg",
            "Trip agency logo");
    private Image trips = new Image("https://image.flaticon.com/icons/svg/2964/2964053.svg",
            "trips");
    private Image articles = new Image("https://image.flaticon.com/icons/svg/1907/1907567.svg",
            "Articles");
    private Image opinions = new Image("https://image.flaticon.com/icons/svg/2885/2885574.svg",
            "Opinions");
    private Image users = new Image("https://image.flaticon.com/icons/svg/3003/3003214.svg",
            "Users");
    private Image drinks = new Image("https://image.flaticon.com/icons/svg/3022/3022561.svg",
            "Drinks");
    private Label planLabel = new Label("What do you want to see?");
    private Tabs tabs = new Tabs(new Tab(new RouterLink("Home",MainView.class)));

    //layouts
    private VerticalLayout tripsVertical = new VerticalLayout(trips,new Label("Trips"));
    private VerticalLayout opinionVertical = new VerticalLayout(opinions, new Label("Opinions"));
    private VerticalLayout articleVertical = new VerticalLayout(articles, new Label("Articles"));
    private VerticalLayout userVertical = new VerticalLayout(users, new Label("Users"));
    private VerticalLayout drinkVertical = new VerticalLayout(drinks, new Label("Drinks"));
    private HorizontalLayout horizontalLayout = new HorizontalLayout(tripsVertical,articleVertical,opinionVertical,userVertical,drinkVertical);
    private VerticalLayout verticalLayout = new VerticalLayout(planLabel,horizontalLayout);

    public DashboardView() {
        logo.setHeight("55px");
        addToNavbar(logo, tabs);
        setContent(verticalLayout);
        prepareButtons();

    }

    private void prepareButtons(){
        trips.addClickListener(e -> UI.getCurrent().navigate("admin/trips"));
        articles.addClickListener(e -> UI.getCurrent().navigate("admin/articles"));
        opinions.addClickListener(e -> UI.getCurrent().navigate("admin/opinions"));
        users.addClickListener(e -> UI.getCurrent().navigate("admin/users"));
        drinks.addClickListener(e -> UI.getCurrent().navigate("admin/drinks"));
    }
}
