package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class Newhome extends AppCompatActivity {
    String str;
    private EditText editTextTitle;
    private EditText editTextDescription;
    private NumberPicker numberPickerPriority;
    ImageView save_not1;

    FirebaseFirestore fstore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newhome);
        //Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("HOME MAINTENANCE");
        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        numberPickerPriority = findViewById(R.id.number_picker_priority);
        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(1000);

        Intent intent = getIntent();
        str = intent.getStringExtra("Emailid");

        save_not1= findViewById(R.id.button4);

        save_not1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });

    }
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.new_note_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/
    private void saveNote() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        int priority = numberPickerPriority.getValue();
        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        CollectionReference notebookRef = fstore.collection("DataBot").document(str).collection("HomeMain");
        notebookRef.add(new Note1(title, description, priority));
        Toast.makeText(this, "ADDED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
        finish();
    }



    public void Clicked(View view)
    {

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        sendIntent.putExtra(Intent.EXTRA_TEXT,editTextDescription.getText().toString());
        sendIntent.setType("text/plain");
        Intent.createChooser(sendIntent,"Share via");
        startActivity(sendIntent);
    }
}