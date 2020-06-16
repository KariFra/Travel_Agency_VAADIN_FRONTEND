package com.kari.travelfront.service;

import com.google.gson.Gson;
import com.kari.travelfront.domain.Role;
import com.kari.travelfront.domain.Traveller;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class TravellerService {


    private RestTemplate restTemplate = new RestTemplate();

    private Gson gson = new Gson();

    private HttpHeaders headers = new HttpHeaders();

    private URI url = UriComponentsBuilder.fromHttpUrl("https://desolate-sea-14021.herokuapp.com/v1/travellers").build().encode().toUri();

    public List<Traveller> getTravellers(){
        restTemplate.getForObject(url,Traveller[].class);
        ResponseEntity<Traveller[]> travellers = restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY,Traveller[].class);
        if(travellers != null) {
            return Arrays.asList(travellers.getBody());
        } else {
            return  new ArrayList<>();
        }
    }

    public Traveller getTraveller(Long id) {
        ResponseEntity<Traveller> traveller = restTemplate.exchange(url+"/"+id, HttpMethod.GET, HttpEntity.EMPTY, Traveller.class);
        if (traveller != null) {
            return traveller.getBody();
        } else {
            return new Traveller();
        }
    }



    public void deleteTraveller(String id) {
        restTemplate.delete(url+"/"+id);
    }

    public void createTraveller(Traveller traveller) {
        headers.setContentType(MediaType.APPLICATION_JSON);
        System.out.println(traveller);
        String string = gson.toJson(traveller);
        HttpEntity<String> httpEntity = new HttpEntity<>(string, headers);
        restTemplate.postForEntity(url, httpEntity, String.class);
    }

    public String updateTraveller(Traveller traveller){
        Traveller oldTraveller = getTraveller(traveller.getId());
        oldTraveller.setFirstName(traveller.getFirstName());
        oldTraveller.setLastName(traveller.getLastName());
        oldTraveller.setAvatarUrl(traveller.getAvatarUrl());
        oldTraveller.setMail(traveller.getMail());
        oldTraveller.setPassword(traveller.getPassword());
        oldTraveller.setRole(Role.USER.name());
        oldTraveller.setTripsId(traveller.getTripsId());
        headers.setContentType(MediaType.APPLICATION_JSON);
        String string = gson.toJson(oldTraveller);
        HttpEntity<String> httpEntity = new HttpEntity<>(string, headers);
        return restTemplate.exchange(url, HttpMethod.PUT, httpEntity, String.class).getBody();

    }

}
