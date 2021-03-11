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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class NewNoteActivity extends AppCompatActivity {
    private EditText editTextTitle;
    private EditText editTextDescription;
    private NumberPicker numberPickerPriority;
    String str1;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private static final int PICK_IMAGE = 100;
    ImageView imageadd;
    Uri image1;



    private ImageView save_not, back_not;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        //setTitle("Add Diary");
        editTextTitle = (EditText)findViewById(R.id.edit_text_title);
        editTextDescription = (EditText)findViewById(R.id.edit_text_description);
        numberPickerPriority = (NumberPicker)findViewById(R.id.number_picker_priority);
        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(1000);


        imageadd=findViewById(R.id.imageView);
        save_not = findViewById(R.id.save_note);
        back_not = findViewById(R.id.back_note);

        imageadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });



        Intent intent = getIntent();
        str1 = intent.getStringExtra("Emailid");

        save_not=(ImageView)findViewById(R.id.save_note);
        back_not =(ImageView)findViewById(R.id.back_note);

        save_not.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });

        back_not.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in1 = new Intent(NewNoteActivity.this,MainNotes.class);
                startActivity(in1);
                finish();
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
            Toast.makeText(this, "Please write Day Date Time and Your Diary", Toast.LENGTH_SHORT).show();
            return;
        }
        CollectionReference notebookRef =firestore.collection("DataBot").document(str1).collection("Dairybook");

        notebookRef.add(new Note(title, description, priority));
        Toast.makeText(this, "Diary added", Toast.LENGTH_SHORT).show();
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
            image1 = data.getData();
            imageadd.setImageURI(image1);
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