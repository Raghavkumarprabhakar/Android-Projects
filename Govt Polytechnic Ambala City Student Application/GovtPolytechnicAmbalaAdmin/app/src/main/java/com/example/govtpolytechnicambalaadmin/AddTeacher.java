package com.example.govtpolytechnicambalaadmin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class AddTeacher extends AppCompatActivity {

    private ImageView addTeacherImage;
    private EditText addTeacherName , addTeacherEmail , addTeacherPost ;
    private Button addTeacherBtn;
    private Spinner addTeacherCategory;
    private final int REQ=1;
    private String category;
    private Bitmap bitmap = null;
    private String name,email,post,downloadUrl="";
    private ProgressDialog pd;
    private StorageReference storageReference;
    private DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);

        addTeacherName = findViewById(R.id.addTeacherName);
        addTeacherEmail = findViewById(R.id.addTeacherEmail);
        addTeacherPost= findViewById(R.id.addTeacherPost);
        addTeacherImage = findViewById(R.id.addTeacherImage);
        addTeacherBtn = findViewById(R.id.addTeacherButton);
        addTeacherCategory = findViewById(R.id.addTeacherCategory);
        pd = new ProgressDialog(this);
        storageReference = FirebaseStorage.getInstance().getReference();
        reference = FirebaseDatabase.getInstance().getReference().child("teachers");

        String[] items= new String[]{"Select Category","Applied Science","Computer Branch", "Mechanical","Electronics","Electrical","Plastic","Civil","Architecture","Automobile"};
        addTeacherCategory.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,items));
        addTeacherCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = addTeacherCategory.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addTeacherImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        addTeacherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });



    }

    private void checkValidation() {
        name = addTeacherName.getText().toString();
        email = addTeacherEmail.getText().toString();
        post = addTeacherPost.getText().toString();

        if (name.isEmpty()){
            addTeacherName.setError("Empty");
            addTeacherName.requestFocus();
        }else if (email.isEmpty()){
            addTeacherEmail.setError("Empty");
            addTeacherEmail.requestFocus();

        }else if (post.isEmpty()){
            addTeacherPost.setError("Empty");
            addTeacherPost.requestFocus();
        }else if (category.equals("Select Category")){
            Toast.makeText(this, "Please Select Category", Toast.LENGTH_SHORT).show();


        }else if (bitmap==null){
            insertData();
        }else{
            pd.setTitle("Please Wait");
            pd.setMessage("Uploading");
            pd.show();
            uploadImage();
        }
    }


    private void uploadImage() {
        pd.setTitle("Please wait");
        pd.setMessage("Uploading");
        pd.show();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);
        byte[] finalImg = byteArrayOutputStream.toByteArray();
        final StorageReference filePath;
        filePath = storageReference.child("teachers").child(finalImg+"jpg");
        final UploadTask uploadTask = filePath.putBytes(finalImg);
        uploadTask.addOnCompleteListener(AddTeacher.this, task -> {
            if (task.isSuccessful()){
                uploadTask.addOnSuccessListener(taskSnapshot -> filePath.getDownloadUrl().addOnSuccessListener(uri -> {
                    downloadUrl = String.valueOf(uri);
                    insertData();

                }));
            }else {
                pd.dismiss();
                Toast.makeText(AddTeacher.this, "Something went Wrong", Toast.LENGTH_SHORT).show();
            }

        });

    }

    private void insertData() {
        reference = reference.child(category);
        final String uniqueKey = reference.push().getKey();


        TeacherData teacherData = new TeacherData(name,email,post,downloadUrl,uniqueKey);

        reference.child(uniqueKey).setValue(teacherData).addOnSuccessListener(unused -> {
            pd.dismiss();
            Toast.makeText(AddTeacher.this, "Teacher Added", Toast.LENGTH_SHORT).show();

        }).addOnFailureListener(e -> {
            pd.dismiss();
            Toast.makeText(AddTeacher.this, "Something went wrong", Toast.LENGTH_SHORT).show();
        });
    }

    private void openGallery() {
        Intent pickImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickImage,REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ && resultCode==RESULT_OK){
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            addTeacherImage.setImageBitmap(bitmap);



        }
    }

}