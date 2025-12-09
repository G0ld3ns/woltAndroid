package com.example.woltandroid.model;



import java.time.LocalDate;
import java.util.List;


public class FoodOrder {

    protected int id;
    private  String name;
    private Double price;

    private OrderStatus orderStatus;



    public FoodOrder(int id, String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public FoodOrder() {
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
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

    @Override
    public String toString() {
        return name + " - "  + price + " " + orderStatus ;
    }

}
