package com.example.admin.tripapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.admin.tripapplication.model.places.nearbyresult.Location;

import java.util.Date;

public class SearchTrip implements Parcelable {
    Location origin;
    Location destination;
    Date date;
    float radius;


    public SearchTrip(Location origin, Location destination, Date date, float radius) {
        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.radius = radius;
    }

    protected SearchTrip(Parcel in) {
        radius = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(radius);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SearchTrip> CREATOR = new Creator<SearchTrip>() {
        @Override
        public SearchTrip createFromParcel(Parcel in) {
            return new SearchTrip(in);
        }

        @Override
        public SearchTrip[] newArray(int size) {
            return new SearchTrip[size];
        }
    };

    public Location getOrigin() {
        return origin;
    }

    public void setOrigin(Location origin) {
        this.origin = origin;
    }

    public Location getDestination() {
        return destination;
    }

    public void setDestination(Location destination) {
        this.destination = destination;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
}
