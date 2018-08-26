package com.example.user.minipro5997secondapp.model.entities;

import java.util.Date;

public class ClientRequest {
    private String name;
    private String phone;
    private String email;
    private ClientRequestStatus status;
    private double sourceLatitude;
    private double sourceLongitude;
    private String destination;


    private Date startDrive;
    private Date endDrive;
    private String id;

    // ------------ constructor ----------

    public ClientRequest() {
    }

    public ClientRequest(String name, String phone, String email, ClientRequestStatus status, double sourceLatitude,
                         double sourceLongitude, String destination) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.status = status;
        this.sourceLatitude = sourceLatitude;
        this.sourceLongitude = sourceLongitude;
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

    public double getSourceLatitude() {
        return sourceLatitude;
    }

    public void setSourceLatitude(double sourceLatitude) {
        this.sourceLatitude = sourceLatitude;
    }

    public double getSourceLongitude() {
        return sourceLongitude;
    }

    public void setSourceLongitude(double sourceLongitude) {
        this.sourceLongitude = sourceLongitude;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}