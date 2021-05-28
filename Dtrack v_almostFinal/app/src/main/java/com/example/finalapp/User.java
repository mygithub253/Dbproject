package com.example.finalapp;

public class User {
    public String fullname,age,emailid,data,docname,docid,phone1,height;
    public User(){
    }
    public User(String fullname,String age,String emailid,String docname,String docid,String phone1,String height){
        this.fullname=fullname;
        this.age=age;
        this.emailid=emailid;
        this.docid=docid;
        this.docname=docname;
        this.phone1=phone1;
        this.height=height;
        this.data="1";
    }
}
