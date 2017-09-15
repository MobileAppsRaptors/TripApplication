package com.example.admin.tripapplication.model.firebase;


import com.example.admin.tripapplication.model.places.nearbyresult.Location;

import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 9/13/2017.
 */

public class Trip {

    Location origin;
    Location destination;
    Date date;
    double leniancy;
    User creator;
    int seats;
    float cost;
    List<User> passengerList;

    public Trip(Location origin, Location destination, Date date, double leniancy, User creator, int seats, float cost, List<User> passengerList) {
        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.leniancy = leniancy;
        this.creator = creator;
        this.seats = seats;
        this.cost = cost;
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

    public List<User> getPassengerList() {
        return passengerList;
    }

    public void setPassengerList(List<User> passengerList) {
        this.passengerList = passengerList;
    }
}
