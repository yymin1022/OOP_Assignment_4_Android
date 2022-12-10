package com.oop7even.oop4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.oop7even.oop4.Adapter.UserCarRecyclerAdapter;
import com.oop7even.oop4.Model.Car;

import java.util.ArrayList;

public class UserInfoActivity extends AppCompatActivity{
    RecyclerView recycleCar;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        Toolbar infoToolbar = findViewById(R.id.info_toolbar);
        setSupportActionBar(infoToolbar);

        ArrayList<Car> carList = (ArrayList<Car>)getIntent().getSerializableExtra("carList");

        recycleCar = findViewById(R.id.info_recycler_car);

        UserCarRecyclerAdapter carAdapter = new UserCarRecyclerAdapter(carList);
        recycleCar.setAdapter(carAdapter);
        recycleCar.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }
}