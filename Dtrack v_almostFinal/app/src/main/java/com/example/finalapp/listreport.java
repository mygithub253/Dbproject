package com.example.finalapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class listreport extends AppCompatActivity {
    ListView listView;
    FirebaseDatabase database;
    DatabaseReference ref;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;                       //initialising variables
    Bio bio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listreport);
        listView = (ListView) findViewById(R.id.listview);
        database = FirebaseDatabase.getInstance();
        Intent intent=getIntent();
        String key=intent.getExtras().getString("unq");
        ref = database.getReference("Users").child(key).child("data");
        list = new ArrayList<>();                                               //creating empty arraylist
        bio = new Bio();                                    //using bio.java class to get glucose, bp , weight and date stamp from firebase

        adapter = new ArrayAdapter<String>(this, R.layout.user_info, R.id.userInfo, list);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int i=1;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    bio = ds.getValue(Bio.class);                                   //converting data from firebase to string and diplaying it in listview

                    list.add(String.valueOf(i)+") " +  bio.getDate().toString() + "\n     Blood Glucose : " + bio.getBg().toString() + "\n     Blood Pressure : " + bio.getBp().toString() + "\n     Weight : " + bio.getWeight().toString());
                    //list.add(String.valueOf(i)+") Blood Glucose : " + bio.getBg().toString() + "\n     Blood Pressure : " + bio.getBp().toString() + "\n     Weight : " + bio.getWeight().toString());
                    i+=1;
                }
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
