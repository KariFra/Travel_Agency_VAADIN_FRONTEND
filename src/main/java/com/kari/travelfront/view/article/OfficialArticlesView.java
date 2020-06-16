package com.kari.travelfront.view.article;

import com.kari.travelfront.domain.Article;
import com.kari.travelfront.service.ArticleService;
import com.kari.travelfront.view.MainView;
import com.kari.travelfront.view.opinion.OfficialOpinionView;
import com.kari.travelfront.view.trip.OfficialTripsView;
import com.vaadin.flow.component.applayout.AppLayout;
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

@Route(value="articles")
@StyleSheet(value= "/css/style.css")
public class OfficialArticlesView extends AppLayout {

    //db
    private ArticleService service = new ArticleService();

    //elements
    private Image logo = new Image("https://image.flaticon.com/icons/svg/2990/2990507.svg", "Trip agency logo");
    private Tabs tabs = new Tabs(new Tab(new RouterLink("Home", MainView.class)),
            new Tab(new RouterLink("Opinions", OfficialOpinionView.class)),
            new Tab(new RouterLink("Trips", OfficialTripsView.class)));
    private List<Article> listOfArticles = service.getArticles();



    public OfficialArticlesView() {
        logo.setHeight("55px");
        addToNavbar(logo, tabs);
        fillMainLayout();
    }


    private HorizontalLayout fillTheFields(Article article){
       return new HorizontalLayout(new VerticalLayout(new Label(article.getCity()),new Image(article.getPhotoUrl(),"photo")),
                new VerticalLayout(new Label(article.getTitle()),new Label(article.getText())));
    }

    private VerticalLayout addManyArticles(){
        VerticalLayout layout = new VerticalLayout();
        for (int n =0; n<listOfArticles.size(); n++){
            layout.add(fillTheFields(listOfArticles.get(n)));
        }
        return layout;
    }

    private void fillMainLayout(){
        int size = listOfArticles.size();
        if(size==0){
            setContent(new Label("There are no articles"));
        } else{
            setContent(addManyArticles());
        }
    }






}
