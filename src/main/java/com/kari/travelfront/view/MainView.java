package com.kari.travelfront.view;

import com.kari.travelfront.LoginView;
import com.kari.travelfront.domain.Drink;
import com.kari.travelfront.operations.CocktailSupport;
import com.kari.travelfront.service.TravellerService;
import com.kari.travelfront.view.traveller.TravellerAccount;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;


import java.time.LocalDate;

@Route
@StyleSheet(value= "/css/style.css")
public class MainView extends AppLayout {

    //cocktailSupport
    private CocktailSupport support = new CocktailSupport();
    private TravellerService service = new TravellerService();
    private Drink drink;

    //choose traveller
    private LoginView loginView = new LoginView();
    private static String name = "Stranger";
    //elements
    private Image logo = new Image("https://image.flaticon.com/icons/svg/2990/2990507.svg", "Trip agency logo");
    private Image trips = new Image("https://cdn.pixabay.com/photo/2019/03/22/19/40/meteora-4074026_1280.jpg",
            "trips");
    private Image articles = new Image("https://cdn.pixabay.com/photo/2019/10/21/12/01/newspapers-4565916_1280.jpg",
            "Articles");
    private Image opinions = new Image("https://cdn.pixabay.com/photo/2015/10/30/16/42/balloon-1014411_1280.jpg",
            "Opinions");
    private Label introLabel = new Label("Hi "+ name);
    private Label dataLabel = new Label("Today it is "+ LocalDate.now().getDayOfWeek().toString() + ", "+
            LocalDate.now().getDayOfMonth() +" "+ LocalDate.now().getMonth());
    private Label planLabel = new Label("It is the best time to plan your trip. Grab a drink and look for a" +
            " new adventure!");
    private Button cocktailButton = new Button("Get cocktail recipe for this week!");
    private Button hide = new Button("Hide");
    private Label cocktailName = new Label();
    private Label recipe = new Label();
    private Tab tab = new Tab(new RouterLink("Account", TravellerAccount.class));
    private Tabs tabs = new Tabs(new Tab(new RouterLink("Login | Register", LoginView.class)),
            new Tab(new RouterLink("Dashboard", DashboardView.class)),tab);


    //layouts
    private VerticalLayout drinksLayout = new VerticalLayout(cocktailName,recipe,hide);
    private VerticalLayout tripsVertical = new VerticalLayout(trips,new Label("Book a trip"));
    private VerticalLayout opinionVertical = new VerticalLayout(opinions, new Label("Read opinions"));
    private VerticalLayout articleVertical = new VerticalLayout(articles, new Label("Get inspired by amazing stories"));
    private HorizontalLayout horizontalLayout = new HorizontalLayout(tripsVertical,articleVertical,opinionVertical);
    private VerticalLayout verticalLayout = new VerticalLayout(introLabel,dataLabel,planLabel,cocktailButton,drinksLayout,horizontalLayout);

    public MainView() {
        logo.setHeight("55px");
        addToNavbar(logo, tabs);
        setContent(verticalLayout);
        setTraveller();
        prepareButtons();
        setVisibility();
    }

    private void setVisibility(){
        hide.setVisible(false);
        cocktailName.setVisible(false);
        recipe.setVisible(false);
    }

    private void prepareButtons(){
        trips.addClickListener(e -> UI.getCurrent().navigate("trips"));
        articles.addClickListener(e -> UI.getCurrent().navigate("articles"));
        opinions.addClickListener(e -> UI.getCurrent().navigate("opinions"));
        cocktailButton.addClickListener(event -> {
            drink = support.getTheDrink();
            cocktailName.setText(drink.getName());
            recipe.setText(drink.getRecipe());
            hide.setVisible(true);
            cocktailName.setVisible(true);
            recipe.setVisible(true);
        });
        hide.addClickListener(event -> {
            hide.setVisible(false);
            cocktailName.setVisible(false);
            recipe.setVisible(false);
        } );
    }

    private String setTraveller(){
        if(loginView.number != null) {
            name = service.getTraveller(loginView.number).getFirstName();
            tab.setVisible(true);
        } else {
            tab.setVisible(false);
        }
        return name;
    }






}
