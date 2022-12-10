package com.oop7even.oop4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.oop7even.oop4.Adapter.CarRecyclerAdapter;
import com.oop7even.oop4.Model.Car;
import com.oop7even.oop4.Model.User;

import java.util.ArrayList;

public class SearchResultActivity extends AppCompatActivity{
    ArrayList<Car> carList;
    User user;

    RecyclerView recycleCar;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Toolbar resultToolbar = findViewById(R.id.result_toolbar);
        setSupportActionBar(resultToolbar);

        carList = (ArrayList<Car>)getIntent().getSerializableExtra("car");
        user = (User)getIntent().getSerializableExtra("user");

        recycleCar = findViewById(R.id.result_recycler_car);
        setupUI();
    }

    void setupUI(){
        CarRecyclerAdapter carAdapter = new CarRecyclerAdapter(carList);
        recycleCar.setAdapter(carAdapter);
        recycleCar.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        carAdapter.setOnCarClickListener((position, clickedCar) -> {
            Intent intent = new Intent(getApplicationContext(), CarDetailActivity.class);
            intent.putExtra("car", clickedCar);
            intent.putExtra("user", user);
            startActivity(intent);
        });
    }
}