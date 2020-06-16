package com.kari.travelfront.view.article;


import com.kari.travelfront.domain.Article;
import com.kari.travelfront.service.ArticleService;
import com.kari.travelfront.view.MainView;
import com.kari.travelfront.view.drink.DrinkView;
import com.kari.travelfront.view.opinion.OpinionsView;
import com.kari.travelfront.view.traveller.TravellersView;
import com.kari.travelfront.view.trip.TripsView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import java.util.stream.Collectors;

@Route(value="admin/articles")
@StyleSheet(value= "/css/style.css")
public class ArticlesView extends AppLayout {

    //db
    private Grid<Article> grid = new Grid<>(Article.class);
    private ArticleService service = new ArticleService();
    private boolean sure;

    //update requirement
    public static String number = "";

    //elements
    private Image logo = new Image("https://image.flaticon.com/icons/svg/2990/2990507.svg", "Trip agency logo");
    private Tabs tabs = new Tabs(new Tab(new RouterLink("Home", MainView.class)),
            new Tab(new RouterLink("Opinions", TripsView.class)),
            new Tab(new RouterLink("Trips", ArticlesView.class)),
            new Tab(new RouterLink("Travellers", TravellersView.class)),
            new Tab(new RouterLink("Drinks", DrinkView.class)));
    private Label articleNumberLabel = new Label();
    private Button deleteButton = new Button("Delete this article");
    private Button updateButton = new Button("Update this article");
    private Button createButton = new Button("Create new article");
    private Label content = new Label(
            "Insert number of article you want to change");
    private Label question = new Label(
            "Do you really want to delete article no:"+number+"?");
    private Label introduction = new Label("There are " + service.getArticles().size() + " articles in the database. " +
            "so don't hesitate to write more! To make changes to existing data, choose an item by " +
                      "clicking on it. Than decide on your action with buttons below.");
    private NativeButton no = new NativeButton("NO! Thanks for asking");
    private NativeButton yes = new NativeButton("Yes");
    private Notification notification = new Notification(content);
    private Notification areYouSure = new Notification(question, yes, no);
    private TextField filter = new TextField();

    //layouts
    private VerticalLayout buttonLayout = new VerticalLayout(articleNumberLabel,deleteButton,updateButton);
    private VerticalLayout verticalLayout = new VerticalLayout(introduction,createButton,filter ,grid, buttonLayout);

    public ArticlesView() {
        createGrid();
        logo.setHeight("55px");
        addToNavbar(logo, tabs);
        serveButtons();
        setContent(verticalLayout);
        refresh();
        }

    public void refresh() {
        grid.setItems(service.getArticles());
    }

    private void setFilter(){
        filter.setPlaceholder("Filter by City");
        filter.setClearButtonVisible(true);
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> update(filter.getValue()));
    }
    private void update(String city) {
        grid.setItems(service.getArticles().stream().filter(item -> item.getCity().contains(city)).collect(Collectors.toList()));
    }

    private void createGrid(){
        grid.setColumns("id", "title", "text","photoUrl","city");
        grid.setMaxHeight("300px");
        grid.addItemClickListener(event -> {
            number = event.getItem().getId().toString();
            articleNumberLabel.setText("What you are planning to do with article no: "+ number+ " ?");
        });
    }

    private void serveButtons(){
        createButton.addClickListener(event -> {
            number = "";
            UI.getCurrent().navigate("article/change");});
        deleteButton.addClickListener(event -> {
            if(!number.equals("")){
                areYouSure.open();
                while(sure){
                service.deleteArticle(number);}
            } else{notification.open();
            }
        });
        updateButton.addClickListener(event -> {
            if(!number.equals("")){
                UI.getCurrent().navigate("article/change");
            } else{notification.open();
            }
        });
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

}
