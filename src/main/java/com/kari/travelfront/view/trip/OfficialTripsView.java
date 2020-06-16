package com.kari.travelfront.view.trip;


import com.kari.travelfront.domain.Article;
import com.kari.travelfront.domain.Trip;
import com.kari.travelfront.service.TripService;
import com.kari.travelfront.view.MainView;
import com.kari.travelfront.view.article.OfficialArticlesView;
import com.kari.travelfront.view.opinion.OfficialOpinionView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import java.util.List;

@Route(value="trips")
@StyleSheet(value= "/css/style.css")
public class OfficialTripsView extends AppLayout {

    //db
    private TripService service = new TripService();

    //elements
    private Image logo = new Image("https://image.flaticon.com/icons/svg/2990/2990507.svg", "Trip agency logo");
    private Tabs tabs = new Tabs(new Tab(new RouterLink("Home", MainView.class)),
            new Tab(new RouterLink("Articles", OfficialArticlesView.class)),
    new Tab(new RouterLink("Opinions", OfficialOpinionView.class)));
    private List<Trip> listOfTrips = service.getTrips();
    public static Long tripId;

    public OfficialTripsView() {
        logo.setHeight("55px");
        addToNavbar(logo, tabs);
        fillMainLayout();
    }

    private HorizontalLayout fillTheFields(Trip trip){
        return new HorizontalLayout(new VerticalLayout(new Label(trip.getCity()),new Image(trip.getUrl(),"photo")),
                new VerticalLayout(new Label(trip.getPrice().toString()),new Label(trip.getLength()),
                        new Label(trip.getDescription()),new Button("Book this trip",event -> {
                            tripId = trip.getId();
                            UI.getCurrent().navigate("booking");
                })));
    }

    private VerticalLayout addManyTrips(){
        VerticalLayout layout = new VerticalLayout();
        for (int n =0; n<listOfTrips.size(); n++){
            layout.add(fillTheFields(listOfTrips.get(n)));
        }
        return layout;
    }

    private void fillMainLayout(){
        int size = listOfTrips.size();
        if(size==0){
            setContent(new Label("There are no trips"));
        } else{
            setContent(addManyTrips());
        }
    }
}
