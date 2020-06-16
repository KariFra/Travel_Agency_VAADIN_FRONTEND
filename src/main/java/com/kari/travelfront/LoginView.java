package com.kari.travelfront;


import com.kari.travelfront.domain.Traveller;
import com.kari.travelfront.service.TravellerService;
import com.kari.travelfront.view.MainView;
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

@Route(value="login")
@StyleSheet(value= "/css/register.css")
public class LoginView extends AppLayout {

    //db
    private TravellerService service = new TravellerService();
    private Traveller activeTraveller;
    public static Long number;

    //elements
    private Image logo = new Image("https://image.flaticon.com/icons/svg/2990/2990507.svg", "Trip agency logo");
    private Tabs tabs = new Tabs(new Tab(new RouterLink("Home", MainView.class)));
    private Label introduction = new Label("Login as one of the existing travellers or register as a new one");
    private Button buttonRegister = new Button("Register new Traveller");

    //layout
    private VerticalLayout layout = new VerticalLayout(introduction);

    public LoginView() {
        logo.setHeight("55px");
        addToNavbar(logo, tabs);
        serveButtons();
        fillMainLayout();
    }

    private void serveButtons(){
        buttonRegister.addClickListener(event -> UI.getCurrent().navigate("register"));
    }

    private VerticalLayout addManyTravellers(){
        VerticalLayout layout = new VerticalLayout();
        for (int n =0; n<service.getTravellers().size(); n++){
            layout.add(fillTheFields(service.getTravellers().get(n)));
        }
        return layout;
    }

    private VerticalLayout fillTheFields(Traveller traveller){
        return new VerticalLayout(new HorizontalLayout(new VerticalLayout(new Label(traveller.getFirstName()),
                new Label(traveller.getLastName())),new Image(traveller.getAvatarUrl(),"photo")),
                new Button("Choose this traveller",event -> {
                    number = traveller.getId();
                    UI.getCurrent().navigate("");
                } ));
    }

    private void fillMainLayout(){
        int size = service.getTravellers().size();
        if(size==0){
            setContent(new Label("There are no articles"));
        } else{
            setContent(addManyTravellers());
        }
    }
}
