package com.example.admin.tripapplication.model.firebase;

/**
 * Created by Admin on 9/13/2017.
 */

public class Car {

    String brand;
    String model;
    String year;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Car(String brand, String model, String year) {

        this.brand = brand;
        this.model = model;
        this.year = year;
    }
}
