package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

public class NewBills extends AppCompatActivity {

    String str;
    private EditText editTextTitle;
    private EditText editTextDescription;
    private NumberPicker numberPickerPriority;
    ImageView save_not1;
    ImageView billsImage;
    private static final int PICK_IMAGE = 100;
    Uri imageuri;

    FirebaseFirestore fstore=FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_bills);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        //setTitle("APPLIANCES");
        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        numberPickerPriority = findViewById(R.id.number_picker_priority);
        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(1000);

        billsImage= findViewById(R.id.billsimage);
        billsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        Intent intent = getIntent();
        str = intent.getStringExtra("Emailid");

        save_not1= findViewById(R.id.button2);

        save_not1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });
    }

    private void saveNote() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        int priority = numberPickerPriority.getValue();
        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "CANNOT BE EMPTY", Toast.LENGTH_SHORT).show();
            return;
        }
        CollectionReference notebookRef =fstore.collection("DataBot").document(str).collection("Bills");

        notebookRef.add(new Note3(title, description, priority));
        Toast.makeText(this, "ADDED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
        finish();
    }

    //image from gallery source
    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageuri = data.getData();
            billsImage.setImageURI(imageuri);
        }
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