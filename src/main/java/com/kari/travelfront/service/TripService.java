package com.kari.travelfront.service;

import com.google.gson.Gson;
import com.kari.travelfront.domain.Trip;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TripService {

    private RestTemplate restTemplate = new RestTemplate();

    private Gson gson = new Gson();

    private HttpHeaders headers = new HttpHeaders();

    private URI url = UriComponentsBuilder.fromHttpUrl("https://desolate-sea-14021.herokuapp.com/v1/trips").build().encode().toUri();

    public List<Trip> getTrips(){
        restTemplate.getForObject(url,Trip[].class);
        ResponseEntity<Trip[]> trips = restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY,Trip[].class);
        if(trips != null) {
            return Arrays.asList(trips.getBody());
        } else {
            return  new ArrayList<>();
        }
    }

    public Trip getTrip(Long id){
        restTemplate.getForObject(url+"/"+id,Trip.class);
        ResponseEntity<Trip> trip = restTemplate.exchange(url+"/"+id, HttpMethod.GET, HttpEntity.EMPTY, Trip.class);
        if (trip != null) {
            return trip.getBody();
        } else {
            return new Trip();
        }
    }

    public void deleteTrip(String id) {
        restTemplate.delete(url+"/"+id);
    }

    public void createTrip(Trip trip) {
        headers.setContentType(MediaType.APPLICATION_JSON);
        String string = gson.toJson(trip);
        HttpEntity<String> httpEntity = new HttpEntity<>(string, headers);
        restTemplate.postForEntity(url, httpEntity, String.class);
    }

    public void updateTrip(Trip trip){
        Trip oldTrip = getTrip(trip.getId());
        oldTrip.setCity(trip.getCity());
        oldTrip.setDescription(trip.getDescription());
        oldTrip.setLength(trip.getLength());
        oldTrip.setPrice(trip.getPrice());
        oldTrip.setUrl(trip.getUrl());
        oldTrip.setUserId(trip.getUserId());
        oldTrip.setFood(trip.getFood());
        oldTrip.setStars(trip.getStars());
        oldTrip.setAdditions(trip.getAdditions());
        headers.setContentType(MediaType.APPLICATION_JSON);
        String string = gson.toJson(oldTrip);
        HttpEntity<String> httpEntity = new HttpEntity<>(string, headers);
        restTemplate.exchange(url, HttpMethod.PUT, httpEntity, String.class);
    }


}
