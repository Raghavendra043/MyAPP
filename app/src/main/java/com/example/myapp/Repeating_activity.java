package com.example.myapp;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Repeating_activity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repeating_activity_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set Onclick Listener.
        findViewById(R.id.bt_notif).setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {


    }
}
