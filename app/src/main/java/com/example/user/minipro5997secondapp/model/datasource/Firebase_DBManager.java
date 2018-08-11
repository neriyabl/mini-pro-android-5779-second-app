package com.example.user.minipro5997secondapp.model.datasource;

import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.user.minipro5997secondapp.model.backend.Backend;
import com.example.user.minipro5997secondapp.model.entities.ClientRequest;
import com.example.user.minipro5997secondapp.model.entities.ClientRequestStatus;
import com.example.user.minipro5997secondapp.model.entities.Driver;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static android.content.ContentValues.TAG;

public class Firebase_DBManager implements Backend {

    private DatabaseReference clientsRequestRef = FirebaseDatabase.getInstance().getReference("clients");
    private DatabaseReference driversRef = FirebaseDatabase.getInstance().getReference("drivers");
    private FirebaseAuth driverAuth;


    /**
     * format the data from firebase to Driver user
     *
     * @param dataSnapshot the firebase ref
     * @return new Driver
     */
    private Driver dataToDriver(DataSnapshot dataSnapshot) {
        return new Driver(
                dataSnapshot.child("name").getValue().toString(),
                dataSnapshot.child("email").getValue().toString(),
                dataSnapshot.child("password").getValue().toString(),
                dataSnapshot.child("id").getValue().toString()
        );
    }

    /**
     * format the data from firebase to client request user
     *
     * @param dataSnapshot the firebase ref
     * @return new client request
     */
    private ClientRequest dataToCLientRequest(DataSnapshot dataSnapshot) {
        ClientRequest request = new ClientRequest(
                dataSnapshot.child("name").getValue().toString(),
                dataSnapshot.child("phone").getValue().toString(),
                dataSnapshot.child("email").getValue().toString(),
                (ClientRequestStatus) dataSnapshot.child("status").getValue(),
                (Location) dataSnapshot.child("source").getValue(),
                (Location) dataSnapshot.child("destination").getValue()
        );
        if (request.getStatus() == ClientRequestStatus.in_progress || request.getStatus() == ClientRequestStatus.close) {
            request.setStartDrive((Date) dataSnapshot.child("startDrive").getValue());
        }
        if (request.getStatus() == ClientRequestStatus.close) {
            request.setEndDrive((Date) dataSnapshot.child("endDrive").getValue());
        }
        return request;
    }

    /**
     * sort the client requests by distance from the driver
     * @param driverLocation the driver location
     * @param requests the list of all client requests
     * @return new sorted list
     */
    private List<ClientRequest> sortByDistance(Location driverLocation, List<ClientRequest> requests){
        Map<Double, ClientRequest> distanceMap = new HashMap<>();
        double distance;
        for (ClientRequest request : requests) {
            distance = driverLocation.distanceTo(request.getSource());
            distanceMap.put(distance,request);
        }
        return (List<ClientRequest>) (new TreeMap<>(distanceMap)).values();
    }


    // ------ implement's methods -----
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
        final Driver[] driver = {null};

        Query query = driversRef.orderByChild("email___password")
                .equalTo(email + "___" + password).limitToFirst(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                driver[0] = dataToDriver(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                driver[0] = null;
            }
        });
        return driver[0];
    }


    //TODO sort by distance
    @Override
    public List<ClientRequest> getRequest(Location driverLocation, int numRequest) {

        final List<ClientRequest> requests = clientsRequestRef.
    }

    @Override
    public List<ClientRequest> getRequest(Location driverLocation, int numRequest, int distance) {
        return null;
    }

    @Override
    public List<ClientRequest> getRequest(Location driverLocation, int numRequest, ClientRequestStatus status) {
        return null;
    }

    @Override
    public List<ClientRequest> getRequest(Location driverLocation, int numRequest, int distance, ClientRequestStatus status) {
        return null;
    }


}
