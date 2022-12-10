package com.oop7even.oop4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.oop7even.oop4.Adapter.AccidentTuneRecyclerAdapter;
import com.oop7even.oop4.Model.Car;
import com.oop7even.oop4.Model.User;

public class CarDetailActivity extends AppCompatActivity{
    Car car;
    User user;

    Button btnBuyCar;
    ImageView ivCarImage;
    TextView tvCarCapacity;
    TextView tvCarColor;
    TextView tvCarDist;
    TextView tvCarFuel;
    TextView tvCarManufacture;
    TextView tvCarName;
    TextView tvCarNumber;
    TextView tvCarPrice;
    TextView tvCarType;
    TextView tvCarYear;

    RecyclerView recyclerAccident;
    RecyclerView recyclerTune;
    TextView tvCarNoAccident;
    TextView tvCarNoTune;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detail);

        car = (Car)getIntent().getSerializableExtra("car");
        user = (User)getIntent().getSerializableExtra("user");

        btnBuyCar = findViewById(R.id.detail_btn_buy);
        ivCarImage = findViewById(R.id.detail_image_car);
        tvCarCapacity = findViewById(R.id.detail_tv_capacity);
        tvCarColor = findViewById(R.id.detail_tv_color);
        tvCarDist = findViewById(R.id.detail_tv_dist);
        tvCarFuel = findViewById(R.id.detail_tv_fuel);
        tvCarManufacture = findViewById(R.id.detail_tv_company);
        tvCarNumber = findViewById(R.id.detail_tv_number);
        tvCarName = findViewById(R.id.detail_tv_name);
        tvCarPrice = findViewById(R.id.detail_tv_price);
        tvCarType = findViewById(R.id.detail_tv_type);
        tvCarYear = findViewById(R.id.detail_tv_year);

        recyclerAccident = findViewById(R.id.detail_recycler_accident);
        recyclerTune = findViewById(R.id.detail_recycler_tune);
        tvCarNoAccident = findViewById(R.id.detail_tv_no_accident);
        tvCarNoTune = findViewById(R.id.detail_tv_no_tune);

        btnBuyCar.setOnClickListener(btnListener);
        setCarInfo();
    }

    View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            user.buyCar(car, null);
        }
    };

    void setCarInfo(){
        tvCarCapacity.setText(String.valueOf(car.getCapacity()));
        tvCarColor.setText(car.getColor());
        tvCarDist.setText(String.valueOf(car.getDistanceDriven()));
        tvCarFuel.setText(car.getFuel());
        tvCarManufacture.setText(car.getManufacture());
        tvCarName.setText(car.getName());
        tvCarNumber.setText(car.getNumber());
        tvCarPrice.setText(String.valueOf(car.getPrice()));
        tvCarType.setText(car.getType());
        tvCarYear.setText(String.valueOf(car.getYear()));

        if(car.getIsAccident()){
            AccidentTuneRecyclerAdapter accidentAdapter = new AccidentTuneRecyclerAdapter(true, car.getAccidents(), null);
            recyclerAccident.setVisibility(View.VISIBLE);
            recyclerAccident.setAdapter(accidentAdapter);
            recyclerAccident.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

            tvCarNoAccident.setVisibility(View.GONE);
        }

        if(car.getIsTuned()){
            AccidentTuneRecyclerAdapter tuneAdapter = new AccidentTuneRecyclerAdapter(false, null, car.getTunes());
            recyclerTune.setVisibility(View.VISIBLE);
            recyclerTune.setAdapter(tuneAdapter);
            recyclerTune.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

            tvCarNoTune.setVisibility(View.GONE);
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Car")
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        for(QueryDocumentSnapshot document : task.getResult()){
                            if(document.getId().equals(car.getNumber())){
                                String carImage = (String)document.getData().get("img");

                                byte[] imageAsBytes = Base64.decode(carImage.getBytes(), Base64.DEFAULT);
                                ivCarImage.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
                            }
                        }
                    }
                });
    }
}