package com.kari.travelfront.view.opinion;

import com.kari.travelfront.LoginView;
import com.kari.travelfront.domain.Opinion;
import com.kari.travelfront.service.OpinionService;
import com.kari.travelfront.service.TravellerService;
import com.kari.travelfront.view.MainView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route(value="leave/opinion")
@StyleSheet(value= "/css/style.css")
public class OpinionCreationView extends AppLayout {

    //db
    private OpinionService service = new OpinionService();
    private TravellerService travellerService = new TravellerService();
    private LoginView loginView = new LoginView();


    //elements
    private Image logo = new Image("https://image.flaticon.com/icons/svg/2990/2990507.svg", "Trip agency logo");
    private Tabs tabs = new Tabs(new Tab(new RouterLink("Home", MainView.class)));
    private TextArea opinionMessage = new TextArea("Message: from 10 to 400 symbols");
    RadioButtonGroup<String> opinionRating = new RadioButtonGroup<>();
    private Button confirm = new Button("Confirm");
    private Button goBack = new Button("Go to opinions");

    //layouts
    private VerticalLayout verticalLayout = new VerticalLayout(opinionMessage,opinionRating,confirm,goBack);

    public OpinionCreationView() {
        logo.setHeight("55px");
        addToNavbar(logo, tabs);
        setContent(verticalLayout);
        opinionMessage.setWidth("400px");
        prepareRadioButtons();
        serveButtons();
    }

    private void prepareRadioButtons(){
        opinionRating.setLabel("Rate our platform");
        opinionRating.setItems("1", "2", "3", "4", "5", "6","7","8","9","10");
        opinionRating.setValue("10");
    }

    private Opinion builder(){
        Opinion opinion = new Opinion();
        opinion.setMessage(opinionMessage.getValue());
        opinion.setRating(Integer.parseInt(opinionRating.getValue()));
        opinion.setUserUrl(travellerService.getTraveller(loginView.number).getAvatarUrl());
        return opinion;
    }

    private void serveButtons(){
        confirm.addClickListener(event -> {
                    Opinion opinion = builder();
                    service.createOpinion(opinion);
                    UI.getCurrent().navigate("opinions");
                }
            );
        goBack.addClickListener(event -> UI.getCurrent().navigate(""));
    }
}
