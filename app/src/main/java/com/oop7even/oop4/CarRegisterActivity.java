package com.oop7even.oop4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.oop7even.oop4.Model.Accident;
import com.oop7even.oop4.Model.Car;
import com.oop7even.oop4.Model.Tune;

public class CarRegisterActivity extends AppCompatActivity {
    int carCapacity;
    int carDistance;
    int carPrice;
    int carYear;
    String carColor;
    String carCompany;
    String carFuel;
    String carName;
    String carNumber;
    String carType;

    AppCompatButton btnRegister;
    EditText inputCapacity;
    EditText inputColor;
    EditText inputCompany;
    EditText inputDistance;
    EditText inputFuel;
    EditText inputName;
    EditText inputNumber;
    EditText inputPrice;
    EditText inputType;
    EditText inputYear;

    EditText inputAccidentContent;
    EditText inputAccidentDate;
    EditText inputTuneContent;
    EditText inputTuneDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_register);

        btnRegister = findViewById(R.id.register_btn_register);
        inputCapacity = findViewById(R.id.register_input_capacity);
        inputColor = findViewById(R.id.register_input_color);
        inputCompany = findViewById(R.id.register_input_company);
        inputDistance = findViewById(R.id.register_input_distance);
        inputFuel = findViewById(R.id.register_input_fuel);
        inputName = findViewById(R.id.register_input_name);
        inputNumber = findViewById(R.id.register_input_number);
//        inputPrice = findViewById(R.id.register_input_price);
//        inputType = findViewById(R.id.register_input_type);
        inputYear = findViewById(R.id.register_input_year);
    }

    View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            carCapacity = Integer.parseInt(inputCapacity.getText().toString());
            carColor = inputColor.getText().toString();
            carCompany = inputCompany.getText().toString();
            carDistance = Integer.parseInt(inputDistance.getText().toString());
            carFuel = inputFuel.getText().toString();
            carName = inputName.getText().toString();
            carNumber = inputNumber.getText().toString();
//            carPrice = Integer.parseInt(inputPrice.getText().toString());
//            carType = inputType.getText().toString();
            carYear = Integer.parseInt(inputYear.getText().toString());

            boolean isAccident = false;
            boolean isTune = false;
            Accident tmpAccident = new Accident("", "");
            Tune tmpTune = new Tune("", "");
            if(inputAccidentContent.getText().length() > 0 && inputAccidentDate.getText().length() > 0){
                isAccident = true;
                tmpAccident = new Accident(inputAccidentDate.getText().toString(), inputAccidentContent.getText().toString());
            }
            if(inputTuneContent.getText().length() > 0 && inputTuneDate.getText().length() > 0){
                isTune = true;
                tmpTune = new Tune(inputTuneDate.getText().toString(), inputTuneContent.getText().toString());
            }

            Car newCar = new Car(carName, carCompany, carNumber, carColor, carType, carPrice, carCapacity, carDistance, carYear, carFuel, isAccident, isTune);
            if(isAccident){
                newCar.addAccident(tmpAccident);
            }
            if(isTune){
                newCar.addTune(tmpTune);
            }
        }
    };






}