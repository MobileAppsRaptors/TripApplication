package com.example.admin.tripapplication.model.firebasemodel;

/**
 * Created by Admin on 9/13/2017.
 */

public class Review {
    User reviewer;
    String data;

    public Review(User reviewer, String data) {
        this.reviewer = reviewer;
        this.data = data;
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
