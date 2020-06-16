package com.kari.travelfront.view.trip;

import com.kari.travelfront.domain.CodesForCurrencies;
import com.kari.travelfront.domain.Trip;
import com.kari.travelfront.service.TripService;
import com.kari.travelfront.view.MainView;
import com.kari.travelfront.view.drink.DrinkView;
import com.kari.travelfront.view.opinion.OpinionsView;
import com.kari.travelfront.view.article.ArticlesView;
import com.kari.travelfront.view.traveller.TravellersView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import java.util.stream.Collectors;


@Route(value="admin/trips")
@StyleSheet(value= "/css/style.css")
public class TripsView extends AppLayout {

    //db
    private Grid<Trip> grid = new Grid<>(Trip.class);
    private TripService service = new TripService();
    private boolean sure;

    //update requirement
    public static String number = "";

    //elements
    private Image logo = new Image("https://image.flaticon.com/icons/svg/2990/2990507.svg", "Trip agency logo");
    private Tabs tabs = new Tabs(new Tab(new RouterLink("Home", MainView.class)),
            new Tab(new RouterLink("Opinions", OpinionsView.class)),
            new Tab(new RouterLink("Articles", ArticlesView.class)),
            new Tab(new RouterLink("Travellers", TravellersView.class)),
            new Tab(new RouterLink("Drinks", DrinkView.class)));

    private Label tripNumberLabel = new Label();
    private Button deleteButton = new Button("Delete this trip");
    private Button updateButton = new Button("Update this trip");
    private Button createButton = new Button("Create new trip");
    private Label content = new Label(
            "Insert number of trip you want to change");
    private Label question = new Label(
            "Do you really want to delete trip no:"+number+"?");
    private Label introduction = new Label("There are " + service.getTrips().size() + " trips in the database. " +
            "so don't hesitate to add more! To make changes to existing data, choose an item by " +
            "clicking on it. Than decide on your action with buttons below.");
    private NativeButton no = new NativeButton("NO! Thanks for asking");
    private NativeButton yes = new NativeButton("Yes");
    private Notification notification = new Notification(content);
    private Notification areYouSure = new Notification(question, yes, no);
    private TextField filter = new TextField();

    //layouts
    private VerticalLayout buttonLayout = new VerticalLayout(tripNumberLabel,deleteButton,updateButton);
    private VerticalLayout verticalLayout = new VerticalLayout(introduction,createButton,filter, grid, buttonLayout);



    public TripsView() {
        serveButtons();
        createGrid();
        setFilter();
        logo.setHeight("55px");
        addToNavbar(logo, tabs);
        setContent(verticalLayout);
        refresh();

    }

    private void refresh() {
        grid.setItems(service.getTrips());
    }

    private void setFilter(){
    filter.setPlaceholder("Filter by City");
    filter.setClearButtonVisible(true);
    filter.setValueChangeMode(ValueChangeMode.EAGER);
    filter.addValueChangeListener(e -> update(filter.getValue()));
}
    private void update(String city) {
        grid.setItems(service.getTrips().stream().filter(item -> item.getCity().contains(city)).collect(Collectors.toList()));
    }

    private void createGrid(){
        grid.setColumns("id", "userId", "price", "city", "url","description","length","food","stars","additions");
        grid.setMaxHeight("300px");
        grid.addItemClickListener(event -> {
            number = event.getItem().getId().toString();
            tripNumberLabel.setText("What you are planning to do with trip no: "+ number+ " ?");
        });
    }

    private void serveButtons(){
        createButton.addClickListener(event -> {
            number = "";
            UI.getCurrent().navigate("trip/change");});
        deleteButton.addClickListener(event -> {
            if(!number.equals("")){
                areYouSure.open();
                while(sure){
                    service.deleteTrip(number);}
            } else{notification.open();
            }
        });
        updateButton.addClickListener(event -> {
            if(!number.equals("")){
                UI.getCurrent().navigate("trip/change");
            } else{notification.open();
            }
        });
        notification.setDuration(3000);
        no.addClickListener(event -> {
            sure = false;
            areYouSure.close();
        });
        yes.addClickListener(event -> {
            sure = true;
            areYouSure.close();
        });
        notification.setPosition(Notification.Position.MIDDLE);
        areYouSure.setPosition(Notification.Position.MIDDLE);
    }
}
