package com.example.govtpolytechnicambalacity.ui.gallery;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.govtpolytechnicambalacity.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class GalleryFragment extends Fragment {

    RecyclerView indiaRecycler,treeRecycler,nccRecycler,SportsRecycler,studyRecycler,otherRecycler;
    GalleryAdapter adapter;

    DatabaseReference reference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        indiaRecycler = view.findViewById(R.id.indiaRecycler);
        treeRecycler = view.findViewById(R.id.treeRecycler);
        nccRecycler = view.findViewById(R.id.nccRecycler);
        SportsRecycler = view.findViewById(R.id.SportsRecycler);
        studyRecycler = view.findViewById(R.id.studyRecycler);
        otherRecycler = view.findViewById(R.id.otherRecycler);

        reference = FirebaseDatabase.getInstance().getReference().child("Gallery");
        getindiaRecycler();
        gettreeRecycler();
        getnccRecycler();
        getSportsRecycler();
        getstudyRecycler();
        getotherRecycler();


        return  view;


    }

    private void getotherRecycler() {
        reference.child("Other Event").addValueEventListener(new ValueEventListener() {

            List<String> imageList= new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    String data = (String) snapshot.getValue();
                    imageList.add(data);
                }
                adapter = new GalleryAdapter(getContext(),imageList);
                otherRecycler.setLayoutManager(new GridLayoutManager(getContext(),3));
                otherRecycler.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {



            }
        });
    }

    private void getstudyRecycler() {
        reference.child("Study").addValueEventListener(new ValueEventListener() {

            List<String> imageList= new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    String data = (String) snapshot.getValue();
                    imageList.add(data);
                }
                adapter = new GalleryAdapter(getContext(),imageList);
                SportsRecycler.setLayoutManager(new GridLayoutManager(getContext(),3));
                SportsRecycler.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {



            }
        });

    }

    private void getSportsRecycler() {

        reference.child("Sports").addValueEventListener(new ValueEventListener() {

            List<String> imageList= new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    String data = (String) snapshot.getValue();
                    imageList.add(data);
                }
                adapter = new GalleryAdapter(getContext(),imageList);
                SportsRecycler.setLayoutManager(new GridLayoutManager(getContext(),3));
                SportsRecycler.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {



            }
        });


    }

    private void getnccRecycler() {
        reference.child("NCC").addValueEventListener(new ValueEventListener() {

            List<String> imageList= new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    String data = (String) snapshot.getValue();
                    imageList.add(data);
                }
                adapter = new GalleryAdapter(getContext(),imageList);
                nccRecycler.setLayoutManager(new GridLayoutManager(getContext(),3));
                nccRecycler.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {



            }
        });


    }

    private void gettreeRecycler() {
        reference.child("Tree Plantation").addValueEventListener(new ValueEventListener() {

            List<String> imageList= new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    String data = (String) snapshot.getValue();
                    imageList.add(data);
                }
                adapter = new GalleryAdapter(getContext(),imageList);
                treeRecycler.setLayoutManager(new GridLayoutManager(getContext(),3));
                treeRecycler.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {



            }
        });
    }

    private void getindiaRecycler() {
        reference.child("Independence Day").addValueEventListener(new ValueEventListener() {

            List<String> imageList= new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    String data = (String) snapshot.getValue();
                    imageList.add(data);
                }
                adapter = new GalleryAdapter(getContext(),imageList);
                indiaRecycler.setLayoutManager(new GridLayoutManager(getContext(),3));
                indiaRecycler.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {



            }
        });
    }
}