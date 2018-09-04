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

/**
 * the exit from app fragment
 */
public class ExitFragment extends Fragment {

    View view;
    Button exit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.exit_layout, container, false);

        getActivity().setTitle("Exit");

        exit = view.findViewById(R.id.exitB);
        exit.setOnClickListener(v -> {
            //set intent to go the home screen of the user phone
            //because not all activity in the app closing in getActivity().finish() or System.exit(0)
            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory(Intent.CATEGORY_HOME);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //go to the home screen
            startActivity(homeIntent);

            //exit from this activity (return to login activity)
            getActivity().finish();
            //close the process
            System.exit(0);
        });

        return view;
    }


}
