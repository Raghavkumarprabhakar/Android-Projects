package com.example.govtpolytechnicambalaadmin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText userEmail, userPassword;
    private TextView tvShow;
    private RelativeLayout loginBtn;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private String email , pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_login );

        userEmail =  findViewById ( R.id.userEmail );
        userPassword = findViewById ( R.id.userPassword );
        tvShow = findViewById ( R.id.txt_show);
        loginBtn = findViewById ( R.id.login_btn );
        sharedPreferences = this.getSharedPreferences ( "Login",MODE_PRIVATE );
        editor = sharedPreferences.edit ();
        if (sharedPreferences.getString ( "isLogin" ,"false").equals ( "yes" )){
            openDash ();
        }

        tvShow.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if (userPassword.getInputType () == 144){
                    userPassword.setInputType(129);
                    tvShow.setText ("Hide");

                }else{
                    userPassword.setInputType ( 144 );
                    tvShow.setText ( "show" );
                }
                userPassword.setSelection ( userPassword.getText ().length () );
            }
        } );

        loginBtn.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                validateData();
            }
        } );
    }

    private void validateData() {
        email = userEmail.getText ().toString ();
        pass = userPassword.getText ().toString ();

        if(email.isEmpty ()){
            userEmail.setError ( "Required" );
            userEmail.requestFocus ();

        }else if (pass.isEmpty ()){
            userPassword.setError ( "Required" );
            userPassword.requestFocus ();
        }else if (email.equals ( "govt.polytechnic@gmail.com" )&& pass.equals ( "raghav@123" )){
            editor.putString ( "isLogin","yes" );
            editor.commit ();
            openDash();
        }else {
            Toast.makeText ( this, "Please Check Email and Password Again", Toast.LENGTH_LONG ).show ();
        }
    }

    private void openDash() {

        startActivity ( new Intent (LoginActivity.this,MainActivity.class) );
        finish ();

    }
}