package com.example.woltandroid.model;


import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


public class Restaurant extends User {
    //private String name;
    private String address;
    private String restaurantName;

    private CuisineType cuisineType;
    private double deliveryFee;
    private LocalTime openingTime;
    private LocalTime closingTime;


    public Restaurant(String login, String password, String name, String surname, String phoneNumber, String address, String restaurantName, CuisineType cuisineType, double deliveryFee, LocalTime openingTime, LocalTime closingTime) {
        super(login, password, name, surname, phoneNumber);
        this.address = address;
        this.restaurantName = restaurantName;
        this.cuisineType = cuisineType;
        this.deliveryFee = deliveryFee;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public Restaurant(boolean isAdmin, LocalDateTime dateUpdated, LocalDateTime dateCreated, String phoneNumber, String surname, String name, String password, String login, int id, String address, String restaurantName, CuisineType cuisineType, double deliveryFee, LocalTime openingTime, LocalTime closingTime) {
        super(isAdmin, dateUpdated, dateCreated, phoneNumber, surname, name, password, login, id);
        this.address = address;
        this.restaurantName = restaurantName;
        this.cuisineType = cuisineType;
        this.deliveryFee = deliveryFee;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public Restaurant() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public CuisineType getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(CuisineType cuisineType) {
        this.cuisineType = cuisineType;
    }

    public double getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(double deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public LocalTime getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(LocalTime openingTime) {
        this.openingTime = openingTime;
    }

    public LocalTime getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(LocalTime closingTime) {
        this.closingTime = closingTime;
    }

    @Override
    public String toString() {
        return "Name: " + name + "\nHours: " + openingTime + " - " + closingTime + "\nType: " + cuisineType;
    }
}
