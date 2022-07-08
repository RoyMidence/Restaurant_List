package com.example.hwk3;

public class Restaurant {
    private String name;
    private String location;
    private double rating;

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Restaurant(String name, String location, float rating) {
        this.name = name;
        this.location = location;
        this.rating = rating;
    }

    public Restaurant() {
        name = "Name";
        location = "Location";
        rating = 1;
    }

    public String getName() {
        return name;
    }
}
