package com.oop7even.oop4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.slider.RangeSlider;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.oop7even.oop4.Model.Car;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    ArrayList<Car> carList;

    boolean isAccident = false;
    boolean isTune = false;
    int carDistanceMax = 300000;
    int carDistanceMin = 0;
    int carPriceMax = 5000;
    int carPriceMin = 0;
    int carYearMax = 2022;
    int carYearMin = 0;
    String carCompany;
    String carFuel;
    String carModel;
    String carType;

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

        inputCompany = findViewById(R.id.search_input_company);
        inputName = findViewById(R.id.search_input_name);
        toggleType = findViewById(R.id.search_toggle_type);
        radioFuel = findViewById(R.id.search_radio_fuel);
        sliderDistance = findViewById(R.id.search_slider_distance);
        sliderPrice = findViewById(R.id.search_slider_price);
        sliderYear = findViewById(R.id.search_slider_year);
        switchAccident = findViewById(R.id.search_switch_accident);
        switchTune = findViewById(R.id.search_switch_tune);
    }
}