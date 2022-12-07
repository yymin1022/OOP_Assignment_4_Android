package com.oop7even.oop4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import com.oop7even.oop4.Model.User;

public class LoginActivity extends AppCompatActivity {
    boolean isSeller = false;
    String userName = "";

    User user = new User("Sans", false);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AppCompatButton btnLogin = findViewById(R.id.login_btn_login);
        CheckBox chkIsSeller = findViewById(R.id.login_chk_seller);

        btnLogin.setOnClickListener(clkListener);
        chkIsSeller.setOnCheckedChangeListener(chkListener);
    }

    View.OnClickListener clkListener = view -> completeLogin();
    CompoundButton.OnCheckedChangeListener chkListener = (compoundButton, b) -> isSeller = b;

    void completeLogin(){
        user.setIsSeller(isSeller);
        user.setName(userName);

        Intent resultIntent = new Intent(this, LoginActivity.class);
        resultIntent.putExtra("user", user);
        resultIntent.putExtra("isSeller", isSeller);
        setResult(9001, resultIntent);
        finish();
    }
}