package com.oop7even.oop4.Model;

import java.util.ArrayList;

public class Car{
    private enum Fuel{
        Diesel,
        Electric,
        Gasoline,
        LPG
    }

    private String name;
    private String manufacture;
    private String number;
    private String color;
    private int price;
    private int capacity;
    private int distanceDriven;
    private int year;
    private Fuel fuel;
    private boolean isAccident;
    private boolean isTuned;
    private final ArrayList<Accident> accidents = new ArrayList<>();
    private final ArrayList<Tune> tunes = new ArrayList<>();

    public Car(String name, String manufacture, String number, String color, int price, int capacity, int distanceDriven, int year, String fuel, boolean isAccident, boolean isTuned){
        this.name = name;
        this.manufacture = manufacture;
        this.number = number;
        this.color = color;
        this.price = price;
        this.capacity = capacity;
        this.distanceDriven = distanceDriven;
        this.year = year;
        this.fuel = Fuel.valueOf(fuel);
        this.isAccident = isAccident;
        this.isTuned = isTuned;
    }

    public void printCarInfo(){
        System.out.printf("Name : %s\nManufacture : %s\nNumber : %s\nPrice : %d\nCapacity : %d\nYear : %d\nAccident History : %s\nTune History : %s\n", name, manufacture, number, price, capacity, year, this.isAccident ? "Y" : "N", this.isTuned ? "Y" : "N");

        if(isAccident) {
            System.out.println("<Accident Info>");
            for (Accident accident : accidents) {
                System.out.printf("Accident Date : %s\nAccident Content : %s\n", accident.getDate(), accident.getContent());
            }
        }

        if(isTuned) {
            System.out.println("<Tune Info>");
            for (Tune tune : tunes) {
                System.out.printf("Tune Date : %s\nTune Content : %s\n", tune.getDate(), tune.getContent());
            }
        }
    }

    public String getName(){
        return this.name;
    }

    public String getManufacture(){
        return this.manufacture;
    }

    public String getNumber(){
        return this.number;
    }

    public String getColor(){
        return this.color;
    }

    public int getPrice(){
        return this.price;
    }

    public int getCapacity(){
        return this.capacity;
    }

    public int getDistanceDriven(){
        return this.distanceDriven;
    }

    public int getYear(){
        return this.year;
    }

    public String getFuel(){
        return this.fuel.toString();
    }

    public boolean getIsAccident(){
        return this.isAccident;
    }

    public boolean getIsTuned(){
        return this.isTuned;
    }

    public ArrayList<Accident> getAccidents(){
        return this.accidents;
    }

    public ArrayList<Tune> getTunes(){
        return this.tunes;
    }

    public void addAccident(Accident accident){
        this.accidents.add(accident);
    }

    public void removeAccident(int idx){
        this.accidents.remove(idx);
    }

    public void addTune(Tune tune){
        this.tunes.add(tune);
    }

    public void removeTune(int idx){
        this.tunes.remove(idx);
    }

    public void setName(String name){
        this.name = name;
    }

    public void setManufacture(String manufacture){
        this.manufacture = manufacture;
    }

    public void setNumber(String number){
        this.number = number;
    }

    public void setColor(String color){
        this.color = color;
    }

    public void setPrice(int price){
        this.price = price;
    }

    public void setCapacity(int capacity){
        this.capacity = capacity;
    }

    public void setDistanceDriven(int distanceDriven){
        this.distanceDriven = distanceDriven;
    }

    public void setYear(int year){
        this.year = year;
    }

    public void setFuel(String fuel){
        this.fuel = Fuel.valueOf(fuel);
    }

    public void setIsAccident(boolean isAccident){
        this.isAccident = isAccident;
    }

    public void setIsTuned(boolean isTuned){
        this.isTuned = isTuned;
    }
}