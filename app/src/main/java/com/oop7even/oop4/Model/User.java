package com.oop7even.oop4.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userName;
    private boolean isSeller;
    protected static ArrayList<Car> Cars = new ArrayList<>();

    public User(String name, boolean isSeller){
        this.userName = name;
        this.isSeller = isSeller;
    }

    // for Seller
    public boolean addCar(Car newCar){
        if(isSeller){
            for (Car car : Cars) { //이미 등록된 차량인지 확인
                if (car.getNumber().equals(newCar.getNumber())) {
                    System.out.println("이미 등록한 차량입니다.");
                    return false; //이미 등록한 차량일 경우 False
                }
            }
            try {
                Cars.add(newCar);
                System.out.println("성공적으로 등록했습니다.");
                return true; //정상적으로 등록된 경우 True
            } catch (Exception e) {
                e.printStackTrace(); //혹시 모를 exception
            }
        }else{
            System.out.println("판매자 계정이 아닙니다.");
            return false; //판매자 계정이 아니면 false
        }
        return false; //기본값 false
    }

    // for Customer
    public boolean buyCar(Car targetCar, User seller){ //정상적으로 구매가 이루어졌는지 반환
        if(!isSeller){
            for (Car car : Cars) {
                if (car.getNumber().equals(targetCar.getNumber())) {
                    System.out.println("이미 구매한 차량입니다");
                    return false; //이미 구매한 차량이면 false
                }
            }
            try {
                for(int i= 0 ;i < seller.getCarList().size(); i++) {
                    if (seller.getCarList().get(i).getNumber().equals(targetCar.getNumber())) {
                        Cars.add(targetCar);
                        seller.getCarList().remove(targetCar);
                        System.out.println("성공적으로 구매했습니다.");
                        return true; //정상적으로 구매한 경우 true
                    }
                }
                System.out.println("해당 차량이 존재하지 않습니다.");
                return false; //끝까지 스캔했는데도 차량이 나오지 않으면 false
            }catch (Exception e){
                e.printStackTrace(); //혹시 모를 exception
            }
        }else{
            System.out.println("판매자 계정으로 로그인한 상태입니다. 다른 계정으로 로그인해주세요.");
            return false; //seller 계정이면 false
        }
        return false; //기본값은 false
    }

    public static ArrayList<Car> getCarList(){
        return Cars;
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