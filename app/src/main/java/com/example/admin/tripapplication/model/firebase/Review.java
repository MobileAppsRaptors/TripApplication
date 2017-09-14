package com.example.admin.tripapplication.model.firebase;

/**
 * Created by Admin on 9/13/2017.
 */

public class Review {


    User reviewer;
    int rating;
    String data;

    public Review(User reviewer, String data, int rating) {
        this.reviewer = reviewer;
        this.data = data;
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public User getReviewer() {
        return reviewer;
    }

    public void setReviewer(User reviewer) {
        this.reviewer = reviewer;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
