package com.example.admin.tripapplication.model.firebase;

import java.net.URL;

public class UserBuilder {
    private String user_id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String imageURL;
    private URL googleAcctLink;
    private URL fbAcctLink;
    private int age;
    private String country;
    private String state;
    private String city;
    private String zip;
    private String address;
    private String email;
    private String sex;
    private Car car;
    private float rating;

    public UserBuilder setUser_id(String user_id) {
        this.user_id = user_id;
        return this;
    }

    public UserBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserBuilder setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public UserBuilder setImageUrl(String imageURL) {
        this.imageURL = imageURL;
        return this;
    }

    public UserBuilder setGoogleAcctLink(URL googleAcctLink) {
        this.googleAcctLink = googleAcctLink;
        return this;
    }

    public UserBuilder setFbAcctLink(URL fbAcctLink) {
        this.fbAcctLink = fbAcctLink;
        return this;
    }

    public UserBuilder setAge(int age) {
        this.age = age;
        return this;
    }

    public UserBuilder setCountry(String country) {
        this.country = country;
        return this;
    }

    public UserBuilder setState(String state) {
        this.state = state;
        return this;
    }

    public UserBuilder setCity(String city) {
        this.city = city;
        return this;
    }

    public UserBuilder setZip(String zip) {
        this.zip = zip;
        return this;
    }

    public UserBuilder setAddress(String address) {
        this.address = address;
        return this;
    }

    public UserBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder setSex(String sex) {
        this.sex = sex;
        return this;
    }

    public UserBuilder setCar(Car car) {
        this.car = car;
        return this;
    }

    public UserBuilder setRating(float rating) {
        this.rating = rating;
        return this;
    }

    public User createUser() {
        return new User(user_id, firstName, lastName, phoneNumber, imageURL, googleAcctLink, fbAcctLink, age, country, state, city, zip, address, email, sex, car, rating);
    }
}