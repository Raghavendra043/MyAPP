package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import static com.google.android.material.navigation.NavigationView.*;

public class MainActivity extends AppCompatActivity implements OnNavigationItemSelectedListener {

    private LinearLayout noteslayout,reminderlayout,grocerieslayout,billslayout,homelayout;
    private DrawerLayout drawer;
    TextView username,usermail;
    TextView page;
    TextView tryy;
    String str;
    //String Username, Emailid;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    CollectionReference cr = firestore.collection("DataBot");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Tool bar and mainpage

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer=findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle( this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //end of toolbar
        //username = findViewById(R.id.usernaeminfo);
        usermail  = findViewById(R.id.changemail);
        username = findViewById(R.id.changename);


        Intent intent = getIntent();
        str = intent.getStringExtra("Emailid");

        //if(!str.isEmpty()){
          //  usermail.setText(Emailid);}
        cr.document(str).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    String Username = documentSnapshot.getString("name");
                    String Emailid =  documentSnapshot.getString("emalID");
                    //page.setText(Emailid);
                    usermail.setText(Emailid);
                    username.setText(Username);

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Error in storing to database", Toast.LENGTH_SHORT).show();
            }
        });


        //button to specified actions

        noteslayout=(LinearLayout)findViewById(R.id.notesaddbutton);
        noteslayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent notesp = new Intent(MainActivity.this,MainNotes.class);
                notesp.putExtra("Emailid",str);
                startActivity(notesp);
            }
        });
        reminderlayout=(LinearLayout)findViewById(R.id.reminderaddbutton);
        reminderlayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent remsp = new Intent(MainActivity.this,RemindersMain.class);
                remsp.putExtra("Emailid",str);
                startActivity(remsp);

            }
        });
        grocerieslayout=(LinearLayout)findViewById(R.id.groceriesaddbutton);
        grocerieslayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent grop = new Intent(MainActivity.this,Groceries.class);
                grop.putExtra("Emailid",str);
                startActivity(grop);

            }
        });
        billslayout=(LinearLayout)findViewById(R.id.billsaddbutton);
        billslayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bils = new Intent(MainActivity.this,Bills.class);
                bils.putExtra("Emailid",str);
                startActivity(bils);
            }
        });
        homelayout=(LinearLayout)findViewById(R.id.homeappliances);
        homelayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homep = new Intent(MainActivity.this,Home.class);
                homep.putExtra("Emailid",str);
                startActivity(homep);
            }
        });

        //end og buttons to specified actions


    }


    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_message:{
                Intent intent = new Intent(MainActivity.this,Login.class);
                startActivity(intent);
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                //      new MessageFragment()).commit();
                break;}
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}