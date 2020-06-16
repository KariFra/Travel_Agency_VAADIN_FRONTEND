package com.kari.travelfront.view.traveller;

import com.kari.travelfront.LoginView;
import com.kari.travelfront.domain.Traveller;
import com.kari.travelfront.operations.Complaint;
import com.kari.travelfront.service.TravellerService;
import com.kari.travelfront.service.TripService;
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
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import java.util.stream.Collectors;

@Route(value="account")
@StyleSheet(value= "/css/register.css")
public class TravellerAccount extends AppLayout {

    //db
    private TravellerService service = new TravellerService();
    private TripService tripService = new TripService();
    private LoginView loginView = new LoginView();
    private Traveller activeTraveller = service.getTraveller(loginView.number);

    //complaint
    private Complaint complaint = new Complaint();


    //elements
    private Image logo = new Image("https://image.flaticon.com/icons/svg/2990/2990507.svg", "Trip agency logo");
    private Tabs tabs = new Tabs(new Tab(new RouterLink("Home", MainView.class)));
    private Label introduction = new Label("This is your traveller account. What you want to do?");
    private Button updateButton = new Button("Update your data");
    private Button deleteButton = new Button("Delete your account");
    private Button passwordButton = new Button("Change your password");
    private Button saveButton = new Button("Save");
    private Button doneButton = new Button("Done");
    private Button opinionButton = new Button("Add Opinion");
    private Button reportButton = new Button("Report a problem / complaint");
    private Button sendButton = new Button("Send your request");
    private Label nameLabel = new Label();
    private Label surnameLabel = new Label();
    private Label mailLabel = new Label();
    private Label tripsLabel = new Label("You have booked trips to:");
    private Label bookedTripsLabel = new Label();
    private Label complaintSend = new Label("Complaint send!");
    private Image avatarImage = new Image();
    private TextField avatarField = new TextField("Avatar");
    private TextField nameField = new TextField("Name");
    private TextField surnameField = new TextField("Surname");
    private TextField mailField = new TextField("Mail");
    private TextArea complaintMessage = new TextArea("Write your complaint");
    private PasswordField passwordField = new PasswordField();
    private Binder<Traveller> binder = new Binder();

    //layout
    private VerticalLayout trips = new VerticalLayout(tripsLabel,bookedTripsLabel);
    private VerticalLayout labels = new VerticalLayout(avatarImage, avatarField, nameLabel, nameField, surnameLabel,
            surnameField, mailLabel, mailField, passwordField, trips);
    private VerticalLayout complaintLayout = new VerticalLayout(complaintMessage,sendButton);
    private VerticalLayout buttons = new VerticalLayout(updateButton, deleteButton,passwordButton, doneButton,
            saveButton,opinionButton,reportButton, complaintLayout,complaintSend);
    private HorizontalLayout horizontalLayout = new HorizontalLayout(labels,buttons);
    private VerticalLayout finalLayout = new VerticalLayout(introduction,horizontalLayout);


    public TravellerAccount() {
        logo.setHeight("55px");
        addToNavbar(logo, tabs);
        setContent(finalLayout);
        fillTheFormRow();
        setElementVisibility();
        serveButtons();
    }

    private void serveButtons(){
        updateButton.addClickListener(event -> {
            prepareForm();
            updateButton.setVisible(false);
        });
        passwordButton.addClickListener(event -> {
            setElementVisibility();
            passwordChange();
            doneButton.setVisible(true);
            passwordButton.setVisible(false);
        });
        deleteButton.addClickListener(event -> {
            service.deleteTraveller(activeTraveller.getId().toString());
        });
        saveButton.addClickListener(event -> {
            updateTraveller();
            UI.getCurrent().navigate("");
        });
        doneButton.addClickListener(event -> {
            fillTheFormAfterChanges();
            setElementVisibility();
            saveButton.setVisible(true);
        });
        opinionButton.addClickListener(event -> {
            UI.getCurrent().navigate("leave/opinion");
        });
        reportButton.addClickListener(event ->{
            complaintLayout.setVisible(true);
        });
        sendButton.addClickListener(event -> {
            String message = complaintMessage.getValue();
//            complaint.sendEmail(message);
            complaintSend.setVisible(true);
        });
    }

    private void fillTheFormRow(){
        nameLabel.setText(activeTraveller.getFirstName());
        surnameLabel.setText(activeTraveller.getLastName());
        mailLabel.setText(activeTraveller.getMail());
        avatarImage.setSrc(activeTraveller.getAvatarUrl());
        if(!activeTraveller.getTripsId().isEmpty()) {
            bookedTripsLabel.setText(activeTraveller.getTripsId().stream().map(element -> tripService.getTrip(element).getCity())
                    .collect(Collectors.joining(",")));
        }

    }



    private void setElementVisibility(){
        complaintSend.setVisible(false);
        complaintLayout.setVisible(false);
        doneButton.setVisible(false);
        saveButton.setVisible(false);
        nameLabel.setVisible(true);
        surnameLabel.setVisible(true);
        mailLabel.setVisible(true);
        trips.setVisible(true);
        avatarImage.setVisible(true);
        nameField.setVisible(false);
        surnameField.setVisible(false);
        mailField.setVisible(false);
        passwordField.setVisible(false);
        avatarField.setVisible(false);
    }

    private void prepareForm(){
        doneButton.setVisible(true);
        nameLabel.setVisible(false);
        surnameLabel.setVisible(false);
        mailLabel.setVisible(false);
        trips.setVisible(false);
        nameField.setVisible(true);
        surnameField.setVisible(true);
        avatarField.setVisible(true);
        mailField.setVisible(true);
        nameField.setValue(activeTraveller.getFirstName());
        surnameField.setValue(activeTraveller.getLastName());
        mailField.setValue(activeTraveller.getMail());
        avatarField.setValue(activeTraveller.getAvatarUrl());
        nameField.setWidth("400px");
        nameField.setValueChangeMode(ValueChangeMode.EAGER);
        avatarField.setWidth("400px");
        avatarField.setValueChangeMode(ValueChangeMode.EAGER);
        surnameField.setWidth("400px");
        surnameField.setValueChangeMode(ValueChangeMode.EAGER);
        mailField.setWidth("400px");
        mailField.setValueChangeMode(ValueChangeMode.EAGER);
        binder.forField(mailField)
                .withValidator(new EmailValidator(
                        "This doesn't look like a valid email address"))
                .bind(Traveller::getMail, Traveller::setMail);
    }

    private void passwordChange(){
        saveButton.setVisible(true);
        passwordField.setVisible(true);
        passwordField.setValue(activeTraveller.getPassword());
    }

    private void updateTraveller(){
            service.updateTraveller(createTraveller());
    }

    private Traveller createTraveller(){
        Traveller oldTraveler = service.getTraveller(activeTraveller.getId());
        oldTraveler.setAvatarUrl(avatarImage.getText());
        oldTraveler.setPassword(passwordField.getValue());
        oldTraveler.setLastName(surnameLabel.getText());
        oldTraveler.setFirstName(nameLabel.getText());
        oldTraveler.setMail(mailLabel.getText());
        return oldTraveler;

    }

    private void fillTheFormAfterChanges(){
        nameLabel.setText(nameField.getValue());
        surnameLabel.setText(surnameField.getValue());
        mailLabel.setText(mailField.getValue());
        avatarImage.setText(avatarField.getValue());
    }
}
