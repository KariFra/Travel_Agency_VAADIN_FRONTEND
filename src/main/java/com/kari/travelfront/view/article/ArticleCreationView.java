package com.kari.travelfront.view.article;

import com.kari.travelfront.domain.Article;
import com.kari.travelfront.service.ArticleService;
import com.kari.travelfront.view.MainView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;


@Route(value="admin/article/change")
@StyleSheet(value= "/css/style.css")
public class ArticleCreationView extends AppLayout {

    //db
    private ArticleService service = new ArticleService();
    private ArticlesView articlesView = new ArticlesView();

    //static
    private Image logo = new Image("https://image.flaticon.com/icons/svg/2990/2990507.svg", "Trip agency logo");
    private Tabs tabs = new Tabs(new Tab(new RouterLink("Home", MainView.class)),
            new Tab(new RouterLink("Articles",ArticlesView.class)));
    private TextField articleTitle = new TextField("Title: from 5 to 50 symbols");
    private TextArea articleText = new TextArea("Text: from 20 to 1000 symbols");
    private TextField articlePhotoUrl = new TextField("Photo: go to https://pixabay.com/pl/");
    private TextField articleCity = new TextField("City");
    private Button confirm = new Button("Confirm");
    private Button goBack = new Button("Go back to articles");
    private String articleNumber = "";

    //layout
    private VerticalLayout verticalLayout = new VerticalLayout(articleTitle,articleText,articlePhotoUrl,articleCity,confirm,goBack);

    public ArticleCreationView() {
        logo.setHeight("55px");
        addToNavbar(logo, tabs);
        setContent(verticalLayout);
        prepareTextFields();
        articleNumber = articlesView.number;
        if(articleNumber != ""){
            formPreparationInUpdate();
        }
        serveButtons();
    }

    private void prepareTextFields(){
        articleTitle.setValueChangeMode(ValueChangeMode.EAGER);
        articleTitle.setMaxLength(50);
        articleTitle.setMinLength(5);
        articleTitle.setWidth("400px");
        articleText.setValueChangeMode(ValueChangeMode.EAGER);
        articleText.setMaxLength(1000);
        articleText.setMinLength(20);
        articleText.setWidth("400px");
        articlePhotoUrl.setValueChangeMode(ValueChangeMode.EAGER);
        articlePhotoUrl.setWidth("400px");
        articleCity.setValueChangeMode(ValueChangeMode.EAGER);
        articleCity.setWidth("400px");
    }

    private Article builder(){
        Article article = new Article();
        article.setTitle(articleTitle.getValue());
        article.setText(articleText.getValue());
        article.setCity(articleCity.getValue());
        article.setPhotoUrl(articlePhotoUrl.getValue());
       return article;
    }

    private void formPreparationInUpdate(){
        Article article = service.getArticle(Long.parseLong(articleNumber));
        articleTitle.setValue(article.getTitle());
        articleText.setValue(article.getText());
        articleCity.setValue(article.getCity());
        articlePhotoUrl.setValue(article.getPhotoUrl());
    }

    private void articleUpdate(){
        Article article = service.getArticle(Long.parseLong(articleNumber));
        article.setTitle(articleTitle.getValue());
        article.setText(articleText.getValue());
        article.setCity(articleCity.getValue());
        article.setPhotoUrl(articlePhotoUrl.getValue());
        service.updateArticle(article);
    }

    private void serveButtons(){
        confirm.addClickListener(event -> {
            if(articleNumber != ""){
                articleUpdate();
                UI.getCurrent().navigate("admin/articles");
            } else {
                Article article = builder();
                service.createArticle(article);
                UI.getCurrent().navigate("admin/articles");
            }});
        goBack.addClickListener(event -> UI.getCurrent().navigate("admin/articles"));
    }
}
