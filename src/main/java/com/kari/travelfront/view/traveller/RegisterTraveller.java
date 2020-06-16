package com.kari.travelfront.view.traveller;


import com.kari.travelfront.domain.Role;
import com.kari.travelfront.domain.Traveller;
import com.kari.travelfront.service.TravellerService;
import com.kari.travelfront.view.MainView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;


import java.util.ArrayList;
import java.util.List;

@Route(value="register")
@StyleSheet(value= "/css/register.css")
public class RegisterTraveller extends AppLayout {

    //db
    private TravellerService travellerService = new TravellerService();


    //url
    public static String url = "https://avatars.dicebear.com/api/bottts/:ball.svg";

    //elements
    private Image logo = new Image("https://image.flaticon.com/icons/svg/2990/2990507.svg", "Trip agency logo");
    private Tabs tabs = new Tabs(new Tab(new RouterLink("Home", MainView.class)));
    private TextArea firstName = new TextArea("First name");
    private TextArea lastName = new TextArea("Last name");
    private TextArea mail = new TextArea("Mail");
    PasswordField passwordField = new PasswordField();
    private Image ball = new Image("https://avatars.dicebear.com/api/bottts/:ball.svg","ball");
    private Image cat = new Image("https://avatars.dicebear.com/api/bottts/:cat.svg","cat");
    private Image lag = new Image("https://avatars.dicebear.com/api/bottts/:lag.svg","lag");
    private Image lady = new Image("https://avatars.dicebear.com/api/bottts/:lady.svg","lady");
    private Image fur = new Image("https://avatars.dicebear.com/api/bottts/:fur.svg","fur");
    private Button confirm = new Button("Register");
    private Binder<Traveller> binder = new Binder();

    //layout
    private HorizontalLayout horizontalLayout = new HorizontalLayout(ball, cat, lag,lady, fur);
    private VerticalLayout verticalLayout = new VerticalLayout(firstName,lastName,mail,passwordField,
            horizontalLayout, confirm);

    public RegisterTraveller() {
        logo.setHeight("55px");
        addToNavbar(logo, tabs);
        serveButtons();
        setContent(verticalLayout);
        passwordField.setLabel("Password");
        passwordField.setPlaceholder("Enter password");
        requirements();
    }

    private Traveller builder(){
        List<Long> tripsId = new ArrayList<>();
        Traveller traveller = new Traveller();
        traveller.setFirstName(firstName.getValue());
        traveller.setLastName(lastName.getValue());
        traveller.setMail(mail.getValue());
        traveller.setPassword(passwordField.getValue());
        traveller.setRole(Role.USER.name());
        traveller.setAvatarUrl(url);
        traveller.setTripsId(tripsId);
        return traveller;
    }

    private void serveButtons(){
        confirm.addClickListener(event -> {
                    Traveller traveller = builder();
            travellerService.createTraveller(traveller);
                    UI.getCurrent().navigate("login");
                }
        );
        ball.addClickListener(event -> url = ball.getSrc());
        cat.addClickListener(event -> url = cat.getSrc());
        lag.addClickListener(event -> url = lag.getSrc());
        lady.addClickListener(event -> url = lady.getSrc());
        fur.addClickListener(event -> url = fur.getSrc());

    }

    private void requirements(){
        firstName.isRequired();
        lastName.isRequired();
        mail.isRequired();
        passwordField.isRequired();
        binder.forField(mail)
                .withValidator(new EmailValidator(
                        "This doesn't look like a valid email address"))
                .bind(Traveller::getMail, Traveller::setMail);
    }
}
