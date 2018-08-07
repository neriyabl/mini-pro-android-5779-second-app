package com.example.user.minipro5997secondapp.model.entities;

import android.location.Location;

public class ClientRequest {
    private String name;
    private String phone;
    private String email;
    private ClientRequestStatus status;
    private Location source;
    private Location destination;

    // ------------ constructor ----------

    public ClientRequest() {
    }

    public ClientRequest(String name, String phone, String email, ClientRequestStatus status, Location source, Location destination) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.status = status;
        this.source = source;
        this.destination = destination;
    }

    // ----------- getters & setters ----------


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ClientRequestStatus getStatus() {
        return status;
    }

    public void setStatus(ClientRequestStatus status) {
        this.status = status;
    }

    public Location getSource() {
        return source;
    }

    public void setSource(Location source) {
        this.source = source;
    }

    public Location getDestination() {
        return destination;
    }

    public void setDestination(Location destination) {
        this.destination = destination;
    }
}