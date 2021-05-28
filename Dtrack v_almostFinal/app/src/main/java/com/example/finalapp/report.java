package com.example.finalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class report extends AppCompatActivity {
    private Button aic,bp,bmi,pred;
    private String A1C,BP,BMI,BGAVG,AGE;
    private Double A1CO,BPO,BMIO,BGAVGO,AGEO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        aic=findViewById(R.id.aic);
        bp=findViewById(R.id.bloodpressure);
        bmi=findViewById(R.id.bmindex);
        pred=findViewById(R.id.predict);
        Bundle extras = getIntent().getExtras();
        A1C = extras.getString("A1C").substring(0,4);
        BP = extras.getString("BP");
        BMI = extras.getString("BMI").substring(0,4);
        BGAVG = extras.getString("bgavg");
        AGE = extras.getString("age");
        A1CO =Double.parseDouble(A1C);
        BPO =Double.parseDouble(BP);
        BMIO =Double.parseDouble(BMI);
        BGAVGO =Double.parseDouble(BGAVG);
        AGEO =Double.parseDouble(AGE);
        if(A1CO<=5.7){
            aic.setText("A1C: "+A1C+"\n\nYour sugar levels are under control");
        }
        else if(A1CO>5.7&&A1CO<=6.4){
            aic.setText("A1C: "+A1C+"\n\nYour sugar levels are in Pre-Diabetic stage");
            aic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(report.this,suggetions.class);
                    intent.putExtra("KEY","1");
                    startActivity(intent);
                }
            });

        }
        else {
            aic.setText("A1C: "+A1C+"\n\nYour sugar levels are in Diabetic stage");
            aic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(report.this,suggetions.class);
                    intent.putExtra("KEY","2");
                    startActivity(intent);
                }
            });
        }
        if(BPO>=80&&BPO<=120.0){
            bp.setText("BP: "+BP+"\n\nYour BP is normal");
        }
        else if(BPO<80.0){
            bp.setText("BP: "+BP+"\n\nYou may have hypotension");
            bp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(report.this,suggetions.class);
                    intent.putExtra("KEY","3");
                    startActivity(intent);
                }
            });
        }
        else{
            bp.setText("BP: "+BP+"\n\nYou may have hypertension");
            bp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(report.this,suggetions.class);
                    intent.putExtra("KEY","4");
                    startActivity(intent);
                }
            });
        }
        if(BMIO>=18.5&&BMIO<24.9){
            bmi.setText("bmi:"+BMI+"\n\nyour body weight is normal");
        }
        else if(BMIO<18.5){
            bmi.setText("bmi:"+BMI+"\n\nyou are underweight");
            bmi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(report.this,suggetions.class);
                    intent.putExtra("KEY","5");
                    startActivity(intent);
                }
            });
        }
        else{
            bmi.setText("bmi:"+BMI+"\n\nyou are overweight");
            bmi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(report.this,suggetions.class);
                    intent.putExtra("KEY","6");
                    startActivity(intent);
                }
            });
        }
        pred.setText("DTRACK Prediction");
        pred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val1=BGAVGO.toString();
                String val2=BPO.toString();
                String val3=BMIO.toString();
                String val4=AGEO.toString();
                if(val3!=null&&val4!=null){
                    OkHttpClient okHttpClient=new OkHttpClient();
                    RequestBody formbody= new FormBody.Builder().add("value1",val1).add("value2",val2).add("value3",val3).add("value5",val4).build();
                    Request request=new Request.Builder().url("http://18.222.224.63/predict").post(formbody).build();
                    okHttpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(report.this, "Network error", Toast.LENGTH_LONG).show();
                                }
                            });

                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            report.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        int ans= Integer.parseInt(response.body().string());
                                        if(ans==0){
                                            pred.setText("We have predicted a low risk of diabetes in the future");
                                        }
                                        else if (ans==1){
                                            pred.setText("we have predicted a high risk of diabetes\n\nplease consult your doctor");
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                        }
                    });
                }
                //äpred.setText(Double.toString(BGAVGO));
            }
        });
        /*if(PREDO==0){
            pred.setText("We have predicted a low risk of diabetes in the future");═
        }
        else {
            bp.setText("we have predicted a high risk of diabetes\n\nplease consult your doctor");
        }*/

    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                Intent intent=new Intent(report.this,homepage.class);
                startActivity(intent);
                return true;
            case KeyEvent.KEYCODE_HOME:
               ;
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}