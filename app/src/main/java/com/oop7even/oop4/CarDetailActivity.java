package com.oop7even.oop4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.oop7even.oop4.Adapter.AccidentTuneRecyclerAdapter;
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

    RecyclerView recyclerAccident;
    RecyclerView recyclerTune;
    TextView tvCarNoAccident;
    TextView tvCarNoTune;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detail);

        Toolbar mainToolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(mainToolbar);

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

        recyclerAccident = findViewById(R.id.detail_recycler_accident);
        recyclerTune = findViewById(R.id.detail_recycler_tune);
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
            AccidentTuneRecyclerAdapter accidentAdapter = new AccidentTuneRecyclerAdapter(true, car.getAccidents(), null);
            recyclerAccident.setVisibility(View.VISIBLE);
            recyclerAccident.setAdapter(accidentAdapter);
            recyclerAccident.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

            tvCarNoAccident.setVisibility(View.GONE);
        }

        if(car.getIsTuned()){
            AccidentTuneRecyclerAdapter tuneAdapter = new AccidentTuneRecyclerAdapter(false, null, car.getTunes());
            recyclerTune.setVisibility(View.VISIBLE);
            recyclerTune.setAdapter(tuneAdapter);
            recyclerTune.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

            tvCarNoTune.setVisibility(View.GONE);
        }
    }
}