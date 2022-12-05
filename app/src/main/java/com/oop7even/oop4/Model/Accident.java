package com.oop7even.oop4.Model;

public class Accident{
    private String date;
    private String content;

    public Accident(String date, String content){
        this.date = date;
        this.content = content;
    }

    public String getDate(){
        return this.date;
    }

    public String getContent(){
        return this.content;
    }

    public void setDate(String date){
        this.date = date;
    }

    public void setContent(String content){
        this.content = content;
    }
}