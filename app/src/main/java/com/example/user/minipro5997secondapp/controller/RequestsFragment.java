package com.example.user.minipro5997secondapp.controller;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.minipro5997secondapp.R;
import com.example.user.minipro5997secondapp.controller.Adapters.RequestAdapter;
import com.example.user.minipro5997secondapp.model.backend.Backend;
import com.example.user.minipro5997secondapp.model.backend.BackendFactory;

public class RequestsFragment extends Fragment {

    View view;
    RecyclerView recyclerView;
    RequestAdapter adapter;

    Backend backend = BackendFactory.getBackend();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.requests_layout,container,false);
        // ----- set the recycle view -----
        recyclerView = view.findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        getActivity().setTitle("MY REQUESTS");

        adapter = new RequestAdapter(view.getContext(), backend.getAllRequest());
        recyclerView.setAdapter(adapter);

        return view;
    }
}
