package com.example.finalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class mass extends AppCompatActivity {
    private Integer count;
    private TextView mas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mass);
        Intent intent=getIntent();
        String key=intent.getExtras().getString("unq");
        /*id=findViewById(R.id.id);
        button =findViewById(R.id.button2);*/
        BarChart barChart = findViewById(R.id.barChart);
        ArrayList<BarEntry> data = new ArrayList<>();
        mas=findViewById(R.id.massid);
        mas.setText("Weight Chart");
        FirebaseDatabase.getInstance().getReference().child("Users").child(key).child("data").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    count=(int) snapshot.getChildrenCount();

                }
                else{
                    count=0;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference().child("Users").child(key).child("data").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i=1;
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    Map<String, Object> map = (Map<String, Object>) ds.getValue();
                    Object price = map.get("weight");
                    int pvalue = Integer.parseInt(String.valueOf(price));
                    if(count<=7){
                        data.add(new BarEntry(i, pvalue));
                    }
                    else if(i>=count-7){
                        data.add(new BarEntry(i, pvalue));

                    }
                    i+=1;
                }
                BarDataSet barDataSet = new BarDataSet(data, "Weight");
                barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                barDataSet.setValueTextColor(Color.WHITE);
                barDataSet.setValueTextSize(15f);
                BarData barData = new BarData(barDataSet);
                barChart.setFitBars(true);
                barChart.setData(barData);
                barChart.getDescription().setText("set example");
                barChart.animateY(2000);
                barChart.getAxisLeft().setAxisMinimum(30f);
                barChart.getAxisLeft().setAxisMaximum(200f);
                barChart.getAxisLeft().setTextSize(15f);
                barChart.getAxisLeft().setTextColor(Color.WHITE);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }
}