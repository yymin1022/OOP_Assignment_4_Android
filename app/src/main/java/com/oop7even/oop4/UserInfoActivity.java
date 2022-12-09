package com.oop7even.oop4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.oop7even.oop4.Model.Car;
import com.oop7even.oop4.Model.User;

import java.util.ArrayList;

public class UserInfoActivity extends AppCompatActivity{
    ArrayList<Car> carList;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        carList = (ArrayList<Car>)getIntent().getSerializableExtra("car");
        user = (User)getIntent().getSerializableExtra("user");
    }
}