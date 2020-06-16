package com.kari.travelfront.view.opinion;

import com.kari.travelfront.domain.Opinion;
import com.kari.travelfront.service.OpinionService;
import com.kari.travelfront.view.MainView;
import com.kari.travelfront.view.article.OfficialArticlesView;
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

@Route(value="opinions")
@StyleSheet(value= "/css/register.css")
public class OfficialOpinionView extends AppLayout {


    //db
    private OpinionService service = new OpinionService();


    //elements
    private Image logo = new Image("https://image.flaticon.com/icons/svg/2990/2990507.svg", "Trip agency logo");
    private Tabs tabs = new Tabs(new Tab(new RouterLink("Home", MainView.class)),
            new Tab(new RouterLink("Articles", OfficialArticlesView.class)),
            new Tab(new RouterLink("Trips", OfficialTripsView.class)));
    private List<Opinion> listOfOpinions = service.getOpinions();

    public OfficialOpinionView() {
        logo.setHeight("55px");
        addToNavbar(logo, tabs);
        fillMainLayout();
    }

    private HorizontalLayout fillTheFields(Opinion opinion){
        return new HorizontalLayout(new Image(opinion.getUserUrl(),"photo"),
                new VerticalLayout(new Label(showRating(opinion.getRating())),new Label(opinion.getMessage())));
    }

    private VerticalLayout addManyOpinions(){
        VerticalLayout layout = new VerticalLayout();
        for (int n =0; n<listOfOpinions.size(); n++){
            layout.add(fillTheFields(listOfOpinions.get(n)));
        }
        return layout;
    }

    private void fillMainLayout(){
        int size = listOfOpinions.size();
        if(size==0){
            setContent(new Label("There are no articles"));
        } else{
            setContent(addManyOpinions());
        }
    }

    private String showRating(int rate){
        String opinionRating = "";
        if(rate>8){
            opinionRating = "Awesome!";
        }
        else if (rate>6 && rate<8){
            opinionRating = "Very nice!";
        }
        else if (rate>4 && rate<6){
            opinionRating = "Nothing special";
        }
        else if (rate<4){
            opinionRating = "Shitty";
        }
        return opinionRating;
    }
}
