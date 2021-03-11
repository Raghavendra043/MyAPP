package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Groceries extends AppCompatActivity implements View.OnClickListener{

    EditText etsearch;
    ImageView btsearch;
    RecyclerView recyclerview;
    List<String> itemList = new ArrayList<>();
    CustomAdapter adapter;
    String name;
    String i_price;
    ImageView imageView;
    String amount;
    String str;
    private static final int PICK_IMAGE = 100;
    String id;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    CollectionReference cr = firestore.collection("DataBot");
    String arr[] = new String[100];
    int count=-1;


    Button btnotif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groceries);

        Intent intent = getIntent();
        str = intent.getStringExtra("Emailid");

        CollectionReference fr = cr.document(str).collection("Groceries");

        etsearch = findViewById(R.id.et_search);
        btsearch = findViewById(R.id.bt_search);
        recyclerview = findViewById(R.id.recycler_view);



        recyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new CustomAdapter(itemList);
        recyclerview.setAdapter(adapter);


        amount = adapter.getAmount();
        name = adapter.getName();
        i_price = adapter.getI_price();



        btsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count=count+1;
                String s = etsearch.getText().toString();
                if(s.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Enter an item name",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        String string = etsearch.getText().toString();

                        Map<String,Object> user = new HashMap<>();
                        user.put("item",string);
                        user.put("quantity",amount);
                        user.put("price",i_price);
                        id = fr.document().getId();
                        arr[count] = id;
                        fr.document(id).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Groceries.this, "added success", Toast.LENGTH_SHORT).show();
                                itemList.add(string);
                                etsearch.setText("");
                                adapter.notifyItemInserted(itemList.size() - 1);

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });




                    }
                    catch (NumberFormatException e){

                    }
                }

            }
        });


        btnotif = findViewById(R.id.bt_notif);



        btnotif.setVisibility(View.INVISIBLE);




        btnotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Set notificationId & text.
                Intent intent = new Intent(Groceries.this, Notification_receiver.class);
                int notificationId = 1;
                intent.putExtra("notificationId", notificationId);


                // getBroadcast(context, requestCode, intent, flags)
                PendingIntent alarmIntent = PendingIntent.getBroadcast(Groceries.this, 0,
                        intent, PendingIntent.FLAG_CANCEL_CURRENT);

                //  Intent intback = new Intent(getApplicationContext(), RemaindersActivity.class);


                AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);

                switch (view.getId()) {
                    case R.id.bt_notif:

                        //  intback.putExtra("hour", hour);
                        //  intback.putExtra("minutes", minute);
                        //  intback.putExtra("event_name", editText.getText().toString());
                        //  String time = hour + " : " + minute;
                        // String name = editText.getText().toString();
                        //  Rems.put(name, time);
                        //  db.collection("Project").document("Reminders").set(Rems, SetOptions.merge());
                        // Create time.
                        Calendar startTime = Calendar.getInstance();
                        startTime.set(Calendar.HOUR_OF_DAY, 22);
                        startTime.set(Calendar.MINUTE, 54);
                        startTime.set(Calendar.SECOND, 2);
                        long alarmStartTime = startTime.getTimeInMillis();

                        // Set alarm.
                        // set(type, milliseconds, intent)
                        alarm.set(AlarmManager.RTC_WAKEUP, alarmStartTime, alarmIntent);
                        Toast.makeText(Groceries.this, "Notification sent", Toast.LENGTH_SHORT).show();

                }
            }
        });

        btnotif.callOnClick();

    }




    public void Clicked(View view) {

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        sendIntent.putExtra(Intent.EXTRA_TEXT, etsearch.getText().toString());
        sendIntent.setType("text/plain");
        Intent.createChooser(sendIntent,"Share via");
        startActivity(sendIntent);
    }

    @Override
    public void onClick(View v) {

    }









}