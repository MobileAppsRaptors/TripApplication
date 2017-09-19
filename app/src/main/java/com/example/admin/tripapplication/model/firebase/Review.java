package com.example.admin.tripapplication.model.firebase;

/**
 * Created by Admin on 9/13/2017.
 */

public class Review {


    String reviewer;
    String reviewee;
    int rating;
    String data;

    public Review(String reviewer, String reviewee, int rating, String data) {
        this.reviewer = reviewer;
        this.reviewee = reviewee;
        this.rating = rating;
        this.data = data;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public String getReviewee() {
        return reviewee;
    }

    public void setReviewee(String reviewee) {
        this.reviewee = reviewee;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
