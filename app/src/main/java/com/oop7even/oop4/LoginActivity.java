package com.oop7even.oop4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.oop7even.oop4.Model.Accident;
import com.oop7even.oop4.Model.Car;
import com.oop7even.oop4.Model.Tune;
import com.oop7even.oop4.Model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class LoginActivity extends AppCompatActivity {
    boolean isSeller = false;
    String userName = "";
    String userPW = "";

    EditText inputID;
    EditText inputPW;

    ArrayList<Car> carList = new ArrayList<>();
    User user = new User("", true);

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = FirebaseFirestore.getInstance();

        AppCompatButton btnLogin = findViewById(R.id.login_btn_login);
        CheckBox chkIsSeller = findViewById(R.id.login_chk_seller);
        inputID = findViewById(R.id.login_input_id);
        inputPW = findViewById(R.id.login_input_pw);

        btnLogin.setOnClickListener(clkListener);
        chkIsSeller.setOnCheckedChangeListener(chkListener);
        inputID.addTextChangedListener(txtWatcher);
        inputPW.addTextChangedListener(txtWatcher);
    }

    View.OnClickListener clkListener = view -> {
        if(userName.isEmpty()){
            Toast.makeText(getApplicationContext(), "사용자 ID를 입력해주세요.", Toast.LENGTH_SHORT).show();
        }else if(userPW.isEmpty()){
            Toast.makeText(getApplicationContext(), "로그인 비밀번호가 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
        }else{
            db.collection("User")
                    .get()
                    .addOnCompleteListener(task -> {
                        boolean isLogin  = false;
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                if(document.getId().equals(userName) && document.getData().get("password").equals(userPW)){
                                    completeLogin();
                                    isLogin = true;
                                    break;
                                }
                            }
                        }
                        
                        if(!isLogin){
                            Toast.makeText(getApplicationContext(), "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    };

    CompoundButton.OnCheckedChangeListener chkListener = (compoundButton, b) -> isSeller = b;
    TextWatcher txtWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String input = editable.toString();

            if(input.equals(inputID.getText().toString())){
                userName = input;
            }else if(input.equals(inputPW.getText().toString())){
                userPW = input;
            }
        }
    };

    void completeLogin(){
        initUser();
    }

    void initUser(){
        ArrayList<String> carNumberList = new ArrayList<>();

        db.collection("User")
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        for(QueryDocumentSnapshot document : task.getResult()){
                            if(document.getId().equals(userName)){
                                isSeller = (boolean)document.getData().get("isSeller");

                                for(String carNumber : (ArrayList<String>)document.getData().get("car_owned")){
                                    carNumberList.add(carNumber);
                                }
                                break;
                            }
                        }

                        initUserCars(carNumberList);
                    }
                });
    }

    void initUserCars(ArrayList<String> carNumberList){
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
                            int price = ((Long)document.getData().get("price")).intValue();
                            int capacity = ((Long)document.getData().get("capacity")).intValue();
                            int distanceDriven = ((Long)document.getData().get("distanceDriven")).intValue();
                            int year = ((Long)document.getData().get("year")).intValue();
                            String fuel = (String)document.getData().get("fuel");
                            boolean isAccident = (boolean)document.getData().get("isAccident");
                            boolean isTuned = (boolean)document.getData().get("isTuned");
                            boolean isSold = (boolean)document.getData().get("isSold");

                            Car tmpCar = new Car(name, manufacture, number, color, type, price, capacity, distanceDriven, year, fuel, isAccident, isTuned);

                            if(isAccident){
                                HashMap<String, HashMap<String, String>> accidentData = (HashMap<String, HashMap<String, String>>)document.getData().get("accidentData");
                                for(int idx = 1; idx < ((HashMap<?, ?>) document.getData().get("accidentData")).size(); idx++){
                                    tmpCar.addAccident(new Accident(accidentData.get(String.format("data%d", idx)).get("date"), accidentData.get(String.format("data%d", idx)).get("content")));
                                }
                            }

                            if(isTuned){
                                HashMap<String, HashMap<String, String>> tuneData = (HashMap<String, HashMap<String, String>>)document.getData().get("tuneData");
                                for(int idx = 1; idx < ((HashMap<?, ?>) document.getData().get("tuneData")).size(); idx++){
                                    tmpCar.addTune(new Tune(tuneData.get(String.format("data%d", idx)).get("date"), tuneData.get(String.format("data%d", idx)).get("content")));
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
                        user.setName(userName);

                        Intent resultIntent = new Intent(this, LoginActivity.class);
                        resultIntent.putExtra("car", carList);
                        resultIntent.putExtra("user", user);
                        resultIntent.putExtra("isSeller", isSeller);
                        setResult(9001, resultIntent);
                        finish();
                    }
                });
    }
}