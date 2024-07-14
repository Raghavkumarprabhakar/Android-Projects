package com.example.govtpolytechnicambalacity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

public class FullImagaeView extends AppCompatActivity {

    private PhotoView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_imagae_view);

        imageView = findViewById(R.id.image_view);


        String image = getIntent().getStringExtra("image");

        Glide.with(this).load(image).into(imageView);


    }
}