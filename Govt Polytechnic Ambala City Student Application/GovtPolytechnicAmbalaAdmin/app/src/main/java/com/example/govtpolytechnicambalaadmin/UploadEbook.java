package com.example.govtpolytechnicambalaadmin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class UploadEbook extends AppCompatActivity {

    private CardView addEbook;
    private EditText ebookTitle;
    private Button uploadEbookBtn;
    private final int REQ = 1;
    private TextView ebookTextView;
    private Uri ebookData;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    String downloadUrl = "";
    private ProgressDialog pd;
    private String ebookName ,title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_ebook);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();


        addEbook = findViewById(R.id.addEbook);
        ebookTitle = findViewById(R.id.ebookTitile);
        uploadEbookBtn = findViewById(R.id.uploadEbookBtn);
        pd= new ProgressDialog(this);
        ebookTextView = findViewById(R.id.ebookTextView);


        addEbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();

            }
        });

        uploadEbookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = ebookTitle.getText().toString();
                if(title.isEmpty()){
                    ebookTitle.setError("Empty");
                    ebookTitle.requestFocus();

                }else if(ebookData==null){
                    Toast.makeText(UploadEbook.this, "Please upload pdf", Toast.LENGTH_SHORT).show();
                }else {
                    uploadEbook();
                }
            }
        });

    }

    private void uploadEbook(){
        pd.setTitle("Please wait");
        pd.setMessage("Uploading");
        pd.show();
        StorageReference reference = storageReference.child("pdf/"+ebookName+"-"+System.currentTimeMillis()+"-pdf");
        reference.putFile(ebookData)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isComplete());
                        Uri uri = uriTask.getResult();
                        uploadData(String.valueOf(uri));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UploadEbook.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void uploadData(String downloadUrl) {
        String uniqueKey = databaseReference.child("ebook").push().getKey();
        HashMap data = new HashMap();
        data.put("ebookTitle",title);
        data.put("ebookUrl", downloadUrl);

        databaseReference.child("ebook").child(uniqueKey).setValue(data).addOnCompleteListener(task -> {
            pd.dismiss();
            Toast.makeText(UploadEbook.this, "Ebook uploaded sucessffully", Toast.LENGTH_SHORT).show();

        }).addOnFailureListener(new OnFailureListener() {
            @Override

            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(UploadEbook.this, "Failed To Upload Ebook " , Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("pdf/docs/ppt");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Pdf File"),REQ);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ && resultCode == RESULT_OK) {

            ebookData = data.getData();
            if (ebookData.toString().startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = UploadEbook.this.getContentResolver().query(ebookData, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        ebookName = cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME));

                    }
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }

            } else if (ebookData.toString().startsWith("file://")) {
                ebookName = new File(ebookData.toString()).getName();

            }
            ebookTextView.setText(ebookName);


        }
    }

}