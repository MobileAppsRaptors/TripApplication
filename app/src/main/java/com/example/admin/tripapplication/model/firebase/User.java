package com.example.admin.tripapplication.model.firebase;

import android.net.Uri;

import java.net.URL;
import java.util.List;

/**
 * Created by Admin on 9/13/2017.
 */

public class User {

    String user_id;
    String first_name_tag;
    String firstName;
    String last_name_tag;
    String lastName;
    String phone_number_tag;
    String phoneNumber;
    String image_uri_tag;
    Uri imageUri;
    String google_acct_link_tag;
    URL googleAcctLink;
    String fb_acct_link_tag;
    URL fbAcctLink;
    String age_tag;
    int age;
    String country_tag;
    String country;
    String state_tag;
    String state;
    String city_tag;
    String city;
    String zip_tag;
    String zip;
    String address_tag;
    String address;
    String email_tag;
    String email;
    String sex_tag;
    String sex;
    String preferredLanguage_tag;
    String preferredLanguage;
    String car_tag;
    Car car;
    String rating_tag;
    float rating;
    String review_tag;
    List<Review> review;
    String trip_list_tag;
    List<Trip> tripList;

    public User(String user_id, String first_name_tag, String firstName, String last_name_tag, String lastName, String phone_number_tag, String phoneNumber, String image_uri_tag, Uri imageUri, String google_acct_link_tag, URL googleAcctLink, String fb_acct_link_tag, URL fbAcctLink, String age_tag, int age, String country_tag, String country, String state_tag, String state, String city_tag, String city, String zip_tag, String zip, String address_tag, String address, String email_tag, String email, String sex_tag, String sex, String preferredLanguage_tag, String preferredLanguage, String car_tag, Car car, String rating_tag, float rating, String review_tag, List<Review> review, String trip_list_tag, List<Trip> tripList) {
        this.user_id = user_id;
        this.first_name_tag = first_name_tag;
        this.firstName = firstName;
        this.last_name_tag = last_name_tag;
        this.lastName = lastName;
        this.phone_number_tag = phone_number_tag;
        this.phoneNumber = phoneNumber;
        this.image_uri_tag = image_uri_tag;
        this.imageUri = imageUri;
        this.google_acct_link_tag = google_acct_link_tag;
        this.googleAcctLink = googleAcctLink;
        this.fb_acct_link_tag = fb_acct_link_tag;
        this.fbAcctLink = fbAcctLink;
        this.age_tag = age_tag;
        this.age = age;
        this.country_tag = country_tag;
        this.country = country;
        this.state_tag = state_tag;
        this.state = state;
        this.city_tag = city_tag;
        this.city = city;
        this.zip_tag = zip_tag;
        this.zip = zip;
        this.address_tag = address_tag;
        this.address = address;
        this.email_tag = email_tag;
        this.email = email;
        this.sex_tag = sex_tag;
        this.sex = sex;
        this.preferredLanguage_tag = preferredLanguage_tag;
        this.preferredLanguage = preferredLanguage;
        this.car_tag = car_tag;
        this.car = car;
        this.rating_tag = rating_tag;
        this.rating = rating;
        this.review_tag = review_tag;
        this.review = review;
        this.trip_list_tag = trip_list_tag;
        this.tripList = tripList;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getFirst_name_tag() {
        return first_name_tag;
    }

    public void setFirst_name_tag(String first_name_tag) {
        this.first_name_tag = first_name_tag;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLast_name_tag() {
        return last_name_tag;
    }

    public void setLast_name_tag(String last_name_tag) {
        this.last_name_tag = last_name_tag;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone_number_tag() {
        return phone_number_tag;
    }

    public void setPhone_number_tag(String phone_number_tag) {
        this.phone_number_tag = phone_number_tag;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getImage_uri_tag() {
        return image_uri_tag;
    }

    public void setImage_uri_tag(String image_uri_tag) {
        this.image_uri_tag = image_uri_tag;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public String getGoogle_acct_link_tag() {
        return google_acct_link_tag;
    }

    public void setGoogle_acct_link_tag(String google_acct_link_tag) {
        this.google_acct_link_tag = google_acct_link_tag;
    }

    public URL getGoogleAcctLink() {
        return googleAcctLink;
    }

    public void setGoogleAcctLink(URL googleAcctLink) {
        this.googleAcctLink = googleAcctLink;
    }

    public String getFb_acct_link_tag() {
        return fb_acct_link_tag;
    }

    public void setFb_acct_link_tag(String fb_acct_link_tag) {
        this.fb_acct_link_tag = fb_acct_link_tag;
    }

    public URL getFbAcctLink() {
        return fbAcctLink;
    }

    public void setFbAcctLink(URL fbAcctLink) {
        this.fbAcctLink = fbAcctLink;
    }

    public String getAge_tag() {
        return age_tag;
    }

    public void setAge_tag(String age_tag) {
        this.age_tag = age_tag;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCountry_tag() {
        return country_tag;
    }

    public void setCountry_tag(String country_tag) {
        this.country_tag = country_tag;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState_tag() {
        return state_tag;
    }

    public void setState_tag(String state_tag) {
        this.state_tag = state_tag;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity_tag() {
        return city_tag;
    }

    public void setCity_tag(String city_tag) {
        this.city_tag = city_tag;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip_tag() {
        return zip_tag;
    }

    public void setZip_tag(String zip_tag) {
        this.zip_tag = zip_tag;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getAddress_tag() {
        return address_tag;
    }

    public void setAddress_tag(String address_tag) {
        this.address_tag = address_tag;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail_tag() {
        return email_tag;
    }

    public void setEmail_tag(String email_tag) {
        this.email_tag = email_tag;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex_tag() {
        return sex_tag;
    }

    public void setSex_tag(String sex_tag) {
        this.sex_tag = sex_tag;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPreferredLanguage_tag() {
        return preferredLanguage_tag;
    }

    public void setPreferredLanguage_tag(String preferredLanguage_tag) {
        this.preferredLanguage_tag = preferredLanguage_tag;
    }

    public String getPreferredLanguage() {
        return preferredLanguage;
    }

    public void setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    public String getCar_tag() {
        return car_tag;
    }

    public void setCar_tag(String car_tag) {
        this.car_tag = car_tag;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public String getRating_tag() {
        return rating_tag;
    }

    public void setRating_tag(String rating_tag) {
        this.rating_tag = rating_tag;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getReview_tag() {
        return review_tag;
    }

    public void setReview_tag(String review_tag) {
        this.review_tag = review_tag;
    }

    public List<Review> getReview() {
        return review;
    }

    public void setReview(List<Review> review) {
        this.review = review;
    }

    public String getTrip_list_tag() {
        return trip_list_tag;
    }

    public void setTrip_list_tag(String trip_list_tag) {
        this.trip_list_tag = trip_list_tag;
    }

    public List<Trip> getTripList() {
        return tripList;
    }

    public void setTripList(List<Trip> tripList) {
        this.tripList = tripList;
    }
}
