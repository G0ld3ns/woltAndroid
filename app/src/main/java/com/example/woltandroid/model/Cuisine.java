package com.example.woltandroid.model;



import java.io.Serializable;
import java.util.List;


public class Cuisine implements Serializable {

    private int id;
    protected String name;
    protected Double price;
    protected boolean spicy = false;
    protected boolean vegan = false;

    public Cuisine(int id, String name, Double price, boolean spicy, boolean vegan) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.spicy = spicy;
        this.vegan = vegan;
    }

    public Cuisine() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public boolean isSpicy() {
        return spicy;
    }

    public void setSpicy(boolean spicy) {
        this.spicy = spicy;
    }

    public boolean isVegan() {
        return vegan;
    }

    public void setVegan(boolean vegan) {
        this.vegan = vegan;
    }
}

