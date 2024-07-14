package com.example.govtpolytechnicambalaadmin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UpdateFaculty extends AppCompatActivity {

    private RecyclerView csDepartment;
    private LinearLayout csNoData;
    private List<TeacherData> list1;
    FloatingActionButton fab;

    private DatabaseReference reference , dbRef;
    private TeacherAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_faculty);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> startActivity(new Intent(UpdateFaculty.this,AddTeacher.class)));

        csDepartment = findViewById ( R.id.csDepartment );
        csNoData = findViewById ( R.id.csNoData );

        reference = FirebaseDatabase.getInstance ().getReference ().child ( "teachers" );
        csDepartment();



    }

    private void csDepartment() {
        dbRef = reference.child ( "Computer Branch" );
        dbRef.addValueEventListener ( new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list1 = new ArrayList<> ();
                if (!dataSnapshot.exists ()){
                    csNoData.setVisibility ( View.VISIBLE );
                    csDepartment.setVisibility ( View.GONE );

                }else {
                    csNoData.setVisibility ( View.GONE );
                    csDepartment.setVisibility ( View.VISIBLE );

                    for (DataSnapshot snapshot: dataSnapshot.getChildren ()){
                        TeacherData data = snapshot.getValue (TeacherData.class);
                        list1.add ( data );

                    }
                    csDepartment.setHasFixedSize ( true );
                    csDepartment.setLayoutManager ( new LinearLayoutManager ( UpdateFaculty.this ) );
                    adapter = new TeacherAdapter ( list1,UpdateFaculty.this,"Computer Branch" );
                    csDepartment.setAdapter ( adapter );
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText ( UpdateFaculty.this,databaseError.getMessage (), Toast.LENGTH_SHORT ).show ();

            }
        } );
    }

}