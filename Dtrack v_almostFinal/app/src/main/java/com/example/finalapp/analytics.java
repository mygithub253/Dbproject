package com.example.finalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class analytics extends AppCompatActivity {
    private Button bloodpressure,bloodglucose,weight,history;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);
        Intent intent=getIntent();
        String key=intent.getExtras().getString("unq");
        bloodpressure=findViewById(R.id.bloodpressuregraph);
        bloodglucose=findViewById(R.id.bloodglucosegraph);
        weight=findViewById(R.id.weightgraph);
        history=findViewById(R.id.patienthistory);
        bloodpressure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(analytics.this,pressure.class);
                intent.putExtra("unq",key);
                startActivity(intent);
            }
        });
        bloodglucose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(analytics.this,glucose.class);
                intent.putExtra("unq",key);
                startActivity(intent);

            }
        });
        weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(analytics.this,mass.class);
                intent.putExtra("unq",key);
                startActivity(intent);

            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(analytics.this,listreport.class);
                intent.putExtra("unq",key);
                startActivity(intent);

            }
        });

    }
}