package com.oop7even.oop4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.slider.RangeSlider;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.oop7even.oop4.Model.Car;
import com.oop7even.oop4.Model.User;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    ArrayList<Car> carList;
    User user;

    boolean carIsAccident = false;
    boolean carIsTune = false;
    int carDistanceMax = 300000;
    int carDistanceMin = 0;
    int carPriceMax = 50000;
    int carPriceMin = 0;
    int carYearMax = 2022;
    int carYearMin = 1990;
    String carCompany = "";
    String carFuel = "전체";
    String carName = "";
    String carType = "전체";

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
        user = (User)getIntent().getSerializableExtra("user");

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

        Intent intent = new Intent(getApplicationContext(), SearchResultActivity.class);
        intent.putExtra("car", resultCar);
        intent.putExtra("user", user);
        startActivity(intent);
    };

    MaterialButtonToggleGroup.OnButtonCheckedListener typeListener = (group, checkedId, isChecked) -> {
        if(isChecked){
            switch (checkedId) {
                case R.id.search_toggle_type_all:
                    carType = "전체";
                    break;
                case R.id.search_toggle_type_car:
                    carType = "승용";
                    break;
                case R.id.search_toggle_type_truck:
                    carType = "화물";
                    break;
                case R.id.search_toggle_type_bus:
                    carType = "버스";
                    break;
            }
        }
    };

    RadioGroup.OnCheckedChangeListener fuelListener = (group, checkedId) -> {
        switch(checkedId){
            case R.id.search_radio_fuel_all:
                carFuel = "전체";
                break;
            case R.id.search_radio_fuel_gasoline:
                carFuel = "가솔린";
                break;
            case R.id.search_radio_fuel_diesel:
                carFuel = "디젤";
                break;
            case R.id.search_radio_fuel_lpg:
                carFuel = "LPG";
                break;
            case R.id.search_radio_fuel_elec:
                carFuel = "전기";
                break;
        }
    };

    RangeSlider.OnChangeListener distanceListener = (slider, value, fromUser) -> {
        carDistanceMax = Math.round(slider.getValues().get(1)) * 1000;
        carDistanceMin = Math.round(slider.getValues().get(0)) * 1000;
    };

    RangeSlider.OnChangeListener priceListener = (slider, value, fromUser) -> {
        carPriceMax = Math.round(slider.getValues().get(1));
        carPriceMin = Math.round(slider.getValues().get(0));
    };

    RangeSlider.OnChangeListener yearListener = (slider, value, fromUser) -> {
        carYearMax = Math.round(slider.getValues().get(1));
        carYearMin = Math.round(slider.getValues().get(0));
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
            if(type.equals("전체")){
                if(fuel.equals("전체")){
                    if(car.getName().contains(name) && car.getManufacture().contains(company) && car.getPrice() <= maxPrice && car.getPrice() >= minPrice && car.getDistanceDriven() <= maxDistanceDriven && car.getDistanceDriven() >= minDistanceDriven && car.getYear() <= maxYear && car.getYear() >= minYear &&  car.getIsAccident() == isAccident && car.getIsTuned() == isTuned){
                        resultCarList.add(car);
                    }
                }else{
                    if(car.getName().contains(name) && car.getManufacture().contains(company) && car.getPrice() <= maxPrice && car.getPrice() >= minPrice && car.getDistanceDriven() <= maxDistanceDriven && car.getDistanceDriven() >= minDistanceDriven && car.getYear() <= maxYear && car.getYear() >= minYear && car.getFuel().equals(fuel) && car.getIsAccident() == isAccident && car.getIsTuned() == isTuned){
                        resultCarList.add(car);
                    }
                }
            }else{
                if(fuel.equals("전체")){
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