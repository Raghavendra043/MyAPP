package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class RemindersMain extends AppCompatActivity {

    private RecyclerView recyclerview;
    private List<Reminder> itemList = new ArrayList<>();
    private CustomAdapter1 adapter;
    private FloatingActionButton fab;
    private Reminder temprem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders_main);

        fab = findViewById(R.id.fab);
        recyclerview = findViewById(R.id.recycler_view);

        recyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new CustomAdapter1(itemList);
        recyclerview.setAdapter(adapter);




        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Set notificationId & text.
                Intent intent = new Intent(RemindersMain.this, SetReminder.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        String event, time;
        int hour, min;
        hour = intent.getIntExtra("hour", 11);
        min = intent.getIntExtra("minutes", 59);
        if (min > 10) {
            time = hour + ":" + min;
        } else {
            time = hour + ":0" + min;
        }
        event = intent.getStringExtra("event_name");
        temprem = new Reminder(event, time);
        itemList.add(temprem);
        adapter.notifyItemInserted(itemList.size() - 1);

    }
}