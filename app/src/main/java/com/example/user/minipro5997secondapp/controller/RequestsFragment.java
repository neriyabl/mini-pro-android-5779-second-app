package com.example.user.minipro5997secondapp.controller;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.user.minipro5997secondapp.R;
import com.example.user.minipro5997secondapp.controller.Adapters.RequestAdapter;
import com.example.user.minipro5997secondapp.model.backend.Backend;
import com.example.user.minipro5997secondapp.model.backend.BackendFactory;
import com.example.user.minipro5997secondapp.model.entities.ClientRequest;
import com.example.user.minipro5997secondapp.model.entities.Driver;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

@SuppressLint("ValidFragment")
public class RequestsFragment extends Fragment {

    View view;
    RecyclerView recyclerView;
    RequestAdapter adapter;
    Spinner filter;
    Backend backend = BackendFactory.getBackend();
    Driver driver;
    Location driverLocation;

    //the service provider to get the client location
    private FusedLocationProviderClient mFusedLocationClient;


    @SuppressLint("ValidFragment")
    RequestsFragment(Driver driver) {
        this.driver = driver;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.requests_layout, container, false);
        // ----- set the recycle view -----
        recyclerView = view.findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        getActivity().setTitle("MY REQUESTS");

        adapter = new RequestAdapter(view.getContext(), backend.getAllRequest(), driver);
        recyclerView.setAdapter(adapter);

        //get the last know location
        getLocation();

        // ---- set the filter ----
        filter = view.findViewById(R.id.spinner);
        String[] items = new String[]{"all", "500", "250", "100", "50"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        filter.setAdapter(adapter);

        filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Location location = new Location("gps");
                location.setLatitude(31.77);
                location.setLongitude(35.177);
                new RequestTask().execute(driverLocation!= null? driverLocation: location);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return view;
    }

    /**
     * async task to get the filtered or new requests list
     */
    @SuppressLint("StaticFieldLeak")
    class RequestTask extends AsyncTask<Location, List<ClientRequest>, Void> {
        List<ClientRequest> requests;

        @Override
        protected Void doInBackground(Location... locations) {
            if (filter.getSelectedItem().toString().equals("all")) {
                requests = backend.getAllRequest();
            } else {
                requests = backend.getRequest(locations[0], 100, Double.parseDouble(filter.getSelectedItem().toString()));
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            recyclerView.setAdapter(new RequestAdapter(view.getContext(), requests, driver));
        }
    }


    /**
     * get the last know location of the driver
     */
    private void getLocation() {
        // check the Permission and request permissions if needed
        if (ActivityCompat.checkSelfPermission(view.getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(view.getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]
                    {android.Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        //get Provider location from the user location services
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        //run the function on the background and add onSuccess listener
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSuccess(Location _location) {
                        // Got last known location. In some rare situations this can be null.
                        if (_location != null) {
                            List<Address> addresses;
                            //save the location
                            driverLocation = _location;
                        } else {
                            Toast.makeText(view.getContext(),"can't find your location",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
