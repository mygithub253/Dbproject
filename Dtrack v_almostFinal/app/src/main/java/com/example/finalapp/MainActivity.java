package com.example.finalapp;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
public class MainActivity extends AppCompatActivity implements View.OnClickListener  {
    private TextView register;
    private EditText password,email;
    private Button button;
    private FirebaseAuth mAuth;                 //intialize variables
    private String bgvalue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        register=(TextView)findViewById(R.id.register);
        register.setOnClickListener(this);
        button=(Button)findViewById(R.id.button);
        button.setOnClickListener(this);
        password=(EditText) findViewById(R.id.password);
        email=(EditText) findViewById(R.id.email);
        mAuth=FirebaseAuth.getInstance();
        email.setOnEditorActionListener(editorlisten);
        password.setOnEditorActionListener(editorlisten);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("bgvalue").orderByKey().limitToLast(1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //bgvalue = snapshot.getValue().toString();
                String val= snapshot.getValue().toString();
                val=val.substring(1,val.length()-1);
                String[] sparate=val.split("=");
                bgvalue=sparate[1];                                                        //gets sensor data from firebase and sends to homepage.java on sucessful login
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private TextView.OnEditorActionListener editorlisten =new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            switch (i){
                case EditorInfo.IME_ACTION_NEXT:
                    closeKeyboard();
                    break;
                case EditorInfo.IME_ACTION_SEND:
                    closeKeyboard();
                    break;
            }
            return false;
        }
    };
    private void closeKeyboard(){
        View view=this.getCurrentFocus();
        if(view !=null){
            InputMethodManager imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.register:
                startActivity(new Intent(this,register_user.class));         //goes to registration page
                break;
            case R.id.button:
                userlogin();
                break;
        }
    }
    private void userlogin() {
        String auemail= email.getText().toString().trim();
        String aupass=password.getText().toString().trim();
        if(auemail.isEmpty()){
            email.setError("Email is required!!");                                //toast message is displayed when email is empty
            email.requestFocus();
            //emailreg.setOnEditorActionListener(editorlisten);
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(auemail).matches()){
            email.setError("Invalid email!!");                                      //toast message is displayed when email is inavlid
            email.requestFocus();
            //emailreg.setOnEditorActionListener(editorlisten);
            return;
        }
        if(aupass.isEmpty()){
            password.setError("Full Name is required!!");                           //toast message is displayed when name is empty
            password.requestFocus();
            //passwordreg.setOnEditorActionListener(editorlisten);
            return;
        }
        if(a/upass.length()<5){
            password.setError("Password should have minimum 5 characters!!");
            password.requestFocus();
            //passwordreg.setOnEditorActionListener(editorlisten);
            return;
        }
        mAuth.signInWithEmailAndPassword(auemail,aupass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override

            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    for(int i=0;i<=2;i++){
                        Toast.makeText(MainActivity.this,"Loading Sensor data!!\nPlease wait for 10 sec",Toast.LENGTH_LONG).show();
                    }
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {                                                          //authenticates email and password 
                            // Do something after 5s = 5000ms
                            Intent intent=new Intent(MainActivity.this,homepage.class);
                            intent.putExtra("key",auemail);
                            intent.putExtra("bgval",bgvalue);                                               // sends the sensor value
                            startActivity(intent);
                            }
                    }, 10000);

                }
                else{
                    Toast.makeText(MainActivity.this,"Login failed check username/password",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

