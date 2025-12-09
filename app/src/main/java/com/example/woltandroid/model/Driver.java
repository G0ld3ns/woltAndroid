package com.example.woltandroid.model;


import java.time.LocalDate;
import java.time.LocalDateTime;


public class Driver extends User{
    private LocalDate bDate;

    private VehicleType vehicleType;
    private String address;

    public Driver() {
    }

    public Driver(boolean isAdmin, LocalDateTime dateUpdated, LocalDateTime dateCreated, String phoneNumber, String surname, String name, String password, String login, int id, LocalDate bDate, VehicleType vehicleType, String address) {
        super(isAdmin, dateUpdated, dateCreated, phoneNumber, surname, name, password, login, id);
        this.bDate = bDate;
        this.vehicleType = vehicleType;
        this.address = address;
    }

    public Driver(String login, String password, String name, String surname, String phoneNumber, LocalDate bDate, VehicleType vehicleType, String address) {
        super(login, password, name, surname, phoneNumber);
        this.bDate = bDate;
        this.vehicleType = vehicleType;
        this.address = address;
    }

    public LocalDate getbDate() {
        return bDate;
    }

    public void setbDate(LocalDate bDate) {
        this.bDate = bDate;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
