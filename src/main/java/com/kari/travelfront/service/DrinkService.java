package com.kari.travelfront.service;

import com.google.gson.Gson;
import com.kari.travelfront.domain.Drink;
import com.kari.travelfront.domain.Drink;
import com.kari.travelfront.domain.Drink;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Component
public class DrinkService {


    private RestTemplate restTemplate = new RestTemplate();

    private Gson gson = new Gson();

    private HttpHeaders headers = new HttpHeaders();

    private URI url = UriComponentsBuilder.fromHttpUrl("https://desolate-sea-14021.herokuapp.com/v1/drink").build().encode().toUri();


    public List<Drink> getDrinks() {
        restTemplate.getForObject(url,String.class);
        ResponseEntity<Drink[]> drinks = restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, Drink[].class);
        if(drinks != null) {
            return Arrays.asList(drinks.getBody());
        } else {
            return  new ArrayList<>();
        }
    }

    public Drink getDrink(Long id) {
        ResponseEntity<Drink> drink = restTemplate.exchange(url+"/"+id, HttpMethod.GET, HttpEntity.EMPTY, Drink.class);
        if (drink != null) {
            return drink.getBody();
        } else {
            return new Drink();
        }
    }

    public void deleteDrink(String id) {
        restTemplate.delete(url+"/"+id);
    }

    public void addDrink(Drink drink) {
        headers.setContentType(MediaType.APPLICATION_JSON);
        String string = gson.toJson(drink);
        HttpEntity<String> httpEntity = new HttpEntity<>(string, headers);
        restTemplate.postForEntity(url, httpEntity, String.class);
    }


}
