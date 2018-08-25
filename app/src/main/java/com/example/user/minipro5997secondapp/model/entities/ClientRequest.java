package com.example.user.minipro5997secondapp.model.entities;

import java.util.Date;

public class ClientRequest {
    private String name;
    private String phone;
    private String email;
    private ClientRequestStatus status;
    private Long sourceLatitude;
    private Long sourceLongitude;
    private Long destination;


    private Date startDrive;
    private Date endDrive;
    private Long id;

    // ------------ constructor ----------

    public ClientRequest() {
    }

    public ClientRequest(String name, String phone, String email, ClientRequestStatus status, Long sourceLatitude,
                         Long sourceLongitude, Long destination, Date startDrive, Date endDrive, Long id) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.status = status;
        this.sourceLatitude = sourceLatitude;
        this.sourceLongitude = sourceLongitude;
        this.destination = destination;
        this.startDrive = startDrive;
        this.endDrive = endDrive;
        this.id = id;
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

    public Long getSourceLatitude() {
        return sourceLatitude;
    }

    public void setSourceLatitude(Long sourceLatitude) {
        this.sourceLatitude = sourceLatitude;
    }

    public Long getSourceLongitude() {
        return sourceLongitude;
    }

    public void setSourceLongitude(Long sourceLongitude) {
        this.sourceLongitude = sourceLongitude;
    }

    public Long getDestination() {
        return destination;
    }

    public void setDestination(Long destination) {
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