package com.kari.travelfront.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Opinion {

    private Long id;
    private String message;
    private String userUrl;
    private int rating;
}
