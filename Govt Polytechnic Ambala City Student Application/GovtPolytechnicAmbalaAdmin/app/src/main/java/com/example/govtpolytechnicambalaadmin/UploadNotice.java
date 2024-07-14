package com.example.govtpolytechnicambalaadmin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class UploadNotice extends AppCompatActivity {
    private CardView addImage;
    private final int REQ=1;
    private Bitmap bitmap;
    private ImageView noticeImageView;
    private Button uploadNoticeButton;
    private DatabaseReference reference;
    private StorageReference storageReference;
    private EditText noticeTitle;
    String downloadUrl= "";
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_notice);

        addImage = findViewById(R.id.addImage);
        noticeImageView = findViewById(R.id.noticeImageView);
        noticeTitle = findViewById(R.id.noticeTitile);
        uploadNoticeButton = findViewById(R.id.uploadNoticeBtn);
        reference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        pd = new ProgressDialog(this);

        addImage.setOnClickListener(view -> openGallery());

        uploadNoticeButton.setOnClickListener(v -> {
            if (noticeTitle.getText().toString().isEmpty()){
                noticeTitle.setError("Empty");
                noticeTitle.requestFocus();

            }else if (bitmap == null){
                uploadData();
            }else {
                uploadImage();

            }
        });

    }

    private void uploadImage() {
        pd.setTitle("Please wait");
        pd.setMessage("Uploading");
        pd.show();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);
        byte[] finalImg = byteArrayOutputStream.toByteArray();
        final StorageReference filePath;
        filePath = storageReference.child("Notice").child(finalImg+"jpg");
        final UploadTask uploadTask = filePath.putBytes(finalImg);
        uploadTask.addOnCompleteListener(UploadNotice.this, task -> {
            if (task.isSuccessful()){
                uploadTask.addOnSuccessListener(taskSnapshot -> filePath.getDownloadUrl().addOnSuccessListener(uri -> {
                    downloadUrl = String.valueOf(uri);
                    uploadData();

                }));
            }else {
                pd.dismiss();
                Toast.makeText(UploadNotice.this, "Something went Wrong", Toast.LENGTH_SHORT).show();
            }

        });

    }

    private void uploadData() {
        reference = reference.child("Notice");
        final String uniqueKey = reference.push().getKey();
        String title = noticeTitle.getText().toString();

        Calendar calFromDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yy");
        String date = currentDate.format(calFromDate.getTime());

        Calendar calFromTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        String time= currentTime.format(calFromTime.getTime());

        NoticeData noticeData = new NoticeData(title,downloadUrl,date,time,uniqueKey);

        reference.child(uniqueKey).setValue(noticeData).addOnSuccessListener(unused -> {
            pd.dismiss();
            Toast.makeText(UploadNotice.this, "Notice Uploaded", Toast.LENGTH_SHORT).show();

        }).addOnFailureListener(e -> {
            pd.dismiss();
            Toast.makeText(UploadNotice.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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
            noticeImageView.setImageBitmap(bitmap);



        }
    }
}