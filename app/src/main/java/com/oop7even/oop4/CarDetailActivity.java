package com.oop7even.oop4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.oop7even.oop4.Model.Car;

public class CarDetailActivity extends AppCompatActivity{
    Car car;

    Button btnBuyCar;
    TextView tvCarCapacity;
    TextView tvCarColor;
    TextView tvCarDist;
    TextView tvCarFuel;
    TextView tvCarManufacture;
    TextView tvCarName;
    TextView tvCarNumber;
    TextView tvCarPrice;
    TextView tvCarYear;

    TextView tvCarNoAccident;
    TextView tvCarNoTune;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detail);

        car = (Car)getIntent().getSerializableExtra("Car");

        btnBuyCar = findViewById(R.id.detail_btn_buy);
        tvCarCapacity = findViewById(R.id.detail_tv_capacity);
        tvCarColor = findViewById(R.id.detail_tv_color);
        tvCarDist = findViewById(R.id.detail_tv_dist);
        tvCarFuel = findViewById(R.id.detail_tv_fuel);
        tvCarManufacture = findViewById(R.id.detail_tv_company);
        tvCarNumber = findViewById(R.id.detail_tv_number);
        tvCarName = findViewById(R.id.detail_tv_name);
        tvCarPrice = findViewById(R.id.detail_tv_price);
        tvCarYear = findViewById(R.id.detail_tv_year);

        tvCarNoAccident = findViewById(R.id.detail_tv_no_accident);
        tvCarNoTune = findViewById(R.id.detail_tv_no_tune);

        setCarInfo();
    }

    void setCarInfo(){
        tvCarCapacity.setText(String.valueOf(car.getCapacity()));
        tvCarColor.setText(car.getColor());
        tvCarDist.setText(String.valueOf(car.getDistanceDriven()));
        tvCarFuel.setText(car.getFuel());
        tvCarManufacture.setText(car.getManufacture());
        tvCarName.setText(car.getName());
        tvCarNumber.setText(car.getNumber());
        tvCarPrice.setText(String.valueOf(car.getPrice()));
        tvCarYear.setText(String.valueOf(car.getYear()));

        if(car.getIsAccident()){
            tvCarNoAccident.setVisibility(View.INVISIBLE);
        }

        if(car.getIsTuned()){
            tvCarNoTune.setVisibility(View.INVISIBLE);
        }
    }
}