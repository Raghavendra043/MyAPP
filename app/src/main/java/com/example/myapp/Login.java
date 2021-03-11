package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.util.*;

import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

public class Login extends AppCompatActivity{

    EditText memail, mpassword;
    Button mlogin;
    TextView title,lcreateacc;
    ImageButton mfacebook,mgoogle;
    String email;
    FirebaseAuth fAuth;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    CollectionReference cdf = firestore.collection("DataBot");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        memail = (EditText)findViewById(R.id.editTextTextPersonName);
        mpassword = (EditText)findViewById(R.id.editTextTextPersonName1);

        mlogin =(Button)findViewById(R.id.button);

        title =(TextView)findViewById(R.id.title);


        fAuth = FirebaseAuth.getInstance();
        /*if(fAuth.getCurrentUser() !=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }*/
        lcreateacc = (TextView)findViewById(R.id.lregister);
        lcreateacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,Register.class));
            }
        });

        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = memail.getText().toString().trim();
                String password = mpassword.getText().toString().trim();
                //String regex1 = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
                //boolean result1 = email.matches(regex1);
                Drawable errorIcon = getResources().getDrawable(R.drawable.error);
                errorIcon.setBounds(new Rect(0, 0, errorIcon.getIntrinsicWidth(), errorIcon.getIntrinsicHeight()));
                if( (TextUtils.isEmpty(email)) ){//| (result1!=true)){
                    memail.setError("Invalid Email",errorIcon);
                }
                if(TextUtils.isEmpty(password)){
                    mpassword.setError("Enter Password",errorIcon);
                }


                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            if(fAuth.getCurrentUser().isEmailVerified()){
                                String usermail = memail.getText().toString();
                                Toast.makeText(Login.this,"Login Sucessful",Toast.LENGTH_SHORT).show();
                                Intent intent345 = new Intent(getApplicationContext(),MainActivity.class);
                                intent345.putExtra("Emailid",usermail);
                                startActivity(intent345);


                                finish();
                            }else {
                                Toast.makeText(Login.this,"Please verify your account",Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Toast.makeText(Login.this,"Error!" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });






    }
}