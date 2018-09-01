package com.example.user.minipro5997secondapp.controller;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.user.minipro5997secondapp.R;

public class ExitFragment extends Fragment implements View.OnClickListener{

    View view;
    Button exit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.exit_layout,container,false);

        getActivity().setTitle("Exit");

        exit = view.findViewById(R.id.exitB);
        exit.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory( Intent.CATEGORY_HOME );
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);

        getActivity().finish();
        System.exit(0);
    }
}
