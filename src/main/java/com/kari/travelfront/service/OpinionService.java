package com.kari.travelfront.service;

import com.google.gson.Gson;
import com.kari.travelfront.domain.Opinion;
import com.kari.travelfront.domain.Opinion;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class OpinionService {

    private RestTemplate restTemplate = new RestTemplate();

    private Gson gson = new Gson();

    private HttpHeaders headers = new HttpHeaders();

    URI url = UriComponentsBuilder.fromHttpUrl("https://desolate-sea-14021.herokuapp.com/v1/opinions").build().encode().toUri();

    public List<Opinion> getOpinions(){
        restTemplate.getForObject(url,Opinion[].class);
        ResponseEntity<Opinion[]> opinions = restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, Opinion[].class);
        if(opinions != null) {
            return Arrays.asList(opinions.getBody());
        } else {
            return  new ArrayList<>();
        }
    }

    public Opinion getOpinion(Long id) {
        ResponseEntity<Opinion> opinion = restTemplate.exchange(url+"/"+id, HttpMethod.GET, HttpEntity.EMPTY, Opinion.class);
        if (opinion != null) {
            return opinion.getBody();
        } else {
            return new Opinion();
        }
    }

    public void deleteOpinion(String id) {
        restTemplate.delete(url+"/"+id);
    }

    public void createOpinion(Opinion opinion) {
        headers.setContentType(MediaType.APPLICATION_JSON);
        String string = gson.toJson(opinion);
        HttpEntity<String> httpEntity = new HttpEntity<>(string, headers);
        restTemplate.postForEntity(url, httpEntity, String.class);
    }

    public void updateOpinion(Opinion opinion){
        Opinion oldOpinion = getOpinion(opinion.getId());
        oldOpinion.setMessage(opinion.getMessage());
        oldOpinion.setRating(opinion.getRating());
        oldOpinion.setUserUrl(opinion.getUserUrl());
        headers.setContentType(MediaType.APPLICATION_JSON);
        String string = gson.toJson(oldOpinion);
        HttpEntity<String> httpEntity = new HttpEntity<>(string, headers);
        restTemplate.exchange(url, HttpMethod.PUT, httpEntity, String.class);
    }


}
