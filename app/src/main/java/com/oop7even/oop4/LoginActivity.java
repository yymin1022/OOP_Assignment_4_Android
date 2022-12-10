package com.oop7even.oop4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.oop7even.oop4.Model.User;

import java.util.ArrayList;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    boolean isSeller = false;
    String userName = "";
    String userPW = "";

    EditText inputID;
    EditText inputPW;

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

                                carNumberList.addAll((ArrayList<String>)Objects.requireNonNull(document.getData().get("car_owned")));
                                break;
                            }
                        }

                        user.setName(userName);

                        Intent resultIntent = new Intent(this, LoginActivity.class);
                        resultIntent.putExtra("car", carNumberList);
                        resultIntent.putExtra("user", user);
                        resultIntent.putExtra("isSeller", isSeller);
                        setResult(9001, resultIntent);
                        finish();
                    }
                });
    }
}