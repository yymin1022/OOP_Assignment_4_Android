package com.oop7even.oop4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;

import com.oop7even.oop4.Model.User;

public class MainActivity extends AppCompatActivity {
    boolean isLoggedIn = false;
    User user = new User("", false);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mainToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);

        openLogin();
    }

    void openLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }
}