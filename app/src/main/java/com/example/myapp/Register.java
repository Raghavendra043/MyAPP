package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    public static final String TAG = "TAG";
    public static final String TAG1 = "TAG";
    TextView rregister,rgotologin;
    EditText rname, remail, rpassword;
    Button rregister1;
    FirebaseAuth fauth;
    FirebaseFirestore fstore;
    String UserID,remailID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        rregister =(TextView)findViewById(R.id.register);
        rgotologin =(TextView)findViewById(R.id.textView);
        rgotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });

        rname =(EditText)findViewById(R.id.editname);
        remail =(EditText)findViewById(R.id.editemailid);
        rpassword =(EditText)findViewById(R.id.editpassword);
        rregister1 =(Button)findViewById(R.id.button3);
        fstore = FirebaseFirestore.getInstance();
        fauth = FirebaseAuth.getInstance();

        /*if(fauth.getCurrentUser() != null){
            Toast.makeText(Register.this,"An account Linked with this MAilID",Toast.LENGTH_SHORT).show();
            //login
        }*/

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner1, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        rregister1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rname1 = rname.getText().toString();
                remailID = remail.getText().toString();
                String rpassword1 = rpassword.getText().toString();

                String regex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
                boolean result = remailID.matches(regex);
                Drawable errorIcon = getResources().getDrawable(R.drawable.error);
                errorIcon.setBounds(new Rect(0, 0, errorIcon.getIntrinsicWidth(), errorIcon.getIntrinsicHeight()));
                if( (TextUtils.isEmpty(rname1)) | (TextUtils.isEmpty(rpassword1)) |  (TextUtils.isEmpty(remailID))){
                    rname.setError("Enter name",errorIcon);

                }

                if(result!=true){
                    remail.setError("Enter valid emailID",errorIcon);
                }
                else{
                    fauth.createUserWithEmailAndPassword(remailID,rpassword1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                fauth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task1) {
                                        if(task1.isSuccessful()){
                                            Toast.makeText(Register.this,"Registered Successfully. Open Email to Verify your account",Toast.LENGTH_SHORT).show();

                                            //UserID = fauth.getCurrentUser().getUid();
                                            DocumentReference documentReference = fstore.collection("DataBot").document(remailID);
                                            documentReference.collection("Notes");
                                            documentReference.collection("Dairy");
                                            documentReference.collection("Bills");
                                            Map<String,Object> user = new HashMap<>();
                                            user.put("name",rname1);
                                            user.put("pass",rpassword1);
                                            user.put("emalID",remailID);
                                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d(TAG,"onSuccess: user prof created");
                                                    startActivity(new Intent(getApplicationContext(),PhoneNumber.class));
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.d(TAG1,"Onfailure: failed"+e.toString());
                                                }
                                            });
                                            //startActivity(new Intent(getApplicationContext(),Login.class));
                                            //finish();
                                        }else {
                                                Toast.makeText(Register.this, "Error!"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            }
                            else {
                                Toast.makeText(Register.this,"Error inlogin",Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }

            }
        });




    }
}