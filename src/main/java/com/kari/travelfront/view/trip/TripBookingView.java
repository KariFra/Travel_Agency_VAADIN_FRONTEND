package com.kari.travelfront.view.trip;

import com.kari.travelfront.LoginView;
import com.kari.travelfront.domain.*;
import com.kari.travelfront.service.CurrencyService;
import com.kari.travelfront.service.TravellerService;
import com.kari.travelfront.service.TripService;
import com.kari.travelfront.view.MainView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;


@Route(value="booking")
@StyleSheet(value= "/css/style.css")
public class TripBookingView extends AppLayout {

    //db
    private TripService service = new TripService();
    private CurrencyService currencyService = new CurrencyService();
    private TravellerService travellerService = new TravellerService();

    //data
    private OfficialTripsView tripsView = new OfficialTripsView();
    private Trip chosenTrip;
    private Long actualPrice;
    private List<Upgrades> additionsList;
    private DecimalFormat df = new DecimalFormat("#.##");
   private LoginView loginView = new LoginView();
    private Traveller traveller = travellerService.getTraveller(loginView.number);
    private boolean bookTrip;


    //elements
    private Image logo = new Image("https://image.flaticon.com/icons/svg/2990/2990507.svg", "Trip agency logo");
    private Tabs tabs = new Tabs(new Tab(new RouterLink("Home", MainView.class)),
            new Tab(new RouterLink("Trips",TripsView.class)));
    private Button update = new Button("Pimp out your trip");
    private Button book = new Button("Finish your booking");
    private Button back = new Button("Go back");
    private Button calculateCurrency = new Button("Change");
    private Button priceCheck = new Button("Calculate price");
    private Select<CodesForCurrencies> placeholderSelect = new Select<>(CodesForCurrencies.values());
    private Label changedCurrency = new Label();
    private Label payment = new Label();
    private Label finalPayment = new Label();
    private Label polishCurrencyCode = new Label("PLN");
    private Label currencyCode = new Label();
    private Label city = new Label();
    private Label description = new Label();
    private Label length = new Label();
    private Label lengthDescription = new Label("Trip time: ");
    private Label food = new Label();
    private Label foodDescription = new Label("Food option: ");
    private Label additions = new Label();
    private Label additionsDescription = new Label("Additional options: ");
    private Label additionsAgreeOption = new Label("I want to add additional options to my trip: ");
    private Label stars = new Label();
    private Label starsDescription = new Label("Hotel standard: ");
    private Label priceDescription = new Label("Your total price: ");
    private Label starsChangeOption = new Label("Change hotel standard: ");
    private ComboBox<String> additionsAcceptance = new ComboBox<>();
    private ComboBox<Integer> starsUpdate = new ComboBox<>();


    //layout
    private HorizontalLayout starsLayoutDisplay = new HorizontalLayout(starsDescription,stars);
    private HorizontalLayout starsLayoutUpdate = new HorizontalLayout(starsChangeOption,starsUpdate);
    private HorizontalLayout additionsLayoutDisplay = new HorizontalLayout(additionsDescription,additions);
    private HorizontalLayout additionsLayoutUpdate = new HorizontalLayout(additionsAgreeOption,additionsAcceptance);
    private HorizontalLayout foodLayoutDisplay = new HorizontalLayout(foodDescription,food);
    private HorizontalLayout timeLayoutDisplay = new HorizontalLayout(lengthDescription,length);
    private HorizontalLayout priceLayoutDisplay = new HorizontalLayout(priceDescription,finalPayment);
    private HorizontalLayout currencyLayout = new HorizontalLayout(payment,polishCurrencyCode,placeholderSelect,
            changedCurrency,currencyCode,calculateCurrency);

    public TripBookingView() {
        logo.setHeight("55px");
        addToNavbar(logo, tabs);
        prepareSettings();
        prepareFields(chosenTrip);
        setContent(prepareFinalLayout(chosenTrip));
        serveButtons();
    }

