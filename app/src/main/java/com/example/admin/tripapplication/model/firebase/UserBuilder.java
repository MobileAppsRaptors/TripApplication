package com.example.admin.tripapplication.model.firebase;

import android.net.Uri;

import java.net.URL;
import java.util.List;

public class UserBuilder {
    private String user_id;
    private String first_name_tag;
    private String firstName;
    private String last_name_tag;
    private String lastName;
    private String phone_number_tag;
    private String phoneNumber;
    private String image_uri_tag;
    private Uri imageUri;
    private String google_acct_link_tag;
    private URL googleAcctLink;
    private String fb_acct_link_tag;
    private URL fbAcctLink;
    private String age_tag;
    private int age;
    private String country_tag;
    private String country;
    private String state_tag;
    private String state;
    private String city_tag;
    private String city;
    private String zip_tag;
    private String zip;
    private String address_tag;
    private String address;
    private String email_tag;
    private String email;
    private String sex_tag;
    private String sex;
    private String preferredLanguage_tag;
    private String preferredLanguage;
    private String car_tag;
    private Car car;
    private String rating_tag;
    private float rating;
    private String review_tag;
    private List<Review> review;
    private String trip_list_tag;
    private List<Trip> tripList;

    public UserBuilder setUser_id(String user_id) {
        this.user_id = user_id;
        return this;
    }

    public UserBuilder setFirst_name_tag(String first_name_tag) {
        this.first_name_tag = first_name_tag;
        return this;
    }

    public UserBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserBuilder setLast_name_tag(String last_name_tag) {
        this.last_name_tag = last_name_tag;
        return this;
    }

    public UserBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserBuilder setPhone_number_tag(String phone_number_tag) {
        this.phone_number_tag = phone_number_tag;
        return this;
    }

    public UserBuilder setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public UserBuilder setImage_uri_tag(String image_uri_tag) {
        this.image_uri_tag = image_uri_tag;
        return this;
    }

    public UserBuilder setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
        return this;
    }

    public UserBuilder setGoogle_acct_link_tag(String google_acct_link_tag) {
        this.google_acct_link_tag = google_acct_link_tag;
        return this;
    }

    public UserBuilder setGoogleAcctLink(URL googleAcctLink) {
        this.googleAcctLink = googleAcctLink;
        return this;
    }

    public UserBuilder setFb_acct_link_tag(String fb_acct_link_tag) {
        this.fb_acct_link_tag = fb_acct_link_tag;
        return this;
    }

    public UserBuilder setFbAcctLink(URL fbAcctLink) {
        this.fbAcctLink = fbAcctLink;
        return this;
    }

    public UserBuilder setAge_tag(String age_tag) {
        this.age_tag = age_tag;
        return this;
    }

    public UserBuilder setAge(int age) {
        this.age = age;
        return this;
    }

    public UserBuilder setCountry_tag(String country_tag) {
        this.country_tag = country_tag;
        return this;
    }

    public UserBuilder setCountry(String country) {
        this.country = country;
        return this;
    }

    public UserBuilder setState_tag(String state_tag) {
        this.state_tag = state_tag;
        return this;
    }

    public UserBuilder setState(String state) {
        this.state = state;
        return this;
    }

    public UserBuilder setCity_tag(String city_tag) {
        this.city_tag = city_tag;
        return this;
    }

    public UserBuilder setCity(String city) {
        this.city = city;
        return this;
    }

    public UserBuilder setZip_tag(String zip_tag) {
        this.zip_tag = zip_tag;
        return this;
    }

    public UserBuilder setZip(String zip) {
        this.zip = zip;
        return this;
    }

    public UserBuilder setAddress_tag(String address_tag) {
        this.address_tag = address_tag;
        return this;
    }

    public UserBuilder setAddress(String address) {
        this.address = address;
        return this;
    }

    public UserBuilder setEmail_tag(String email_tag) {
        this.email_tag = email_tag;
        return this;
    }

    public UserBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder setSex_tag(String sex_tag) {
        this.sex_tag = sex_tag;
        return this;
    }

    public UserBuilder setSex(String sex) {
        this.sex = sex;
        return this;
    }

    public UserBuilder setPreferredLanguage_tag(String preferredLanguage_tag) {
        this.preferredLanguage_tag = preferredLanguage_tag;
        return this;
    }

    public UserBuilder setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
        return this;
    }

    public UserBuilder setCar_tag(String car_tag) {
        this.car_tag = car_tag;
        return this;
    }

    public UserBuilder setCar(Car car) {
        this.car = car;
        return this;
    }

    public UserBuilder setRating_tag(String rating_tag) {
        this.rating_tag = rating_tag;
        return this;
    }

    public UserBuilder setRating(float rating) {
        this.rating = rating;
        return this;
    }

    public UserBuilder setReview_tag(String review_tag) {
        this.review_tag = review_tag;
        return this;
    }

    public UserBuilder setReview(List<Review> review) {
        this.review = review;
        return this;
    }

    public UserBuilder setTrip_list_tag(String trip_list_tag) {
        this.trip_list_tag = trip_list_tag;
        return this;
    }

    public UserBuilder setTripList(List<Trip> tripList) {
        this.tripList = tripList;
        return this;
    }

    public User createUser() {
        return new User(user_id, first_name_tag, firstName, last_name_tag, lastName, phone_number_tag, phoneNumber, image_uri_tag, imageUri, google_acct_link_tag, googleAcctLink, fb_acct_link_tag, fbAcctLink, age_tag, age, country_tag, country, state_tag, state, city_tag, city, zip_tag, zip, address_tag, address, email_tag, email, sex_tag, sex, preferredLanguage_tag, preferredLanguage, car_tag, car, rating_tag, rating, review_tag, review, trip_list_tag, tripList);
    }

    public UserBuilder(){}
}