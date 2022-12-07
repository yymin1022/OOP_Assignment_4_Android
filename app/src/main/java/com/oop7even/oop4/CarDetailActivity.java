package com.oop7even.oop4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.oop7even.oop4.Model.Car;

public class CarDetailActivity extends AppCompatActivity{
    Car car;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detail);

        car = (Car)getIntent().getSerializableExtra("Car");

        Toast.makeText(getApplicationContext(), car.getName(), Toast.LENGTH_SHORT).show();
    }
}