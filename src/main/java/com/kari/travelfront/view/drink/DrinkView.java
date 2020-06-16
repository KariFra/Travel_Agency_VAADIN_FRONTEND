package com.kari.travelfront.view.drink;

import com.kari.travelfront.domain.Drink;
import com.kari.travelfront.operations.CocktailSupport;
import com.kari.travelfront.service.DrinkService;
import com.kari.travelfront.view.MainView;
import com.kari.travelfront.view.article.ArticlesView;
import com.kari.travelfront.view.opinion.OpinionsView;
import com.kari.travelfront.view.traveller.TravellersView;
import com.kari.travelfront.view.trip.TripsView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route(value="admin/drinks")
@StyleSheet(value= "/css/style.css")
public class DrinkView extends AppLayout {

    //db
    private Grid<Drink> grid = new Grid<>(Drink.class);
    private DrinkService service = new DrinkService();
    private CocktailSupport support = new CocktailSupport();
    private boolean sure;


    //update requirement
    public static String number = "";

    //elements
    private Image logo = new Image("https://image.flaticon.com/icons/svg/2990/2990507.svg", "Trip agency logo");
    private Tabs tabs = new Tabs(new Tab(new RouterLink("Home", MainView.class)),
            new Tab(new RouterLink("Opinions", OpinionsView.class)),
            new Tab(new RouterLink("Articles", ArticlesView.class)),
            new Tab(new RouterLink("Travellers", TravellersView.class)),
            new Tab(new RouterLink("Trips", TripsView.class)));
    private Label drinkNumberLabel = new Label();
    private Label drinkNameLabel = new Label();
    private Label drinkRecipeLabel = new Label();
    private Label drinkNameLabelChange = new Label();
    private Label drinkRecipeLabelChange = new Label();
       private Label content = new Label(
            "Insert number of drink you want to change");
    private Label question = new Label(
            "Do you really want to delete drink no:"+number+"?");
    private Label introduction = new Label("There are " + service.getDrinks().size() + " drinks in the database. " +
            "so don't hesitate to add more! To make changes to existing data, choose an item by " +
            "clicking on it. Than decide on your action with buttons below.");
    private Button acceptButton = new Button("Done");
    private Button deleteButton = new Button("Delete this drink");
    private Button inspirationButton = new Button("Get inspired");
    private Button hideInspirationButton = new Button("Hide");
    private Button addButton = new Button("Add drink");
    private Button createButton = new Button("Create new drink");
    private NativeButton no = new NativeButton("NO! Thanks for asking");
    private NativeButton yes = new NativeButton("Yes");
    private Notification notification = new Notification(content);
    private Notification areYouSure = new Notification(question, yes, no);


    //layouts
    private HorizontalLayout changeLabels = new HorizontalLayout(drinkNameLabelChange,drinkRecipeLabelChange,acceptButton);
    private VerticalLayout hiddenButtons = new VerticalLayout(hideInspirationButton,addButton);
    private HorizontalLayout drinkInspirationLayout = new HorizontalLayout(drinkNameLabel,drinkRecipeLabel,hiddenButtons);
    private VerticalLayout buttonLayout = new VerticalLayout(drinkNumberLabel,deleteButton,changeLabels,createButton);
    private VerticalLayout verticalLayout = new VerticalLayout(introduction,inspirationButton,drinkInspirationLayout ,grid, buttonLayout);

    public DrinkView() {
        createGrid();
        logo.setHeight("55px");
        addToNavbar(logo, tabs);
        serveButtons();
        setContent(verticalLayout);
        changeElementVisibility();
        prepareNotification();
        refresh();
    }

    public void refresh() {
        grid.setItems(service.getDrinks());
    }

    private void createGrid(){
        grid.setColumns("id", "name", "recipe");
        grid.setMaxHeight("300px");
        grid.addItemClickListener(event -> {
            number = event.getItem().getId().toString();
            drinkNumberLabel.setText("What you are planning to do with drink no: "+ number+ " ?");
        });
    }

    private void changeElementVisibility(){
        hideInspirationButton.setVisible(false);
        addButton.setVisible(false);
        changeLabels.setVisible(false);
    }

    private void prepareNotification(){
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
    private void serveButtons(){
        hideInspirationButton.addClickListener(event -> {
            hiddenButtons.setVisible(false);
            drinkNameLabel.setVisible(false);
            drinkRecipeLabel.setVisible(false);
            addButton.setVisible(false);
        });
        addButton.addClickListener(event -> {
            Drink newDrink = new Drink();
            drinkNameLabel.getText();
            newDrink.setName(drinkNameLabel.getText());
            newDrink.setRecipe(drinkRecipeLabel.getText());
            service.addDrink(newDrink);
            refresh();
        });
        inspirationButton.addClickListener(event ->{
            drinkNameLabel.setText(support.getInspired().getName());
            drinkRecipeLabel.setText(support.getInspired().getRecipe());
            hideInspirationButton.setVisible(true);
            addButton.setVisible(true);
        } );
        createButton.addClickListener(event -> {
                    number = "";
            changeLabels.setVisible(true);
        });
        deleteButton.addClickListener(event -> {
            if(!number.equals("")){
                areYouSure.open();
                while(sure){
                    service.deleteDrink(number);
                    number = "";
                    refresh();}
            } else{notification.open();
            }
        });
        acceptButton.addClickListener(event -> createDrink());

    }

    private void createDrink(){
        Drink newDrink = new Drink();
        newDrink.setName(drinkNameLabelChange.getText());
        newDrink.setRecipe(drinkRecipeLabelChange.getText());
        service.addDrink(newDrink);
        refresh();
    }

}
