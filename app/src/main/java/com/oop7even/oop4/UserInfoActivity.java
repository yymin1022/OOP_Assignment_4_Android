package com.oop7even.oop4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.oop7even.oop4.Model.Car;
import com.oop7even.oop4.Model.User;

import java.util.ArrayList;

public class UserInfoActivity extends AppCompatActivity{
    ArrayList<Car> carList;
    User user;

    RecyclerView recycleCar;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        Toolbar infoToolbar = findViewById(R.id.info_toolbar);
        setSupportActionBar(infoToolbar);

        carList = (ArrayList<Car>)getIntent().getSerializableExtra("car");
        user = (User)getIntent().getSerializableExtra("user");

        recycleCar = findViewById(R.id.info_recycler_car);
    }
}