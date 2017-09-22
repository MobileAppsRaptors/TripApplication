package com.example.admin.tripapplication.model.firebase;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Admin on 9/13/2017.
 */

public class Review implements Parcelable {


    String reviewer;
    String reviewee;
    int rating;
    String data;

    public Review(){}

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.reviewer);
        dest.writeString(this.reviewee);
        dest.writeInt(this.rating);
        dest.writeString(this.data);
    }

    protected Review(Parcel in) {
        this.reviewer = in.readString();
        this.reviewee = in.readString();
        this.rating = in.readInt();
        this.data = in.readString();
    }

    public static final Parcelable.Creator<Review> CREATOR = new Parcelable.Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel source) {
            return new Review(source);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };
}
