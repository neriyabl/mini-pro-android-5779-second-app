package com.example.user.minipro5997secondapp.model.entities;

import android.location.Location;

import java.util.Date;

public class ClientRequest {
    private String name;
    private String phone;
    private String email;
    private ClientRequestStatus status;
    private Location source;
    private Location destination;


    private Date startDrive;
    private Date endDrive;
    private Long id;

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
        this.startDrive = null;
        this.endDrive = null;
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

    public Date getStartDrive() {
        return startDrive;
    }

    public void setStartDrive(Date startDrive) {
        this.startDrive = startDrive;
    }

    public Date getEndDrive() {
        return endDrive;
    }

    public void setEndDrive(Date endDrive) {
        this.endDrive = endDrive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}