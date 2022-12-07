package com.oop7even.oop4;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.oop7even.oop4.Model.User;

public class MainActivity extends AppCompatActivity {
    boolean isLoggedIn = false;

    ActivityResultLauncher<Intent> loginLauncher;
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
        Intent initIntent = new Intent(this, LoginActivity.class);
        initIntent.putExtra("user", user);

        loginLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result ->  {
            if(result.getResultCode() == 9001){
                Intent resultIntent = result.getData();

                if(resultIntent != null){
                    user = (User) resultIntent.getSerializableExtra("user");
                    Toast.makeText(getApplicationContext(), user.getName(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        loginLauncher.launch(initIntent);
    }
}