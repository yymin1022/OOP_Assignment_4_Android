package com.oop7even.oop4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.oop7even.oop4.Adapter.CarRecyclerAdapter;
import com.oop7even.oop4.Model.Car;

import java.util.ArrayList;

public class SearchResultActivity extends AppCompatActivity{
    ArrayList<Car> carList;

    RecyclerView recycleCar;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        carList = (ArrayList<Car>)getIntent().getSerializableExtra("car");

        recycleCar = findViewById(R.id.result_recycler_car);
        setupUI();
    }

    void setupUI(){
        CarRecyclerAdapter carAdapter = new CarRecyclerAdapter(carList);
        recycleCar.setAdapter(carAdapter);
        recycleCar.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        carAdapter.setOnCarClickListener((position, clickedCar) -> {
            Intent intent = new Intent(getApplicationContext(), CarDetailActivity.class);
            intent.putExtra("Car", clickedCar);
            startActivity(intent);
        });
    }
}