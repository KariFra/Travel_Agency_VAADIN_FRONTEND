package com.kari.travelfront.view.trip;

import com.kari.travelfront.domain.Trip;
import com.kari.travelfront.domain.Upgrades;
import com.kari.travelfront.service.TripService;
import com.kari.travelfront.view.MainView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import java.util.Arrays;


@Route(value="trip/change")
@StyleSheet(value= "/css/style.css")
public class TripCreationView extends AppLayout {

    //db
    private TripService service = new TripService();
    private TripsView tripsView = new TripsView();


    //elements
    private Image logo = new Image("https://image.flaticon.com/icons/svg/2990/2990507.svg", "Trip agency logo");
    private Tabs tabs = new Tabs(new Tab(new RouterLink("Home", MainView.class)),
            new Tab(new RouterLink("Trips",TripsView.class)));
    private TextField tripPrice = new TextField("Price");
    private TextArea tripDescription = new TextArea("Description: from 10 to 400 symbols");
    private TextField tripCity = new TextField("City");
    private TextField tripUrl = new TextField("Photo: go to https://pixabay.com/pl/");
    private TextField tripLength = new TextField("Length");
    private TextField foodOption = new TextField("Food option");
    private TextField stars = new TextField("Hotel stars");
    private ComboBox<Upgrades> additionOne = new ComboBox<>();
    private ComboBox<Upgrades> additionTwo = new ComboBox<>();
    private ComboBox<Upgrades> additionThree = new ComboBox<>();
    private Button confirm = new Button("Confirm");
    private Button goBack = new Button("Go back");
    private String tripNumber = "";


    public TripCreationView() {
        logo.setHeight("55px");
        addToNavbar(logo, tabs);
        setContent(prepareLayout());
        prepareTextFields();
        tripNumber = tripsView.number;
        if(tripNumber != ""){
            formPreparationInUpdate();
        }
        serveButtons();

    }

    private VerticalLayout prepareLayout(){
        if(tripNumber != ""){
            return new VerticalLayout(tripPrice, tripDescription, tripCity, tripUrl,tripLength,
                    foodOption,stars,additionOne,additionTwo,additionThree,confirm,goBack);
        } else{
            return new VerticalLayout(tripPrice, tripDescription, tripCity, tripUrl,tripLength,
                    additionOne,additionTwo,additionThree,confirm,goBack);
        }

    }

    private void prepareTextFields(){
        tripPrice.setValueChangeMode(ValueChangeMode.EAGER);
        tripPrice.setWidth("400px");
        tripDescription.setValueChangeMode(ValueChangeMode.EAGER);
        tripDescription.setMaxLength(400);
        tripDescription.setMinLength(10);
        tripDescription.setWidth("400px");
        tripCity.setValueChangeMode(ValueChangeMode.EAGER);
        tripCity.setWidth("400px");
        tripUrl.setValueChangeMode(ValueChangeMode.EAGER);
        tripUrl.setWidth("400px");
        tripLength.setValueChangeMode(ValueChangeMode.EAGER);
        tripLength.setWidth("400px");
        additionOne.setWidth("400px");
        additionOne.setLabel("Upgrade");
        additionOne.setItems(Upgrades.values());
        additionTwo.setWidth("400px");
        additionTwo.setLabel("Upgrade");
        additionTwo.setItems(Upgrades.values());
        additionThree.setWidth("400px");
        additionThree.setLabel("Upgrade");
        additionThree.setItems(Upgrades.values());
        foodOption.setWidth("400px");
        foodOption.setValueChangeMode(ValueChangeMode.EAGER);
        stars.setWidth("400px");
        stars.setValueChangeMode(ValueChangeMode.EAGER);
    }

    private void formPreparationInUpdate(){
        Trip trip = service.getTrip(Long.parseLong(tripNumber));
        tripPrice.setValue(trip.getPrice().toString());
        tripDescription.setValue(trip.getDescription());
        tripUrl.setValue(trip.getUrl());
        tripCity.setValue(trip.getCity());
        tripLength.setValue(trip.getLength());
        foodOption.setValue(trip.getFood());
        stars.setValue(String.valueOf(trip.getStars()));
        if(trip.getAdditions().size() == 1){
            additionOne.setValue(trip.getAdditions().get(0));
        } else if (trip.getAdditions().size() == 2){
            additionOne.setValue(trip.getAdditions().get(0));
            additionTwo.setValue(trip.getAdditions().get(1));
        } else if (trip.getAdditions().size() == 3) {
            additionOne.setValue(trip.getAdditions().get(0));
            additionTwo.setValue(trip.getAdditions().get(1));
            additionThree.setValue(trip.getAdditions().get(2));
        } else {
            additionOne.setValue(Upgrades.NONE);
            additionTwo.setValue(Upgrades.NONE);
            additionThree.setValue(Upgrades.NONE);
        }
    }

    private void tripUpdate(){
        Trip trip = service.getTrip(Long.parseLong(tripNumber));
        createTripObject(trip);
        service.updateTrip(trip);
    }

    private Trip builder(){
        Trip trip = new Trip();
        createTripObject(trip);
        return trip;
    }

    private void createTripObject(Trip trip){
        trip.setPrice(new Long(tripPrice.getValue()));
        trip.setDescription(tripDescription.getValue());
        trip.setUrl(tripUrl.getValue());
        trip.setCity(tripCity.getValue());
        trip.setLength(tripLength.getValue());
        trip.setAdditions(Arrays.asList(additionOne.getValue(),
                additionTwo.getValue(),additionThree.getValue()));
    }

    private void serveButtons(){
        confirm.addClickListener(event -> {
            if(tripNumber != ""){
                tripUpdate();
                UI.getCurrent().navigate("admin/trips");
            } else {
                Trip trip = builder();
                service.createTrip(trip);
                UI.getCurrent().navigate("admin/trips");
            }});
        goBack.addClickListener(event -> UI.getCurrent().navigate("admin/trips"));
    }
}
