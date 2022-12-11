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

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.oop7even.oop4.Adapter.CarRecyclerAdapter;
import com.oop7even.oop4.Model.Accident;
import com.oop7even.oop4.Model.Car;
import com.oop7even.oop4.Model.Tune;
import com.oop7even.oop4.Model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    boolean isLoggedIn = false;
    boolean isSeller = false;

    ActivityResultLauncher<Intent> loginLauncher;
    AppCompatButton btnRegister;
    AppCompatButton btnSearch;
    RecyclerView recycleCar;
    TextView tvCarCnt;
    TextView tvUsername;
    
    ArrayList<Car> carList = new ArrayList<>();
    ArrayList<String> carNumberList = new ArrayList<>();
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

        if(carNumberList.size() > 0){
            carList.clear();
            initCarList(carNumberList);
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
        if(item.getItemId() == R.id.toolbar_menu_info){
            Intent intent = new Intent(getApplicationContext(), UserInfoActivity.class);
            intent.putExtra("carList", user.getCarList());
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
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
                    user = (User)resultIntent.getSerializableExtra("user");

                    carNumberList = (ArrayList<String>)resultIntent.getSerializableExtra("car");
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
            intent.putExtra("carlist", carList);
            intent.putExtra("user", user);
            startActivity(intent);
        });
    }

    void initCarList(ArrayList<String> carNumberList){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Car")
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        for(QueryDocumentSnapshot document : task.getResult()){
                            String name = (String)document.getData().get("name");
                            String manufacture = (String)document.getData().get("manufacture");
                            String number = document.getId();
                            String color = (String)document.getData().get("color");
                            String type = (String)document.getData().get("type");
                            String image = (String)document.getData().get("img");
                            int price = ((Long)Objects.requireNonNull(document.getData().get("price"))).intValue();
                            int capacity = ((Long)Objects.requireNonNull(document.getData().get("capacity"))).intValue();
                            int distanceDriven = ((Long)Objects.requireNonNull(document.getData().get("distanceDriven"))).intValue();
                            int year = ((Long)Objects.requireNonNull(document.getData().get("year"))).intValue();
                            String fuel = (String)document.getData().get("fuel");
                            boolean isAccident = (boolean)document.getData().get("isAccident");
                            boolean isTuned = (boolean)document.getData().get("isTuned");
                            boolean isSold = (boolean)document.getData().get("isSold");

                            Car tmpCar = new Car(name, manufacture, number, color, type, price, capacity, distanceDriven, year, fuel, isAccident, isTuned);

                            if(isAccident){
                                HashMap<String, HashMap<String, String>> accidentData = (HashMap<String, HashMap<String, String>>)document.getData().get("accidentData");
                                for(int idx = 1; idx <= ((HashMap<?, ?>) document.getData().get("accidentData")).size(); idx++){
                                    tmpCar.addAccident(new Accident(accidentData.get(String.format("data%d", idx)).get("date"), Objects.requireNonNull(accidentData.get(String.format("data%d", idx))).get("content")));
                                }
                            }

                            if(isTuned){
                                HashMap<String, HashMap<String, String>> tuneData = (HashMap<String, HashMap<String, String>>)document.getData().get("tuneData");
                                for(int idx = 1; idx <= ((HashMap<?, ?>) document.getData().get("tuneData")).size(); idx++){
                                    tmpCar.addTune(new Tune(tuneData.get(String.format("data%d", idx)).get("date"), Objects.requireNonNull(tuneData.get(String.format("data%d", idx))).get("content")));
                                }
                            }

                            if(!isSold){
                                carList.add(tmpCar);
                            }

                            if(carNumberList.contains(document.getId())){
                                user.addCar(tmpCar);
                            }
                        }

                        user.setIsSeller(isSeller);
                        setupUI();
                    }
                });
    }
}