package com.example.finalapp;

public class Bio {
    private String bg;
    private String bp;
    private String weight;
    private String date;

    public Bio() {
    }

    public Bio(String date,String bg, String bp, String weight) {
        this.bg = bg;
        this.bp = bp;
        this.weight = weight;
        this.date = date;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public String getBg() {
        return bg;
    }

    public void setBg(String bg) {
        this.bg = bg;
    }

    public String getBp() {
        return bp;
    }

    public void setBp(String bp) {
        this.bp = bp;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}


