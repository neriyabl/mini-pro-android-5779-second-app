package com.example.user.minipro5997secondapp.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.user.minipro5997secondapp.R;
import com.example.user.minipro5997secondapp.model.backend.Backend;
import com.example.user.minipro5997secondapp.model.backend.BackendFactory;
import com.example.user.minipro5997secondapp.model.entities.Driver;

public class RegisterActivity extends AppCompatActivity {


    Backend backend = BackendFactory.getBackend();

    private EditText name;
    private EditText email;
    private EditText password;
    private EditText id;

    private Button cancel;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        id = findViewById(R.id.id);

        cancel = findViewById(R.id.cancel);
        save = findViewById(R.id.save);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backend.addDriver(new Driver(name.getText().toString(),
                        email.getText().toString(), password.getText().toString(),
                        id.getText().toString()),getApplicationContext());
            }
        });
    }
}
