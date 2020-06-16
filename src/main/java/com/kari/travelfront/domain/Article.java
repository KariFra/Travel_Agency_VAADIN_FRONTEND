package com.kari.travelfront.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Article {

    private Long id;

    private String title;

    private String text;

    private String photoUrl;

    private String city;

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
