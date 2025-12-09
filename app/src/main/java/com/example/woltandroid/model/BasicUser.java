package com.example.woltandroid.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
public class BasicUser extends User{
    protected String address;

    public BasicUser() {
    }

    public BasicUser(boolean isAdmin, LocalDateTime dateUpdated, LocalDateTime dateCreated, String phoneNumber, String surname, String name, String password, String login, int id, String address) {
        super(isAdmin, dateUpdated, dateCreated, phoneNumber, surname, name, password, login, id);
        this.address = address;
    }

    public BasicUser(String login, String password, String name, String surname, String phoneNumber, String address) {
        super(login, password, name, surname, phoneNumber);
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
