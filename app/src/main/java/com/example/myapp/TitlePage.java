package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class TitlePage extends AppCompatActivity {

    TextView ttitle, tdes;
    private static final int time= 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_page);

        ttitle = (TextView)findViewById(R.id.textView2);
        tdes = (TextView)findViewById(R.id.textView3);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent1 = new Intent(TitlePage.this, Login.class);
                startActivity(intent1);
                finish();
            }
        },time);
    }
}