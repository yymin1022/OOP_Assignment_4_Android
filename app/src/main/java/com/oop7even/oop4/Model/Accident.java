package com.oop7even.oop4.Model;

import java.io.Serializable;

public class Accident implements Serializable {
    private static final long serialVersionUID = 1L;

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