package com.kari.travelfront.service;

import com.google.gson.Gson;
import com.kari.travelfront.domain.Cocktail;
import com.kari.travelfront.domain.Drink;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


@Component
public class CocktailService {

    private RestTemplate restTemplate = new RestTemplate();

    private Gson gson = new Gson();

    private HttpHeaders headers = new HttpHeaders();

    private URI url = UriComponentsBuilder.fromHttpUrl("https://desolate-sea-14021.herokuapp.com/v1/cocktail").build().encode().toUri();



    public Cocktail getDrink() {
        ResponseEntity<Cocktail> drink = restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, Cocktail.class);
        if (drink != null) {
            return drink.getBody();
        } else {
            return new Cocktail();
        }
    }
}
