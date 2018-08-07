package com.example.user.minipro5997secondapp.model.datasource;

import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;
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

import java.util.List;

public class Firebase_DBManager implements Backend {

    private DatabaseReference clientsRequestRef = FirebaseDatabase.getInstance().getReference("clients");
    private DatabaseReference driversRef = FirebaseDatabase.getInstance().getReference("drivers");
    private FirebaseAuth driverAuth;

    private Driver dataToDriver(DataSnapshot dataSnapshot){
        return new Driver(
                dataSnapshot.child("name").getValue().toString(),
                dataSnapshot.child("email").getValue().toString(),
                dataSnapshot.child("password").getValue().toString(),
                dataSnapshot.child("id").getValue().toString()
        );
    }
    private ClientRequest dataToCLientRequest(DataSnapshot dataSnapshot){
        return new ClientRequest(
                dataSnapshot.child("name").getValue().toString(),
                dataSnapshot.child("phone").getValue().toString(),
        dataSnapshot.child("email").getValue().toString(),
                (ClientRequestStatus)dataSnapshot.child("ClientRequestStatus")
                        .getValue()

                //TODO
        private String name;
        private String phone;
        private String email;
        private ClientRequestStatus status;
        private Location source;
        private Location destination;

        )
    }


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
                .equalTo(email+"___"+password).limitToFirst(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                driver[0] = dataToDriver(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //TODO
                return;
            }
        });
        return driver[0];
    }

    @Override
    public List<ClientRequest> getRequest(int numRequest) {
        final List<ClientRequest> clientRequests;
        Query query = clientsRequestRef.limitToFirst(numRequest);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                clientRequests =
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
