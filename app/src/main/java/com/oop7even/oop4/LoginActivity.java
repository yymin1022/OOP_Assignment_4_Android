package com.oop7even.oop4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.oop7even.oop4.Model.User;

public class LoginActivity extends AppCompatActivity {
    User user = new User("Sans", false);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        completeLogin();
    }

    void completeLogin(){
        Intent resultIntent = new Intent(this, LoginActivity.class);
        resultIntent.putExtra("user", user);
        setResult(9001, resultIntent);
        finish();
    }
}