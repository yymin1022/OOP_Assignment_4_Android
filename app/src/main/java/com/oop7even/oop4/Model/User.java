package com.oop7even.oop4.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userName;
    private boolean isSeller;
    protected ArrayList<Car> Cars = new ArrayList<>();

    public User(String name, boolean isSeller){
        this.userName = name;
        this.isSeller = isSeller;
    }

    // for Seller
    public void addCar(Car newCar){
        Cars.add(newCar);
        // 신규 판매 차량 등록, 차량 리스트에 새로운 차량 추가
        // 파일 입출력을 통해 판매 차량 명단 txt 파일에 차량에 대한 정보 추가
        // 파일 내용 로직 상에서 갱신하는 과정 필요
    }

    // for Customer
    public void buyCar(Car car, User seller){
        Cars.add(car);
        seller.Cars.remove(car);

        // 차량 구매, myCar 리스트에 이 차량 추가
        // 주인이 등록되었기에 기존 Car 리스트에서 차량 제거
        // 알림 메시지 등 필요
        // 파일 입출력으로 user의 차량 txt 파일에 이 차량에 대한 데이터를 이어붙이면서, 판매 차량 명단 txt 파일에 이 차량에 대한 데이터를 삭제
        // 파일 내용 로직 상에서 갱신하는 과정 필요
    }

    public ArrayList<Car> getCarList(){
        return this.Cars;
    }

    public boolean getIsSeller(){
        return this.isSeller;
    }

    public String getName(){
        return this.userName;
    }

    public void setCarList(ArrayList<Car> myList){
        this.Cars = myList;
    }

    public void setIsSeller(boolean isSeller){
        this.isSeller = isSeller;
    }

    public void setName(String name){
        this.userName = name;
    }

    public void showCarList(){
        for(Car car : Cars){
            car.printCarInfo();
        }
    }
}
