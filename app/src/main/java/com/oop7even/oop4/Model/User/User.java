package com.oop7even.oop4.Model.User;

import java.util.ArrayList;

import com.oop7even.oop4.Model.Car;

public class User {
    private String userName;
    private boolean isSeller;
    protected ArrayList<Car> Cars = new ArrayList<>();

    protected String getName(){
        return this.userName;
    }

    protected ArrayList<Car> getCarList(){
        return this.Cars;
    }

    protected void setName(String name){
        this.userName = name;
    }

    protected void setCarList(ArrayList<Car> myList){
        this.Cars = myList;
    }

    protected void showCarList(){
        // 차량 리스트를 펼쳐, 모든 차량에 담긴 모든 정보를 조회하는 기능
    }

    protected void setIsSeller(boolean flag){
        this.isSeller = flag;
    }
}