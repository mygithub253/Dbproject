package com.example.finalapp;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.database.FirebaseDatabase;
public class register_user extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private EditText fullname,age,emailreg,passwordreg,docname,docemail,phone1,height;
    private ProgressBar progressbar;
    private TextView registerreg,homebutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        mAuth = FirebaseAuth.getInstance();
        registerreg=(Button)findViewById(R.id.registerreg);
        registerreg.setOnClickListener(this);
        fullname=(EditText)findViewById(R.id.fullname);
        age=(EditText)findViewById(R.id.age);
        emailreg=(EditText)findViewById(R.id.emailreg);
        passwordreg=(EditText)findViewById(R.id.passwordreg);
        docname=(EditText)findViewById(R.id.docname);
        docemail=(EditText)findViewById(R.id.docemail);
        phone1=(EditText)findViewById(R.id.phone1);
        height=(EditText)findViewById(R.id.height);
        homebutton = (TextView) findViewById(R.id.homebutton);
        homebutton.setOnClickListener(this);
        emailreg.setOnEditorActionListener(editorlisten);
        passwordreg.setOnEditorActionListener(editorlisten);
        docemail.setOnEditorActionListener(editorlisten);
        fullname.setOnEditorActionListener(editorlisten);
        age.setOnEditorActionListener(editorlisten);
        docname.setOnEditorActionListener(editorlisten);
        phone1.setOnEditorActionListener(editorlisten);
        height.setOnEditorActionListener(editorlisten);
    }
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
            case R.id.registerreg:
                registerreg();
                break;
            case R.id.homebutton:
                startActivity(new Intent(register_user.this,MainActivity.class));
                break;
        }
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
    private void registerreg() {
        String pname= fullname.getText().toString().trim();
        String page= age.getText().toString().trim();
        int myNum=0;
        try {
            myNum = Integer.parseInt(age.getText().toString());
        } catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }
        String pemail= emailreg.getText().toString().trim();
        String ppassword= passwordreg.getText().toString().trim();
        String docn= docname.getText().toString().trim();
        String doce= docemail.getText().toString().trim();
        String number1=phone1.getText().toString().trim();
        String high=height.getText().toString().trim();
        if(pname.isEmpty()){
            fullname.setError("Full Name is required!!");
            fullname.requestFocus();
            return;
        }
        if(page.isEmpty()){
            age.setError("Age is required!!");
            age.requestFocus();
            return;
        }
        if(myNum>100) {
            passwordreg.setError("Please enter valid age");
            passwordreg.requestFocus();
            return;
        }
        if(pemail.isEmpty()){
            emailreg.setError("Email is required!!");
            emailreg.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(pemail).matches()){
            emailreg.setError("Invalid email!!");
            emailreg.requestFocus();
            return;
        }
        if(ppassword.isEmpty()){
            passwordreg.setError("Full Name is required!!");
            passwordreg.requestFocus();
            return;
        }
        if(ppassword.length()<5){
            passwordreg.setError("Password should have minimum 5 characters!!");
            passwordreg.requestFocus();
            return;
        }
        if(docn.isEmpty()){
            docname.setError("Full Name is required!!");
            docname.requestFocus();
            return;
        }
        if(doce.isEmpty()){
            docemail.setError("Email is required!!");
            docemail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(doce).matches()){
            docemail.setError("Valid email is required!!");
            docemail.requestFocus();
            return;
        }
        if(number1.isEmpty()){
            phone1.setError("Full Name is required!!");
            phone1.requestFocus();
            return;
        }
        if(high.isEmpty()){
            height.setError("Full Name is required!!");
            height.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(pemail,ppassword).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                          @Override
                                          public void onComplete(@NonNull Task<AuthResult> task) {
                                              if(task.isSuccessful()){
                                                  User user= new User(pname,page,pemail,doce,docn,number1,high);
                                                  FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                          .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                      @Override
                                                      public void onComplete(@NonNull Task<Void> task) {
                                                          if (task.isSuccessful()){
                                                              Toast.makeText(register_user.this,"You have been registered!!",Toast.LENGTH_LONG).show();
                                                          }
                                                          else{
                                                              Toast.makeText(register_user.this,"Failed to register!!",Toast.LENGTH_LONG).show();
                                                          }
                                                      }
                                                  }) ;
                                              }
                                              else{
                                                  Toast.makeText(register_user.this,"Failed to register!!",Toast.LENGTH_LONG).show();
                                              }
                                          }
                                      }
                );
        startActivity(new Intent(register_user.this,MainActivity.class));
    }
}