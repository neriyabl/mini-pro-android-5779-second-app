package com.example.user.minipro5997secondapp.controller;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.user.minipro5997secondapp.R;
import com.example.user.minipro5997secondapp.controller.Adapters.RequestAdapter;
import com.example.user.minipro5997secondapp.model.backend.Backend;
import com.example.user.minipro5997secondapp.model.backend.BackendFactory;
import com.example.user.minipro5997secondapp.model.entities.ClientRequest;
import com.example.user.minipro5997secondapp.model.entities.Driver;

import java.util.List;

@SuppressLint("ValidFragment")
public class RequestsFragment extends Fragment {

    View view;
    RecyclerView recyclerView;
    RequestAdapter adapter;
    Spinner filter;
    Backend backend = BackendFactory.getBackend();
    Driver driver;

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

        // ---- set the filter ----
        filter = view.findViewById(R.id.spinner);
        String[] items = new String[]{"all", "1000", "500", "250", "100", "50"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        filter.setAdapter(adapter);

        filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Location location = new Location("gps");
                location.setLatitude(31.77);
                location.setLongitude(35.177);

                new RequestTask().execute(location);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return view;
    }

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
}
