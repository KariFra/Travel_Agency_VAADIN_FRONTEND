package com.kari.travelfront.view.opinion;

import com.kari.travelfront.domain.Opinion;
import com.kari.travelfront.service.OpinionService;
import com.kari.travelfront.view.MainView;
import com.kari.travelfront.view.article.ArticlesView;
import com.kari.travelfront.view.drink.DrinkView;
import com.kari.travelfront.view.traveller.TravellersView;
import com.kari.travelfront.view.trip.TripsView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route(value="admin/opinions")
@StyleSheet(value= "/css/style.css")
public class OpinionsView extends AppLayout {

    //db
    private Grid<Opinion> grid = new Grid<>(Opinion.class);
    private OpinionService service = new OpinionService();

    public static String number = "";


    //elements
    private Image logo = new Image("https://image.flaticon.com/icons/svg/2990/2990507.svg", "Trip agency logo");
    private Tabs tabs = new Tabs(new Tab(new RouterLink("Home", MainView.class)),
            new Tab(new RouterLink("Trips", TripsView.class)),
            new Tab(new RouterLink("Articles", ArticlesView.class)),
            new Tab(new RouterLink("Travellers", TravellersView.class)),
            new Tab(new RouterLink("Drinks", DrinkView.class)));
    private Label introduction = new Label("There are " + service.getOpinions().size() + " opinions in the database. " +
            "This is what travellers think about our service.");
    private TextArea messageArea = new TextArea();
    private TextField ratingField = new TextField();
    private TextField userField = new TextField();
    private Button updateButton = new Button("Update");
    private Button deleteButton = new Button("Delete");



    //layouts
    private HorizontalLayout buttonLayout = new HorizontalLayout(updateButton,deleteButton);
    private VerticalLayout verticalLayout = new VerticalLayout(introduction, grid,messageArea,ratingField,userField,buttonLayout);

    public OpinionsView() {
        grid.setColumns("id", "message", "userUrl","rating");
        grid.addItemClickListener(event -> {
                    number = event.getItem().getId().toString();
                    prepareFields();
                });
        updateButton.addClickListener(event -> {
            updateTheOpinion();
            refresh();
        });
        deleteButton.addClickListener(event -> {
            deleteTheOpinion();
            refresh();
        });
        logo.setHeight("55px");
        hideFields();
        addToNavbar(logo, tabs);
        setContent(verticalLayout);
        refresh();
    }

    private void refresh() {
        grid.setItems(service.getOpinions());
    }

    private void deleteTheOpinion(){
        service.deleteOpinion(number);
        hideFields();
    }

    private void updateTheOpinion(){
        service.updateOpinion(buildOpinion());
        hideFields();
    }

    private Opinion buildOpinion(){
        Opinion oldOpinion = service.getOpinion(new Long(number));
        oldOpinion.setMessage(messageArea.getValue());
        oldOpinion.setRating(Integer.parseInt(ratingField.getValue()));
        oldOpinion.setUserUrl(userField.getValue());
        return oldOpinion;
    }

    private void prepareFields(){
        Opinion opinion = service.getOpinion(new Long(number));
        messageArea.setValue(opinion.getMessage());
        ratingField.setValue(String.valueOf(opinion.getRating()));
        userField.setValue(opinion.getUserUrl());
        messageArea.setVisible(true);
        ratingField.setVisible(true);
        userField.setVisible(true);
        updateButton.setVisible(true);
        deleteButton.setVisible(true);
    }

    private void hideFields(){
        messageArea.setVisible(false);
        ratingField.setVisible(false);
        userField.setVisible(false);
        updateButton.setVisible(false);
        deleteButton.setVisible(false);
    }
}
