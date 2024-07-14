package com.example.govtpolytechnicambalacity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.exifinterface.media.ExifInterface;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.govtpolytechnicambalacity.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.auth.FirebaseAuthCredentialsProvider;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private EditText regName, regEmail, regPassword;
    private Button register;
    private String name , email , pass;
    private FirebaseAuth auth;
    private DatabaseReference reference;
    private DatabaseReference dbref;
    private ProgressDialog pd;
    private TextView openLog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_register );

        regName = findViewById ( R.id.regName );
        regEmail = findViewById ( R.id.regEmail );
        regPassword = findViewById ( R.id.regPassword );
        register = findViewById ( R.id.register );
        auth = FirebaseAuth.getInstance ();
        reference = FirebaseDatabase.getInstance ().getReference ();
        pd = new ProgressDialog ( this );
        openLog = findViewById ( R.id.openlog );


        register.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                validateData();
            }
        } );

        openLog.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                openLogin();
            }
        } );


    }

    private void openLogin() {

        startActivity ( new Intent (RegisterActivity.this,LoginActivity.class) );
        finish ();
    }

    @Override
    protected void onStart() {

        super.onStart ();

        if (auth.getCurrentUser () !=null){
            openMain();
        }
    }

    private void openMain() {
        startActivity ( new Intent (RegisterActivity.this, MainActivity.class) );
        finish ();

    }
    private void validateData(){
        name = regName.getText ().toString ();
        email = regEmail.getText ().toString ();
        pass = regPassword.getText ().toString ();

        if (name.isEmpty ()){
            regName.setError ( "Required" );
            regName.requestFocus ();

        }else if (email.isEmpty ()){
            regEmail.setError ( "Required" );
            regEmail.requestFocus ();
        }else if (pass.isEmpty ()){
            regPassword.setError ( "Required" );
            regPassword.requestFocus ();
        }else{
            createUser();
        }

    }
    private void createUser(){
        pd.setTitle ( "Please wait" );
        pd.setMessage ( "Creating user" );
        pd.show ();
        auth.createUserWithEmailAndPassword ( email,pass )
                .addOnCompleteListener ( new OnCompleteListener<AuthResult> () {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful ()){
                            pd.dismiss ();
                            uploadData();
                            
                        }else{
                            pd.dismiss ();
                            Toast.makeText ( RegisterActivity.this, "Error"+task.getException ().getMessage (), Toast.LENGTH_SHORT ).show ();
                        }
                    }
                } ).addOnFailureListener ( new OnFailureListener () {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss ();
                        Toast.makeText ( RegisterActivity.this, "Error:"+e.getMessage (), Toast.LENGTH_SHORT ).show ();
                    }
                } );

    }

    private void uploadData() {
        pd.setTitle ( "Please wait" );
        pd.setMessage ( "Uploading User Data" );
        pd.show ();
        dbref = reference.child ( "users" );
        String key = dbref.push ().getKey ();
        HashMap<String , String> user = new HashMap<> ();
        user.put ( "Key",key );
        user.put ( "name",name );
        user.put ( "email",email );

        dbref.child ( key ).setValue ( user )
                .addOnCompleteListener ( new OnCompleteListener<Void> () {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful ()){
                            pd.dismiss ();
                            Toast.makeText ( RegisterActivity.this, "User Created ", Toast.LENGTH_SHORT ).show ();
                        }else {
                            pd.dismiss ();
                            Toast.makeText ( RegisterActivity.this,task.getException ().getMessage (), Toast.LENGTH_SHORT ).show ();
                        }
                    }
                } ).addOnFailureListener ( new OnFailureListener () {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss ();
                        Toast.makeText ( RegisterActivity.this,e.getMessage (), Toast.LENGTH_SHORT ).show ();
                    }
                } );





    }
}