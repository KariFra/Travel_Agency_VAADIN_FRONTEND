package com.kari.travelfront.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Weather {

    private String minimalTemp;
    private String maximalTemp;
    private String weatherState;
    private String date;
    private String iconUrl;
}
