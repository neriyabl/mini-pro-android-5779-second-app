package com.example.user.minipro5997secondapp.model.datasource;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.user.minipro5997secondapp.model.backend.Backend;
import com.example.user.minipro5997secondapp.model.entities.ClientRequest;
import com.example.user.minipro5997secondapp.model.entities.ClientRequestStatus;
import com.example.user.minipro5997secondapp.model.entities.Driver;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class Firebase_DBManager implements Backend {

    private DatabaseReference clientsRequestRef = FirebaseDatabase.getInstance().getReference("clients");
    private DatabaseReference driversRef = FirebaseDatabase.getInstance().getReference("drivers");
    private FirebaseAuth driverAuth;


    @Override
    public void addDriver(Driver driver, final Context context) {
        driversRef.push().setValue(driver).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(context, "Success to add the request", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Fail to add the request", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public Driver getDriver(String email, String password) {

        return null;
    }

    @Override
    public List<ClientRequest> getRequest(int numRequest) {
        return null;
    }

    @Override
    public List<ClientRequest> getRequest(int numRequest, int distance) {
        return null;
    }

    @Override
    public List<ClientRequest> getRequest(int numRequest, ClientRequestStatus status) {
        return null;
    }

    @Override
    public List<ClientRequest> getRequest(int numRequest, int distance, ClientRequestStatus status) {
        return null;
    }
}
