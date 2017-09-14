package com.example.admin.tripapplication.data;

import android.location.Location;

import com.example.admin.tripapplication.model.firebase.Trip;
import com.example.admin.tripapplication.model.firebase.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**
 * Created by Admin on 9/12/2017.
 */

public class FirebaseHelper {

    public boolean AddTrip(Trip trip){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("trips");

        myRef.setValue(trip);
        return true;
    }

    public void GetTrips(Location location, int radius){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("trips");
        myRef.orderByKey().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                System.out.println(dataSnapshot.getKey());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}

            // ...
        });
    }

    /*public ArrayList<Trip> GetTrips(String address, int radius){

    }



    public boolean AddNewUser(User user){
        return false;
    }

    public User GetPublicUserData(){

    }

    public User GetPrivateUserData(){

    }

    public boolean UpdateUser(User user){
        return false;
    }

    public boolean DeleteUser(User user){
        return false;
    }*/
}
