package com.example.user.minipro5997secondapp.model.backend;

import android.content.Context;
import android.location.Location;

import com.example.user.minipro5997secondapp.model.entities.ClientRequest;
import com.example.user.minipro5997secondapp.model.entities.ClientRequestStatus;
import com.example.user.minipro5997secondapp.model.entities.Driver;

import java.util.List;

public interface Backend {
    void addDriver(Driver driver, Context context);

    Driver getDriver(Driver driver);

    List<ClientRequest> getAllRequest();

    List<ClientRequest> getRequest(Location driverLocation, int numRequest);

    List<ClientRequest> getRequest(Location driverLocation, int numRequest, double distance);

    List<ClientRequest> getRequest(Location driverLocation, int numRequest, ClientRequestStatus status);

    List<ClientRequest> getRequest(Location driverLocation, int numRequest, int distance, ClientRequestStatus status);
}
