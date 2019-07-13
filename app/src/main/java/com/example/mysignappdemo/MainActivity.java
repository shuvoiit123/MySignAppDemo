package com.example.mysignappdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText signinEmailEdittext,signinPasswordEdittext;
    private Button signinButton;
    private TextView signupTextView;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        signinEmailEdittext = findViewById(R.id.signInEmailEdittextId);
        signinPasswordEdittext = findViewById(R.id.signInPasswordEdittextId);
        signinButton = findViewById(R.id.signinButtonId);
        signupTextView = findViewById(R.id.signupTextViewId);


        signinEmailEdittext.setOnClickListener(this);
        signinPasswordEdittext.setOnClickListener(this);
        signinButton.setOnClickListener(this);
        signupTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
           
            case R.id.signinButtonId:
                
                userLogIn();

                break;
            case R.id.signupTextViewId :

                Intent intent = new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(intent);
        }

    }

    private void userLogIn() {
        String email = signinEmailEdittext.getText().toString().trim();
        String password = signinPasswordEdittext.getText().toString().trim();

        if(email.isEmpty())
        {
            signinEmailEdittext.setError("Enter an email address");
            signinEmailEdittext.requestFocus();
            return;
        }

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            signinEmailEdittext.setError("Enter a valid email address");
            signinEmailEdittext.requestFocus();
            return;
        }

        //checking the validity of the password
        if(password.isEmpty())
        {
            signinPasswordEdittext.setError("Enter a password");
            signinPasswordEdittext.requestFocus();
            return;
        }
        if(password.length()<6)
        {
            signinPasswordEdittext.setError("minimum length of password should be 6");
            signinPasswordEdittext.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if(task.isSuccessful())
                {
                    finish();
                    Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }
                else{
                    Toast.makeText(getApplicationContext(),"Log in UnSuccessFull",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


}
