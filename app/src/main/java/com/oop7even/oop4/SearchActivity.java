package com.oop7even.oop4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.oop7even.oop4.Model.Car;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    ArrayList<Car> carList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        carList = (ArrayList<Car>)getIntent().getSerializableExtra("car");
    }
}