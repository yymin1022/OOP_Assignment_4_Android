package com.oop7even.oop4;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.oop7even.oop4.Model.Accident;
import com.oop7even.oop4.Model.Car;
import com.oop7even.oop4.Model.Tune;
import com.oop7even.oop4.Model.User;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CarRegisterActivity extends AppCompatActivity {
    ActivityResultLauncher<Intent> imageLauncher;
    User user;

    int carCapacity;
    int carDistance;
    int carPrice;
    int carYear;
    String carColor;
    String carCompany;
    String carFuel;
    String carImage;
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

    ImageView imageCar;
    LinearLayout layoutImageCar;

    EditText inputAccidentContent;
    EditText inputAccidentDate;
    EditText inputTuneContent;
    EditText inputTuneDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_register);

        user = (User)getIntent().getSerializableExtra("user");

        btnRegister = findViewById(R.id.register_btn_register);
        inputCapacity = findViewById(R.id.register_input_capacity);
        inputColor = findViewById(R.id.register_input_color);
        inputCompany = findViewById(R.id.register_input_company);
        inputDistance = findViewById(R.id.register_input_distance);
        inputFuel = findViewById(R.id.register_input_fuel);
        inputName = findViewById(R.id.register_input_name);
        inputNumber = findViewById(R.id.register_input_number);
        inputPrice = findViewById(R.id.register_input_price);
        inputType = findViewById(R.id.register_input_type);
        inputYear = findViewById(R.id.register_input_year);

        imageCar = findViewById(R.id.register_image_car);
        layoutImageCar = findViewById(R.id.register_layout_image);

        inputAccidentContent = findViewById(R.id.register_input_accident_content);
        inputAccidentDate = findViewById(R.id.register_input_accident_date);
        inputTuneContent = findViewById(R.id.register_input_tune_content);
        inputTuneDate = findViewById(R.id.register_input_tune_date);

        btnRegister.setOnClickListener(btnListener);
        layoutImageCar.setOnClickListener(imageListener);

        imageLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if(result.getResultCode() == Activity.RESULT_OK){
                Intent resultIntent = result.getData();

                if(resultIntent != null){
                    Uri imageUri = resultIntent.getData();

                    try{
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
                            Bitmap imageBitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(getContentResolver(), imageUri));
                            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 70, outputStream);
                            byte[] bytes = outputStream.toByteArray();
                            carImage = Base64.encodeToString(bytes, Base64.DEFAULT);

                            imageCar.setImageURI(imageUri);
                            imageCar.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
                        }
                    }catch(IOException e){
                        e.printStackTrace();
                    }


                }
            }
        });
    }

    View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(inputCapacity.getText().length() == 0 || inputDistance.getText().length() == 0 || inputPrice.getText().length() == 0 || inputYear.getText().length() == 0){
                Toast.makeText(getApplicationContext(), "차량 정보를 모두 입력해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            carCapacity = Integer.parseInt(inputCapacity.getText().toString());
            carColor = inputColor.getText().toString();
            carCompany = inputCompany.getText().toString();
            carDistance = Integer.parseInt(inputDistance.getText().toString());
            carFuel = inputFuel.getText().toString();
            carName = inputName.getText().toString();
            carNumber = inputNumber.getText().toString();
            carPrice = Integer.parseInt(inputPrice.getText().toString());
            carType = inputType.getText().toString();
            carYear = Integer.parseInt(inputYear.getText().toString());

            if(carColor.isEmpty() || carCompany.isEmpty() || carFuel.isEmpty() || carImage.isEmpty() || carName.isEmpty() || carNumber.isEmpty() || carType.isEmpty()){
                Toast.makeText(getApplicationContext(), "차량 정보를 모두 입력해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean isAccident = false;
            boolean isTune = false;
            Accident tmpAccident = new Accident("", "");
            Tune tmpTune = new Tune("", "");
            if(inputAccidentContent.getText() != null && inputAccidentDate.getText() != null){
                isAccident = true;
                tmpAccident = new Accident(inputAccidentDate.getText().toString(), inputAccidentContent.getText().toString());
            }
            if(inputTuneContent.getText() != null && inputTuneDate.getText() != null){
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

            if(user.addCar(newCar)){
                Toast.makeText(getApplicationContext(), "차량이 등록되었습니다.", Toast.LENGTH_SHORT).show();
                // Upload to Firebase
                finish();
            }else{
                Toast.makeText(getApplicationContext(), "차량을 등록하지 못했습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    };

    View.OnClickListener imageListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ImagePicker.with(CarRegisterActivity.this)
                    .galleryOnly()
                    .crop(16f, 9f)
                    .compress(1024)
                    .createIntent(intent -> {
                        imageLauncher.launch(intent);
                        return null;
                    });
        }
    };
}