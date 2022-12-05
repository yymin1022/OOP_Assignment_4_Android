package com.oop7even.oop4.Model;


import java.util.ArrayList;

class User {
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

class Customer extends User {
    private ArrayList<Car> MyCar = new ArrayList<>();


    public Customer(String name, ArrayList<Car> list, ArrayList<Car> mylist){
        setIsSeller(false);
        setName(name);
        setCarList(list);
        setMyCarList(mylist);
    }

    public ArrayList<Car> getMyCarList(){
        return this.MyCar;
    }

    public void setMyCarList(ArrayList<Car> List){
        this.MyCar = List;
    }

    public void buyCar(Car car){
        MyCar.add(car);
        Cars.remove(car);

        // 차량 구매, myCar 리스트에 이 차량 추가
        // 주인이 등록되었기에 기존 Car 리스트에서 차량 제거
        // 알림 메시지 등 필요
        // 파일 입출력으로 user의 차량 txt 파일에 이 차량에 대한 데이터를 이어붙이면서, 판매 차량 명단 txt 파일에 이 차량에 대한 데이터를 삭제
        // 파일 내용 로직 상에서 갱신하는 과정 필요
    }

}

class Seller extends User {

    public Seller(String name, ArrayList<Car> CarList) {
        setIsSeller(true);
        setName(name);
        setCarList(CarList);
    }

    public void AddCar(Car newCar){
        Cars.add(newCar);
        // 신규 판매 차량 등록, 차량 리스트에 새로운 차량 추가
        // 파일 입출력을 통해 판매 차량 명단 txt 파일에 차량에 대한 정보 추가
        // 파일 내용 로직 상에서 갱신하는 과정 필요
    }

}