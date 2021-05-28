package com.example.finalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class homepage extends AppCompatActivity {
    private EditText bp,bg,w,id;
    private Button save,graph;
    private String unique,debug;
    private Integer sum,avg,count=0,globalcount;
    private Double A1C=0.0,bmi=0.0;
    private TextView textView;
    Gmailsender sender;
    Integer intbp;
    String user="mymailid.253@gmail.com",password="abcdef@123",subject="PATIENT ALERT",body,remail,name,height,age,bloodglucose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        Bundle extra =getIntent().getExtras();
        String key = extra.getString("key");
        bloodglucose = extra.getString("bgval");
        double intglucose = Double.parseDouble(bloodglucose);
        intglucose =  (0.13675)*Math.pow(intglucose,2) + (-148.15865)*Math.pow(intglucose,1)+ (40228.0908);
        int bglu = (int)intglucose;
        textView=findViewById(R.id.textView2);
        bloodglucose = Integer.toString(bglu);
        bp = findViewById(R.id.abcd);
        bg = findViewById(R.id.ab);
        w = findViewById(R.id.abc);
        save = findViewById(R.id.button3);
        graph=findViewById(R.id.graphplot);
        sender=new Gmailsender(user,password);
        //textView.setText("Blood Glucose : "+bloodglucose);
        bg.setText("Blood Glucose from sensor : "+bloodglucose);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String,Object> map = new HashMap<>();
                String bloop= bp.getText().toString().trim();
                //String bloodg=bg.getText().toString().trim();
                String bloodg = bloodglucose.trim();
                String weight=w.getText().toString().trim();
                String DateToday = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                if(bloop.isEmpty()){
                    bp.setError("field empty");
                    bp.requestFocus();
                    //emailreg.setOnEditorActionListener(editorlisten);
                    return;
                }
                /*if(bloodg.isEmpty()){
                    bg.setError("field empty");
                    bg.requestFocus();
                    //emailreg.setOnEditorActionListener(editorlisten);
                    return;
                }*/
                if(weight.isEmpty()){
                    w.setError("field empty");
                    w.requestFocus();
                    //passwordreg.setOnEditorActionListener(editorlisten);
                    return;
                }
                map.put("date",DateToday);
                map.put("bp",bloop);
                map.put("bg",bloodg);
                map.put("weight",weight);
                intbp =Integer.parseInt(bp.getText().toString());

                FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("emailid").equalTo(key)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot childsnapshot: snapshot.getChildren()){
                                    unique=childsnapshot.getKey();

                                    FirebaseDatabase.getInstance().getReference().child("Users").child(unique).child("data").push()
                                            .setValue(map)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Toast.makeText(homepage.this,"Updated",Toast.LENGTH_LONG).show();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.i("jfbvkj", "onFailure: "+e.toString());
                                                }
                                            }).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.i("jfbvkj", "onSuccess: ");
                                        }
                                    });
                                    FirebaseDatabase.getInstance().getReference().child("Users").child(unique).child("data").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            sum=0;
                                            for(DataSnapshot ds:dataSnapshot.getChildren()){
                                                Map<String,Object> map=(Map<String, Object>) ds.getValue();
                                                Object price=map.get("bg");
                                                int pvalue=Integer.parseInt(String.valueOf(price));
                                                sum+=pvalue;
                                                avg=sum/8;
                                                A1C=(avg+46.7)/28.7;
                                            }
                                        }


                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                                    FirebaseDatabase.getInstance().getReference().child("Users").child(unique).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for (DataSnapshot data : snapshot.getChildren()) {
                                                if(data.getKey().equals("height")){
                                                    height = data.getValue().toString();
                                                }
                                                if(data.getKey().equals("age")){
                                                    age= data.getValue().toString();
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                    FirebaseDatabase.getInstance().getReference().child("Users").child(unique).child("data").addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if(snapshot.exists()){
                                                count=(int) snapshot.getChildrenCount();
                                                globalcount=count;
                                                if(count==8){
                                                    Intent intent=new Intent(homepage.this,report.class);
                                                    intent.putExtra("A1C",Double.toString(A1C));
                                                    intent.putExtra("BP",bp.getText().toString());
                                                    int weight= Integer.parseInt(w.getText().toString());
                                                    double heightd=Double.parseDouble(height);
                                                    bmi=weight/(heightd*heightd);
                                                    intent.putExtra("BMI",Double.toString(bmi));
                                                    intent.putExtra("bgavg",Double.toString(avg));
                                                    intent.putExtra("age",age);
                                                    startActivity(intent);
                                                    snapshot.getRef().setValue("1");
                                                    //FirebaseDatabase.getInstance().getReference().child("Users").child(unique).child("data").setValue(1);
                                                    A1C=0.0;
                                                    count=0;
                                                }
                                                else{
                                                    ;
                                                }
                                            }
                                            else{
                                                count=0;
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                    FirebaseDatabase.getInstance().getReference().child("Users").child(unique).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for (DataSnapshot data : snapshot.getChildren()) {
                                                if(data.getKey().equals("fullname")){
                                                    name = data.getValue().toString();
                                                    body="Your patient"+" "+name+" "+"has been advised medical attention";
                                                }}
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                    FirebaseDatabase.getInstance().getReference().child("Users").child(unique).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for (DataSnapshot data : snapshot.getChildren()) {
                                                if(data.getKey().equals("docname")){
                                                    remail = data.getValue().toString();

                                                }}
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                if(A1C>5.7){
                    new MyAsyncClass().execute();
                }

            }

        });

        graph.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                if(count!=0){
                    Intent intent=new Intent(homepage.this, analytics.class);
                    intent.putExtra("unq",unique);
                    startActivity(intent);

                }
                else{
                    CharSequence text="NO DATA TO DISPLAY";
                    Toast.makeText(getApplicationContext(),text, Toast.LENGTH_LONG).show();
            }
                }

        });


    }
    class MyAsyncClass extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(homepage.this);
            pDialog.setMessage("Please wait...");
            pDialog.show();
        }
        @Override
        protected Void doInBackground(Void... mApi) {
            try {
                // Add subject, Body, your mail Id, and receiver mail Id.
                sender.sendMail(subject, body, user, remail);
                Log.d("send", "done");
            }
            catch (Exception ex) {
                Log.d("exceptionsending", ex.toString());
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pDialog.cancel();
            Toast.makeText(homepage.this, "mail sent", Toast.LENGTH_SHORT).show();
        }
    }
}