package com.oop7even.oop4;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.oop7even.oop4.Model.User;

public class MainActivity extends AppCompatActivity {
    boolean isLoggedIn = false;
    boolean isSeller = false;

    ActivityResultLauncher<Intent> loginLauncher;
    AppCompatButton btnRegister;
    AppCompatButton btnSearch;
    RecyclerView recycleCar;
    TextView tvUsername;
    User user = new User("", false);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mainToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);

        btnRegister = findViewById(R.id.main_btn_register);
        btnSearch = findViewById(R.id.main_btn_search);
        recycleCar = findViewById(R.id.main_recycler_car);
        tvUsername = findViewById(R.id.main_tv_username);

        if(!isLoggedIn) {
            openLogin();
        }
    }

    void openLogin(){
        Intent initIntent = new Intent(this, LoginActivity.class);
        initIntent.putExtra("user", user);

        loginLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result ->  {
            if(result.getResultCode() == 9001){
                Intent resultIntent = result.getData();

                if(resultIntent != null){
                    isSeller = resultIntent.getBooleanExtra("isSeller", false);
                    user = (User) resultIntent.getSerializableExtra("user");
                    Toast.makeText(getApplicationContext(), isSeller ? "Seller : " : "Customer : " + user.getName(), Toast.LENGTH_SHORT).show();

                    setupUI();
                }
            }
        });

        loginLauncher.launch(initIntent);
    }

    void setupUI(){
        btnRegister.setVisibility(isSeller ? View.VISIBLE : View.INVISIBLE);
        tvUsername.setText(user.getName());
    }
}