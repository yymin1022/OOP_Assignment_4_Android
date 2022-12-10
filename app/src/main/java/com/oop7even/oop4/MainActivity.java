package com.oop7even.oop4;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.oop7even.oop4.Adapter.CarRecyclerAdapter;
import com.oop7even.oop4.Model.Accident;
import com.oop7even.oop4.Model.Car;
import com.oop7even.oop4.Model.Tune;
import com.oop7even.oop4.Model.User;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    boolean isLoggedIn = false;
    boolean isSeller = false;

    ActivityResultLauncher<Intent> loginLauncher;
    AppCompatButton btnRegister;
    AppCompatButton btnSearch;
    RecyclerView recycleCar;
    TextView tvCarCnt;
    TextView tvUsername;
    
    ArrayList<Car> carList;
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
        tvCarCnt = findViewById(R.id.main_tv_car_cnt);
        tvUsername = findViewById(R.id.main_tv_username);

        if(!isLoggedIn) {
            openLogin();
        }

        btnRegister.setOnClickListener(onBtnListener);
        btnSearch.setOnClickListener(onBtnListener);
    }

    @Override
    protected void onResume(){
        super.onResume();

        if(carList != null){
            setupUI();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch(item.getItemId()){
            case R.id.toolbar_menu_info:
                Intent intent = new Intent(getApplicationContext(), UserInfoActivity.class);
                intent.putExtra("car", carList);
                intent.putExtra("user", user);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    View.OnClickListener onBtnListener = v -> {
        Intent intent;
        switch(v.getId()){
            case R.id.main_btn_register:
                intent = new Intent(getApplicationContext(), CarRegisterActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
                break;
            case R.id.main_btn_search:
                intent = new Intent(getApplicationContext(), SearchActivity.class);
                intent.putExtra("car", carList);
                intent.putExtra("user", user);
                startActivity(intent);
                break;
        }
    };

    void openLogin(){
        Intent initIntent = new Intent(this, LoginActivity.class);
        initIntent.putExtra("user", user);

        loginLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result ->  {
            if(result.getResultCode() == 9001){
                Intent resultIntent = result.getData();

                if(resultIntent != null){
                    isSeller = resultIntent.getBooleanExtra("isSeller", false);
                    user = (User) resultIntent.getSerializableExtra("user");

                    if(isSeller){
                        carList = user.getCarList();
                    }else{
                        carList = (ArrayList<Car>) resultIntent.getSerializableExtra("car");
                    }

                    Toast.makeText(getApplicationContext(), isSeller ? "Seller : " : "Customer : " + user.getName(), Toast.LENGTH_SHORT).show();

                    setupUI();
                }
            }
        });

        loginLauncher.launch(initIntent);
    }

    void setupUI(){
        CarRecyclerAdapter carAdapter = new CarRecyclerAdapter(carList);

        btnRegister.setVisibility(isSeller ? View.VISIBLE : View.INVISIBLE);
        recycleCar.setAdapter(carAdapter);
        recycleCar.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        tvCarCnt.setText(String.valueOf(carList.size()));
        tvUsername.setText(user.getName());

        carAdapter.setOnCarClickListener((position, clickedCar) -> {
            Intent intent = new Intent(getApplicationContext(), CarDetailActivity.class);
            intent.putExtra("car", clickedCar);
            intent.putExtra("user", user);
            startActivity(intent);
        });
    }
}