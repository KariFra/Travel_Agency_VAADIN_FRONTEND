package com.kari.travelfront.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Trip {

    private Long id;
    private Long userId;
    private Long price;
    private String city;
    private String url;
    private String description;
    private String length;
    private String food;
    private int stars;
    private List<Upgrades> additions;

    @Override
    public String toString() {
        return "Trip{" +
                "id=" + id +
                ", traveller=" + userId +
                ", price=" + price +
                ", city='" + city + '\'' +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                ", length='" + length + '\'' +
                ", food='" + food + '\'' +
                ", stars=" + stars +
                ", additions=" + additions +
                '}';
    }
}
