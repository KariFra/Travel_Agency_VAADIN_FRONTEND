package com.kari.travelfront.service;

import com.kari.travelfront.domain.Currency;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class CurrencyService {

    private RestTemplate restTemplate = new RestTemplate();

    private URI url = UriComponentsBuilder.fromHttpUrl("https://desolate-sea-14021.herokuapp.com/v1/currency").build().encode().toUri();


    public Currency getAmount(String currency) {
        ResponseEntity<Currency> result = restTemplate.exchange(url+"/"+currency, HttpMethod.GET,
                HttpEntity.EMPTY, Currency.class);
        if (result != null) {
            return result.getBody();
        } else {
            return new Currency();
        }
    }
}
