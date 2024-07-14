package com.example.govtpolytechnicambalacity.ui.faculty;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.govtpolytechnicambalacity.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class FacultyFragment extends Fragment {


    private RecyclerView csDepartment;
    private LinearLayout csNoData;
    private List<TeacherData> list1;

    private DatabaseReference reference , dbRef;
    private TeacherAdapter adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_faculty, container, false);


        csDepartment = view.findViewById ( R.id.csDepartment );
        csNoData = view.findViewById ( R.id.csNoData );

        reference = FirebaseDatabase.getInstance ().getReference ().child ( "teachers" );
        csDepartment();

        return view;

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
                    csDepartment.setLayoutManager ( new LinearLayoutManager ( getContext () ) );
                    adapter = new TeacherAdapter ( list1,getContext ());
                    csDepartment.setAdapter ( adapter );
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText ( getContext (),databaseError.getMessage (), Toast.LENGTH_SHORT ).show ();

            }
        } );
    }
}

