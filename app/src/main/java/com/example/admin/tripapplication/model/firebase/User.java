package com.example.admin.tripapplication.model.firebase;

import android.net.Uri;

import java.net.URL;
import java.util.List;

/**
 * Created by Admin on 9/13/2017.
 */

public class User {

    String user_id;
    String name;
    String phoneNumber;
    Uri imageUri;
    URL googleAcctLink;
    URL fbAcctLink;
    int age;
    String country;
    String state;
    String city;
    String zip;
    String address;
    String email;
    String sex;
    Car car;
    float rating;
    List<Review> review;
    List<Trip> tripList;

    public User(String user_id, String name, String phoneNumber, Uri imageUri, URL googleAcctLink, URL fbAcctLink, int age, String country, String state, String city, String zip, String address, String email, String sex, Car car, float rating, List<Review> review, List<Trip> tripList) {
        this.user_id = user_id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.imageUri = imageUri;
        this.googleAcctLink = googleAcctLink;
        this.fbAcctLink = fbAcctLink;
        this.age = age;
        this.country = country;
        this.state = state;
        this.city = city;
        this.zip = zip;
        this.address = address;
        this.email = email;
        this.sex = sex;
        this.car = car;
        this.rating = rating;
        this.review = review;
        this.tripList = tripList;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public URL getGoogleAcctLink() {
        return googleAcctLink;
    }

    public void setGoogleAcctLink(URL googleAcctLink) {
        this.googleAcctLink = googleAcctLink;
    }

    public URL getFbAcctLink() {
        return fbAcctLink;
    }

    public void setFbAcctLink(URL fbAcctLink) {
        this.fbAcctLink = fbAcctLink;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public List<Review> getReview() {
        return review;
    }

    public void setReview(List<Review> review) {
        this.review = review;
    }

    public List<Trip> getTripList() {
        return tripList;
    }

    public void setTripList(List<Trip> tripList) {
        this.tripList = tripList;
    }
}