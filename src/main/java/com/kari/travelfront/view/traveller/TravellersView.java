package com.kari.travelfront.view.traveller;


import com.kari.travelfront.domain.Opinion;
import com.kari.travelfront.domain.Traveller;
import com.kari.travelfront.domain.Trip;
import com.kari.travelfront.service.TravellerService;
import com.kari.travelfront.view.MainView;
import com.kari.travelfront.view.article.ArticlesView;
import com.kari.travelfront.view.drink.DrinkView;
import com.kari.travelfront.view.opinion.OpinionsView;
import com.kari.travelfront.view.trip.TripsView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;


@Route(value="admin/users")
@StyleSheet(value= "/css/style.css")
public class TravellersView extends AppLayout {

    //db
    private Grid<Traveller> grid = new Grid<>(Traveller.class);
    private TravellerService service = new TravellerService();

    //elements
    private Image logo = new Image("https://image.flaticon.com/icons/svg/2990/2990507.svg", "Trip agency logo");
    private Tabs tabs = new Tabs(new Tab(new RouterLink("Home", MainView.class)),
            new Tab(new RouterLink("Opinions", OpinionsView.class)),
            new Tab(new RouterLink("Articles", ArticlesView.class)),
            new Tab(new RouterLink("Trips", TripsView.class)),
            new Tab(new RouterLink("Drinks", DrinkView.class)));
    private Label introduction = new Label("There are " + service.getTravellers().size() + " users in the database.");

    //layout
    private VerticalLayout verticalLayout = new VerticalLayout(introduction,grid);

    public TravellersView() {
        createGrid();
        logo.setHeight("55px");
        addToNavbar(logo, tabs);
        setContent(verticalLayout);
        refresh();
    }

    public void refresh() {
        grid.setItems(service.getTravellers());
    }


    private void createGrid(){
        grid.setColumns("id", "firstName", "lastName","mail","password","role","avatarUrl","tripsId");
        grid.setMaxHeight("300px");

    }
}
