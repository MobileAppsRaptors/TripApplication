package com.example.admin.tripapplication.model.firebase;


import android.os.Parcel;
import android.os.Parcelable;

import com.example.admin.tripapplication.model.places.nearbyresult.Location;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Admin on 9/13/2017.
 */

public class Trip implements Parcelable {

    Location origin;
    Location destination;
    Date date;
    double leniancy;
    User creator;
    int seats;
    float cost;
    String description;
    Map<String, User> passengerList;

    public Trip(){};

    public Trip(Location origin, Location destination, Date date, double leniancy, User creator, int seats, float cost, String description, Map<String, User> passengerList) {
        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.leniancy = leniancy;
        this.creator = creator;
        this.seats = seats;
        this.cost = cost;
        this.description = description;
        this.passengerList = passengerList;
    }

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

    public double getLeniancy() {
        return leniancy;
    }

    public void setLeniancy(double leniancy) {
        this.leniancy = leniancy;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, User> getPassengerList() {
        return passengerList;
    }

    public void setPassengerList(Map<String, User> passengerList) {
        this.passengerList = passengerList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.origin, flags);
        dest.writeParcelable(this.destination, flags);
        dest.writeLong(this.date != null ? this.date.getTime() : -1);
        dest.writeDouble(this.leniancy);
        dest.writeParcelable(this.creator, flags);
        dest.writeInt(this.seats);
        dest.writeFloat(this.cost);
        dest.writeString(this.description);
        if(passengerList != null) {
            dest.writeInt(this.passengerList.size());
            for (Map.Entry<String, User> entry : this.passengerList.entrySet()) {
                dest.writeString(entry.getKey());
                dest.writeParcelable(entry.getValue(), flags);
            }
        }
    }

    protected Trip(Parcel in) {
        this.origin = in.readParcelable(Location.class.getClassLoader());
        this.destination = in.readParcelable(Location.class.getClassLoader());
        long tmpDate = in.readLong();
        this.date = tmpDate == -1 ? null : new Date(tmpDate);
        this.leniancy = in.readDouble();
        this.creator = in.readParcelable(User.class.getClassLoader());
        this.seats = in.readInt();
        this.cost = in.readFloat();
        this.description = in.readString();
        int passengerListSize = in.readInt();
        this.passengerList = new HashMap<String, User>(passengerListSize);
        for (int i = 0; i < passengerListSize; i++) {
            String key = in.readString();
            User value = in.readParcelable(User.class.getClassLoader());
            this.passengerList.put(key, value);
        }
    }

    public static final Parcelable.Creator<Trip> CREATOR = new Parcelable.Creator<Trip>() {
        @Override
        public Trip createFromParcel(Parcel source) {
            return new Trip(source);
        }

        @Override
        public Trip[] newArray(int size) {
            return new Trip[size];
        }
    };
}
