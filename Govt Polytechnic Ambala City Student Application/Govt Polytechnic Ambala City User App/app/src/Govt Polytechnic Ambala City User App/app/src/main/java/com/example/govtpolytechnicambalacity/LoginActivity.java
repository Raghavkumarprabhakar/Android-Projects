package com.example.govtpolytechnicambalacity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private TextView openReg , openForgetPassword;
    private EditText logEmail , loginPassword;
    private Button loginBtn;
    private String email, password;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_login );

        openReg = findViewById ( R.id.openReg );
        logEmail = findViewById ( R.id.loginEmail );
        loginPassword = findViewById ( R.id.loginPassword );
        loginBtn = findViewById ( R.id.loginBtn );
        auth = FirebaseAuth.getInstance ();
        openForgetPassword =  findViewById ( R.id.openForgetPass );
        openReg.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                openRegister();
            }
        } );
        loginBtn.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                validateData();
            }
        } );
        openForgetPassword.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity ( new Intent (LoginActivity.this,ForgetPassword.class) );
            }
        } );
    }

    private void validateData() {
        email = logEmail.getText ().toString ();
        password = loginPassword.getText ().toString ();
        if (email.isEmpty () || password.isEmpty ()){
            Toast.makeText ( this, "please all fields", Toast.LENGTH_SHORT ).show ();
        }else {
            loginUser();
        }
    }

    private void loginUser() {
        auth.signInWithEmailAndPassword ( email,password )
                .addOnCompleteListener ( new OnCompleteListener<AuthResult> () {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful ()){
                            openMain();

                        }else {
                            Toast.makeText ( LoginActivity.this, "Error:"+task.getException ().getMessage (), Toast.LENGTH_SHORT ).show ();
                        }


                    }
                } ).addOnFailureListener ( new OnFailureListener () {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText ( LoginActivity.this, "Error:"+e.getMessage (), Toast.LENGTH_SHORT ).show ();

                    }
                } );
    }

    private void openMain() {
        startActivity ( new Intent (LoginActivity.this, MainActivity.class) );
        finish ();
    }

    private void openRegister() {
        startActivity ( new Intent (LoginActivity.this, RegisterActivity.class) );
        finish ();
    }
}