package com.example.admin.tripapplication.data;

import android.location.Location;
import android.support.annotation.NonNull;

import com.example.admin.tripapplication.model.firebase.Trip;
import com.example.admin.tripapplication.model.firebase.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by Admin on 9/12/2017.
 */

public class FirebaseHelper {

    public static final String TAG = "FirebaseHelper";

    public boolean AddTrip(Trip trip) throws InterruptedException {
        final CountDownLatch writeSignal = new CountDownLatch(1);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("trips");

        myRef.setValue(trip)
                .addOnCompleteListener(new OnCompleteListener<Void>() {

                    @Override
                    public void onComplete(@NonNull final Task<Void> task) {
                        writeSignal.countDown();
                    }
                });

        writeSignal.await(10, TimeUnit.SECONDS);
        System.out.println(TAG + "Wrote data");
        return true;
    }

    public void GetTrips(Location location, int radius){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("/trips/");
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

    private float degreesToRadians(float degrees) {
        return degrees * ((float)Math.PI) / 180;
    }

    private float distanceInKmBetweenEarthCoordinates(float lat1, float lon1, float lat2, float lon2) {
        float earthRadiusKm = 6371;

        float dLat = degreesToRadians(lat2-lat1);
        float dLon = degreesToRadians(lon2-lon1);

        lat1 = degreesToRadians(lat1);
        lat2 = degreesToRadians(lat2);

        float a = (float) Math.sin(dLat/2) * (float) Math.sin(dLat/2) +
                (float) Math.sin(dLon/2) * (float) Math.sin(dLon/2) * (float) Math.cos(lat1) * (float) Math.cos(lat2);
        float c = 2 * (float) Math.atan2((float) Math.sqrt(a), (float) Math.sqrt(1-a));
        return earthRadiusKm * c;
    }
}
