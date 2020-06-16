package com.kari.travelfront.service;

import com.google.gson.Gson;
import com.kari.travelfront.domain.Article;
import org.springframework.http.*;
import org.springframework.stereotype.Component;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ArticleService {


    private RestTemplate restTemplate = new RestTemplate();

    private Gson gson = new Gson();

    private HttpHeaders headers = new HttpHeaders();

    private URI url = UriComponentsBuilder.fromHttpUrl("https://desolate-sea-14021.herokuapp.com/v1/articles").build().encode().toUri();

    public List<Article> getArticles(){
        restTemplate.getForObject(url,String.class);
        ResponseEntity<Article[]> articles = restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, Article[].class);
        if(articles != null) {
            return Arrays.asList(articles.getBody());
        } else {
            return  new ArrayList<>();
        }
    }

    public Article getArticle(Long id) {
        ResponseEntity<Article> article = restTemplate.exchange(url+"/"+id, HttpMethod.GET, HttpEntity.EMPTY, Article.class);
        if (article != null) {
            return article.getBody();
        } else {
            return new Article();
        }
    }

    public void deleteArticle(String id) {
        restTemplate.delete(url+"/"+id);
    }

    public void createArticle(Article article) {
        headers.setContentType(MediaType.APPLICATION_JSON);
        String string = gson.toJson(article);
        HttpEntity<String> httpEntity = new HttpEntity<>(string, headers);
        restTemplate.postForEntity(url, httpEntity, String.class);
    }

    public void updateArticle(Article article){
        Article oldArticle = getArticle(article.getId());
        oldArticle.setCity(article.getCity());
        oldArticle.setText(article.getText());
        oldArticle.setPhotoUrl(article.getPhotoUrl());
        oldArticle.setTitle(article.getTitle());
        headers.setContentType(MediaType.APPLICATION_JSON);
        String string = gson.toJson(oldArticle);
        HttpEntity<String> httpEntity = new HttpEntity<>(string, headers);
        restTemplate.exchange(url, HttpMethod.PUT, httpEntity, String.class);
    }

}
