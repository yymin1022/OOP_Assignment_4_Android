package com.oop7even.oop4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.oop7even.oop4.Model.Car;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    ArrayList<Car> carList;

    boolean isAccident = false;
    boolean isTune = false;
    int carDistanceMax = 300000;
    int carDistanceMin = 0;
    int carPriceMax = 5000;
    int carPriceMin = 0;
    int carYearMax = 2022;
    int carYearMin = 0;
    String carCompany;
    String carFuel;
    String carModel;
    String carType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        carList = (ArrayList<Car>)getIntent().getSerializableExtra("car");
    }
}