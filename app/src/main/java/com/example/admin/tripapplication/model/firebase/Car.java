package com.example.admin.tripapplication.model.firebase;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Admin on 9/13/2017.
 */

public class Car implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.brand);
        dest.writeString(this.model);
        dest.writeString(this.year);
    }

    protected Car(Parcel in) {
        this.brand = in.readString();
        this.model = in.readString();
        this.year = in.readString();
    }

    public static final Parcelable.Creator<Car> CREATOR = new Parcelable.Creator<Car>() {
        @Override
        public Car createFromParcel(Parcel source) {
            return new Car(source);
        }

        @Override
        public Car[] newArray(int size) {
            return new Car[size];
        }
    };
}
