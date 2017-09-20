package com.example.admin.tripapplication.model.firebase;

import android.os.Parcel;
import android.os.Parcelable;

import java.net.URL;
import java.util.Map;

/**
 * Created by Admin on 9/13/2017.
 */

public class User implements Parcelable {

    String user_id;
    String firstName;
    String lastName;
    String phoneNumber;
    String imageURL;
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
    Map<String, Review> review;
    Map<String, Trip> tripList;

    public User(){}

    public User(String user_id, String firstName, String lastName, String phoneNumber, String imageURL, URL googleAcctLink, URL fbAcctLink, int age, String country, String state, String city, String zip, String address, String email, String sex, Car car, float rating){
        this.user_id = user_id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.imageURL = imageURL;
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
    }

    public User(String user_id, String firstName, String lastName, String phoneNumber, String imageURL, URL googleAcctLink, URL fbAcctLink, int age, String country, String state, String city, String zip, String address, String email, String sex, Car car, float rating, Map<String, Review> review, Map<String, Trip> tripList) {
        this.user_id = user_id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.imageURL = imageURL;
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

    protected User(Parcel in) {
        user_id = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        phoneNumber = in.readString();
        imageURL = in.readString();
        age = in.readInt();
        country = in.readString();
        state = in.readString();
        city = in.readString();
        zip = in.readString();
        address = in.readString();
        email = in.readString();
        sex = in.readString();
        rating = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user_id);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(phoneNumber);
        dest.writeString(imageURL);
        dest.writeInt(age);
        dest.writeString(country);
        dest.writeString(state);
        dest.writeString(city);
        dest.writeString(zip);
        dest.writeString(address);
        dest.writeString(email);
        dest.writeString(sex);
        dest.writeFloat(rating);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
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

    public Map<String, Review> getReview() {
        return review;
    }

    public void setReview(Map<String, Review> review) {
        this.review = review;
    }

    public Map<String, Trip> getTripList() {
        return tripList;
    }

    public void setTripList(Map<String, Trip> tripList) {
        this.tripList = tripList;
    }
}