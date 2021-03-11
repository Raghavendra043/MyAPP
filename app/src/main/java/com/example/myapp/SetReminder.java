package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SetReminder extends AppCompatActivity implements View.OnClickListener{

    Button setbtn, cnclbtn;
    TimePicker timePicker;
    EditText editText ;
    String mail = "raghavendra074743@gmail.com",id;

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    CollectionReference notef = firestore.collection("DataBot");
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_reminder);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set Onclick Listener.
        setbtn = findViewById(R.id.setbtn);
        cnclbtn = findViewById(R.id.cnclbtn);
        setbtn.setOnClickListener(this);
        cnclbtn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        editText = findViewById(R.id.edtxt_remname);
        timePicker = findViewById(R.id.timePicker);



        Intent intent = new Intent(SetReminder.this, Notification_receiver1.class);
        int notificationId = 1;
        intent.putExtra("notificationId", notificationId);
        intent.putExtra("todo", editText.getText().toString());

        // getBroadcast(context, requestCode, intent, flags)
        PendingIntent alarmIntent = PendingIntent.getBroadcast(SetReminder.this, 0,
                intent, PendingIntent.FLAG_CANCEL_CURRENT);

        Intent intback = new Intent(getApplicationContext(), RemindersMain.class);

        AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);

        switch (v.getId()) {
            case R.id.setbtn:
                CollectionReference fr = notef.document(mail).collection("Reminders");
                int hour = (int) timePicker.getCurrentHour();
                int minute = (int) timePicker.getCurrentMinute();
                intback.putExtra("hour", hour);
                intback.putExtra("minutes", minute);
                intback.putExtra("event_name", editText.getText().toString());
                String time = hour + " : " + minute;
                String name = editText.getText().toString();
                Map<String,Object> user = new HashMap<>();
                user.put("title",name);
                user.put("time",time);
                id = fr.document().getId();
                fr.document(id).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(SetReminder.this, "success", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
                //user.put("emalID",remailID);

                //  Rems.put(name, time);
                //  db.collection("Project").document("Reminders").set(Rems, SetOptions.merge());
                // Create time.
                Calendar startTime = Calendar.getInstance();
                startTime.set(Calendar.HOUR_OF_DAY, hour);
                startTime.set(Calendar.MINUTE, minute);
                startTime.set(Calendar.SECOND, 0);
                long alarmStartTime = startTime.getTimeInMillis();

                // Set alarm.
                // set(type, milliseconds, intent)
                alarm.set(AlarmManager.RTC_WAKEUP, alarmStartTime, alarmIntent);
                Toast.makeText(this, "Done!", Toast.LENGTH_SHORT).show();
                startActivity(intback);
                break;

            case R.id.cnclbtn:
                alarm.cancel(alarmIntent);
                Toast.makeText(this, "Canceled.", Toast.LENGTH_SHORT).show();
                break;
        }
        finish();
    }
}
