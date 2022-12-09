package com.oop7even.oop4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.button.MaterialButtonToggleGroup;
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
    String carName = "";
    String carType = "All";

    AppCompatButton btnSearch;
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
        carName = inputName.getText().toString();

        ArrayList<Car> resultCar = findCar(carName, carCompany, carType, carPriceMax, carPriceMin, carDistanceMax, carDistanceMin, carYearMax, carYearMin, carFuel, carIsAccident, carIsTune);
        Toast.makeText(getApplicationContext(), String.valueOf(resultCar.size()), Toast.LENGTH_SHORT).show();
    };

    MaterialButtonToggleGroup.OnButtonCheckedListener typeListener = (group, checkedId, isChecked) -> {
        switch(checkedId){
            case R.id.search_toggle_type_all:
                carType = "All";
                break;
            case R.id.search_toggle_type_car:
                carType = "Car";
                break;
            case R.id.search_toggle_type_truck:
                carType = "Truck";
                break;
            case R.id.search_toggle_type_bus:
                carType = "Bus";
                break;
        }
    };

    RadioGroup.OnCheckedChangeListener fuelListener = (group, checkedId) -> {
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

    ArrayList<Car> findCar(String name, String company, String type, int maxPrice, int minPrice, int maxDistanceDriven, int minDistanceDriven, int maxYear, int minYear, String fuel, boolean isAccident, boolean isTuned){
        ArrayList<Car> resultCarList = new ArrayList<>();

        for(Car car : carList){
            if(type.equals("All")){
                if(fuel.equals("All")){
                    if(car.getName().contains(name) && car.getManufacture().contains(company) && car.getPrice() <= maxPrice && car.getPrice() >= minPrice && car.getDistanceDriven() <= maxDistanceDriven && car.getDistanceDriven() >= minDistanceDriven && car.getYear() <= maxYear && car.getYear() >= minYear &&  car.getIsAccident() == isAccident && car.getIsTuned() == isTuned){
                        resultCarList.add(car);
                    }
                }else{
                    if(car.getName().contains(name) && car.getManufacture().contains(company) && car.getPrice() <= maxPrice && car.getPrice() >= minPrice && car.getDistanceDriven() <= maxDistanceDriven && car.getDistanceDriven() >= minDistanceDriven && car.getYear() <= maxYear && car.getYear() >= minYear && car.getFuel().equals(fuel) && car.getIsAccident() == isAccident && car.getIsTuned() == isTuned){
                        resultCarList.add(car);
                    }
                }
            }else{
                if(fuel.equals("All")){
                    if(car.getName().contains(name) && car.getManufacture().contains(company) && car.getType().equals(type) && car.getPrice() <= maxPrice && car.getPrice() >= minPrice && car.getDistanceDriven() <= maxDistanceDriven && car.getDistanceDriven() >= minDistanceDriven && car.getYear() <= maxYear && car.getYear() >= minYear && car.getIsAccident() == isAccident && car.getIsTuned() == isTuned){
                        resultCarList.add(car);
                    }
                }else{
                    if(car.getName().contains(name) && car.getManufacture().contains(company) && car.getType().equals(type) && car.getPrice() <= maxPrice && car.getPrice() >= minPrice && car.getDistanceDriven() <= maxDistanceDriven && car.getDistanceDriven() >= minDistanceDriven && car.getYear() <= maxYear && car.getYear() >= minYear && car.getFuel().equals(fuel) && car.getIsAccident() == isAccident && car.getIsTuned() == isTuned){
                        resultCarList.add(car);
                    }
                }
            }
        }
        
        return resultCarList;
    }
}