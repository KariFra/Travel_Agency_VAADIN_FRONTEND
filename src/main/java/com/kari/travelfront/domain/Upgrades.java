package com.kari.travelfront.domain;

public enum Upgrades {
    NONE("No upgrades"),
    WINERY_TRIP("Trip to the closest winery and 5 liters of wine"),
    DRIVER("Private driver for the whole trip"),
    PROBLEM_SOLVER("Private assistant who deals with all the problems "),
    NETFLIX_CARD("Access to all netflix movies for the time of trip");

    Upgrades(String description) {
    }

}
