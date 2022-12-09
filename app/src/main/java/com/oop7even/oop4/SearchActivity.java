package com.oop7even.oop4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.android.material.slider.RangeSlider;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.oop7even.oop4.Model.Car;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    ArrayList<Car> carList;

    boolean carIsAccident = false;
    boolean carIsTune = false;
    int carDistanceMax = 300000;
    int carDistanceMin = 0;
    int carPriceMax = 5000;
    int carPriceMin = 0;
    int carYearMax = 2022;
    int carYearMin = 0;
    String carCompany = "";
    String carFuel = "All";
    String carModel = "";
    String carType = "All";

    MaterialButton btnSearch;
    EditText inputCompany;
    EditText inputName;
    MaterialButtonToggleGroup toggleType;
    RadioGroup radioFuel;
    RangeSlider sliderDistance;
    RangeSlider sliderPrice;
    RangeSlider sliderYear;
    SwitchMaterial switchAccident;
    SwitchMaterial switchTune;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        carList = (ArrayList<Car>)getIntent().getSerializableExtra("car");

        btnSearch = findViewById(R.id.search_btn_search);
        inputCompany = findViewById(R.id.search_input_company);
        inputName = findViewById(R.id.search_input_name);
        toggleType = findViewById(R.id.search_toggle_type);
        radioFuel = findViewById(R.id.search_radio_fuel);
        sliderDistance = findViewById(R.id.search_slider_distance);
        sliderPrice = findViewById(R.id.search_slider_price);
        sliderYear = findViewById(R.id.search_slider_year);
        switchAccident = findViewById(R.id.search_switch_accident);
        switchTune = findViewById(R.id.search_switch_tune);

        btnSearch.setOnClickListener(btnListener);
        toggleType.addOnButtonCheckedListener(typeListener);
        radioFuel.setOnCheckedChangeListener(fuelListener);
        sliderDistance.addOnChangeListener(distanceListener);
        sliderPrice.addOnChangeListener(priceListener);
        sliderYear.addOnChangeListener(yearListener);
        switchAccident.setOnCheckedChangeListener(accidentListener);
        switchTune.setOnCheckedChangeListener(tuneListener);
    }

    View.OnClickListener btnListener = v -> {
        carCompany = inputCompany.getText().toString();
        carModel = inputName.getText().toString();

        ArrayList<Car> resultCar = new ArrayList<>();
    };

    MaterialButtonToggleGroup.OnButtonCheckedListener typeListener = new MaterialButtonToggleGroup.OnButtonCheckedListener() {
        @Override
        public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
            switch(checkedId){
                case R.id.search_toggle_type_all:
                    carFuel = "All";
                    break;
                case R.id.search_toggle_type_car:
                    carFuel = "Car";
                    break;
                case R.id.search_toggle_type_truck:
                    carFuel = "Truck";
                    break;
                case R.id.search_toggle_type_bus:
                    carFuel = "Bus";
                    break;
            }
        }
    };

    RadioGroup.OnCheckedChangeListener fuelListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch(checkedId){
                case R.id.search_radio_fuel_all:
                    carFuel = "All";
                    break;
                case R.id.search_radio_fuel_gasoline:
                    carFuel = "Gasoline";
                    break;
                case R.id.search_radio_fuel_diesel:
                    carFuel = "Diesel";
                    break;
                case R.id.search_radio_fuel_lpg:
                    carFuel = "LPG";
                    break;
                case R.id.search_radio_fuel_elec:
                    carFuel = "Electric";
                    break;
            }
        }
    };

    RangeSlider.OnChangeListener distanceListener = (slider, value, fromUser) -> {
        carDistanceMax = (int)slider.getValueTo();
        carDistanceMin = (int)slider.getValueFrom();
    };

    RangeSlider.OnChangeListener priceListener = (slider, value, fromUser) -> {
        carPriceMax = (int)slider.getValueTo();
        carPriceMin = (int)slider.getValueFrom();
    };

    RangeSlider.OnChangeListener yearListener = (slider, value, fromUser) -> {
        carYearMax = (int)slider.getValueTo();
        carYearMin = (int)slider.getValueFrom();
    };

    CompoundButton.OnCheckedChangeListener accidentListener = (buttonView, isChecked) -> {
        carIsAccident = isChecked;
    };

    CompoundButton.OnCheckedChangeListener tuneListener = (buttonView, isChecked) -> {
        carIsTune = isChecked;
    };
}