package com.example.user.minipro5997secondapp.model.backend;

import android.content.Context;

import com.example.user.minipro5997secondapp.model.entities.ClientRequest;
import com.example.user.minipro5997secondapp.model.entities.ClientRequestStatus;
import com.example.user.minipro5997secondapp.model.entities.Driver;

import java.util.List;

public interface Backend {
    void addDriver(Driver driver, Context context);

    Driver getDriver(String email, String password);

    List<ClientRequest> getRequest(int numRequest);

    List<ClientRequest> getRequest(int numRequest, int distance);

    List<ClientRequest> getRequest(int numRequest, ClientRequestStatus status);

    List<ClientRequest> getRequest(int numRequest, int distance, ClientRequestStatus status);
}
