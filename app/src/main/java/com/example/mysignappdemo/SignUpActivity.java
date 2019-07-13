package com.example.mysignappdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignUpActivity extends AppCompatActivity implements OnClickListener {
    private EditText signupEmailEdittext,signupPasswordEdittext;
    private Button signupButton;
    private TextView signinTextView;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        signupEmailEdittext = findViewById(R.id.signupEmailEdittextId);
        signupPasswordEdittext = findViewById(R.id.signupPasswordEdittextId);
        signupButton = findViewById(R.id.signupButtonId);
        signinTextView = findViewById(R.id.signinTextViewId);
        progressBar = findViewById(R.id.signupProgreessBarId);

        signupEmailEdittext.setOnClickListener(this);
        signupPasswordEdittext.setOnClickListener(this);
        signupButton.setOnClickListener(this);
        signinTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {

            case R.id.signupButtonId:
                userRegester();
                break;
            case R.id.signinTextViewId :

                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
        }

    }

    private void userRegester() {
        String email = signupEmailEdittext.getText().toString().trim();
        String password = signupPasswordEdittext.getText().toString().trim();

        if(email.isEmpty())
        {
            signupEmailEdittext.setError("Enter an email address");
            signupEmailEdittext.requestFocus();
            return;
        }

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            signupEmailEdittext.setError("Enter a valid email address");
            signupEmailEdittext.requestFocus();
            return;
        }

        //checking the validity of the password
        if(password.isEmpty())
        {
            signupPasswordEdittext.setError("Enter a password");
            signupPasswordEdittext.requestFocus();
            return;
        }
        if(password.length()<6)
        {
            signupPasswordEdittext.setError("minimum length of password should be 6");
            signupPasswordEdittext.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);

                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information


                    finish();
                    Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                } else {
                   if(task.getException() instanceof FirebaseAuthUserCollisionException)
                   {
                       Toast.makeText(getApplicationContext(),"Usere is allready regesterd",Toast.LENGTH_SHORT).show();
                   }
                   else
                   {
                       Toast.makeText(getApplicationContext(),"Error : "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                   }

                }



            }
        });
    }

}
