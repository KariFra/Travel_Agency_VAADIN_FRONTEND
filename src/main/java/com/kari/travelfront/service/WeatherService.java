package com.kari.travelfront.service;

import com.kari.travelfront.domain.Weather;
import com.kari.travelfront.domain.Weather;
import com.kari.travelfront.domain.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class WeatherService {

    private RestTemplate restTemplate = new RestTemplate();

    private URI url = UriComponentsBuilder.fromHttpUrl("https://desolate-sea-14021.herokuapp.com/v1/weather").build().encode().toUri();

    public List<Weather> getWeatherForFiveDays(String city){
        restTemplate.getForObject(url+"/"+city,Weather[].class);
        ResponseEntity<Weather[]> weathers = restTemplate.exchange(url +"/"+ city, HttpMethod.GET, HttpEntity.EMPTY,
                Weather[].class);
        if(weathers != null) {
            return Arrays.asList(weathers.getBody());
        } else {
            return  new ArrayList<>();
        }

    }


    public Weather getWeatherNow(String city) {
        ResponseEntity<Weather> weatherNow = restTemplate.exchange(url + "/now/" + city, HttpMethod.GET,
                HttpEntity.EMPTY, Weather.class);
        if (weatherNow != null) {
            return weatherNow.getBody();
        } else {
            return new Weather();
        }
    }
}
