package com.oop7even.oop4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;

import com.oop7even.oop4.Model.User;

public class LoginActivity extends AppCompatActivity {
    User user = new User("Sans", false);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AppCompatButton btnLogin = findViewById(R.id.login_btn_login);
        CheckBox chkIsSeller = findViewById(R.id.login_chk_seller);
    }

    void completeLogin(){
        Intent resultIntent = new Intent(this, LoginActivity.class);
        resultIntent.putExtra("user", user);
        setResult(9001, resultIntent);
        finish();
    }
}