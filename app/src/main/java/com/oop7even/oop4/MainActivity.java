package com.oop7even.oop4;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
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

    View.OnClickListener onBtnListener = v -> {
        switch(v.getId()){
            case R.id.main_btn_register:
                startActivity(new Intent(getApplicationContext(), CarRegisterActivity.class));
                break;
            case R.id.main_btn_search:
                startActivity(new Intent(getApplicationContext(), SearchActivity.class));
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
                    Toast.makeText(getApplicationContext(), isSeller ? "Seller : " : "Customer : " + user.getName(), Toast.LENGTH_SHORT).show();

                    setupUI();
                }
            }
        });

        loginLauncher.launch(initIntent);
    }

    void setupUI(){
        ArrayList<Car> tmpCarList = new ArrayList<>();
        Car testCar = new Car("Veloster N", "Hyundai", "123구1234", "White", 2300, 5, 87120, 2019, "Gasoline", true, true);
        testCar.addAccident(new Accident("2022. 11. 28. 12:00", "차선변경 중 상대방 과실 100% 접촉사고. 좌측 휀더 교환"));
        testCar.addTune(new Tune("2022. 10. 23.", "배기구 2개, 가변밸브 1개"));
        Car testCar2 = new Car("Elantra N", "Hyundai", "12사1123", "퍼포먼스 블루", 2600, 5, 32250, 2021, "Gasoline", false, false);
        tmpCarList.add(testCar2);
        tmpCarList.add(testCar);
        tmpCarList.add(testCar);
        tmpCarList.add(testCar);

        CarRecyclerAdapter carAdapter = new CarRecyclerAdapter(tmpCarList);

        btnRegister.setVisibility(isSeller ? View.VISIBLE : View.INVISIBLE);
        recycleCar.setAdapter(carAdapter);
        recycleCar.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        tvCarCnt.setText(String.valueOf(tmpCarList.size()));
        tvUsername.setText(user.getName());

        carAdapter.setOnCarClickListener((position, clickedCar) -> {
            Intent intent = new Intent(getApplicationContext(), CarDetailActivity.class);
            intent.putExtra("Car", clickedCar);
            startActivity(intent);
        });
    }
}