    private void serveButtons(){
        calculateCurrency.addClickListener(event -> {
            countCurrencyChange();
            currencyCode.setText(placeholderSelect.getValue().name());
        });
        update.addClickListener(event -> {
            bookTrip = true;
            setContent(prepareFinalLayout(chosenTrip));
            bookTrip = false;
        });
        priceCheck.addClickListener(event -> {
            finalPayment.setText(additionsPricing().toString());
            book.setVisible(true);
        });
        back.addClickListener(event -> UI.getCurrent().navigate("trips"));
        book.addClickListener(event -> {
            tripUpdate();
            service.updateTrip(chosenTrip);
            UI.getCurrent().navigate("account");
        });
    }

    private void prepareFields(Trip trip){
        starsUpdate.setItems(3,4,5);
        additionsAcceptance.setItems("YES","NO");
        payment.setText(actualPrice.toString());
        city.setText(trip.getCity());
        description.setText(trip.getDescription());
        length.setText(trip.getLength());
        food.setText(trip.getFood());
        additions.setText(trip.getAdditions().stream().map(element -> element.toString())
                .collect(Collectors.joining(",")));
        stars.setText(String.valueOf(trip.getStars()));
        additionsAcceptance.isRequired();
        starsUpdate.isRequired();
    }

    private VerticalLayout prepareFinalLayout(Trip trip){
        while(bookTrip){
            additionsAcceptance.setValue("YES");
            starsUpdate.setValue(4);
            return new VerticalLayout(back,new Image(trip.getUrl(),"photo"),city, description,timeLayoutDisplay,
                    foodLayoutDisplay,additionsLayoutDisplay,starsLayoutDisplay,currencyLayout,starsLayoutUpdate,
                    additionsLayoutUpdate,priceLayoutDisplay,priceCheck, book);
        }
        return new VerticalLayout(back,new Image(trip.getUrl(),"photo"),city, description,timeLayoutDisplay,
                foodLayoutDisplay,additionsLayoutDisplay,starsLayoutDisplay,currencyLayout,update);
    }

    private void prepareSettings(){
        chosenTrip = service.getTrip(tripsView.tripId);
        actualPrice = chosenTrip.getPrice();
        additionsList = chosenTrip.getAdditions();
        placeholderSelect.setPlaceholder("Choose currency");
        book.setVisible(false);
    }

    private void countCurrencyChange(){
        CodesForCurrencies currency = placeholderSelect.getValue();
        String code = currency.name();
        String course = currencyService.getAmount(code).getRate();
        Double courseDouble = new Double(course);
        Double actualPriceDouble = new Double(actualPrice);
        Double sum = actualPriceDouble/courseDouble;
        changedCurrency.setText(df.format(sum));
    }

    private Long additionsPricing(){
        if(starsUpdate.getValue() > chosenTrip.getStars()){
            actualPrice = chosenTrip.getPrice() + 400;
        } else if(starsUpdate.getValue() < chosenTrip.getStars()){
            actualPrice = chosenTrip.getPrice() - 400;
        }
        if (additionsAcceptance.getValue().equals("YES")){
            if(additionsList.contains(Upgrades.WINERY_TRIP)){
                actualPrice += 500;
            } else if(additionsList.contains(Upgrades.DRIVER)){
                actualPrice += 200;
            } else if(additionsList.contains(Upgrades.NETFLIX_CARD)){
                actualPrice += 50;
            }else if(additionsList.contains(Upgrades.PROBLEM_SOLVER)){
                actualPrice += 1000;
            }
        }
        return actualPrice;
    }


    private void tripUpdate(){
        Trip trip = service.getTrip(chosenTrip.getId());
        createTripObject(trip,traveller);
        service.updateTrip(trip);
    }

    private void createTripObject(Trip trip,Traveller traveller){
        trip.setUserId(traveller.getId());
        trip.setPrice(actualPrice);
        trip.setStars(starsUpdate.getValue());
    }
}
