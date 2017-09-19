package com.example.admin.tripapplication.data;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.admin.tripapplication.model.firebase.Review;
import com.example.admin.tripapplication.model.firebase.Trip;
import com.example.admin.tripapplication.model.firebase.User;
import com.example.admin.tripapplication.model.firebase.UserBuilder;
import com.example.admin.tripapplication.model.places.nearbyresult.Location;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by Admin on 9/12/2017.
 */

public class FirebaseHelper {

    public static final String TAG = "FirebaseHelper";
    FirebaseInterface presenter;

    public FirebaseHelper(FirebaseInterface fbInterface){
        this.presenter = fbInterface;
    }

    public boolean AddTrip(Trip trip) {
        final CountDownLatch writeSignal = new CountDownLatch(1);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("trips");

        //This adds a random key under trips
        myRef.push();

        String trip_key = myRef.getKey();
        myRef.child(trip_key).setValue(trip)
                .addOnCompleteListener(new OnCompleteListener<Void>() {

                    @Override
                    public void onComplete(@NonNull final Task<Void> task) {
                        writeSignal.countDown();
                    }
                });

        AddGeoFire(trip_key, trip.getOrigin(), "origin");
        AddGeoFire(trip_key, trip.getDestination(), "destination");

        AddTripUser(trip_key, trip);

        try {
            writeSignal.await(10, TimeUnit.SECONDS);
        }catch(Exception ex){}
        System.out.println(TAG + "Wrote data");

        return true;
    }

    private void AddTripUser(String trip_key, Trip trip) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/trips");

        myRef.push();

        String trip_user_key = myRef.getKey();

        myRef.child(trip_user_key).setValue(trip)
                .addOnCompleteListener(new OnCompleteListener<Void>() {

                    @Override
                    public void onComplete(@NonNull final Task<Void> task) {
                        if (!task.isSuccessful())
                            Log.d(TAG, "onComplete:  Error inside addtripuser" + task.getException().getMessage());
                    }
                });
    }

    private void AddGeoFire(String trip_key, Location location, String source) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("trips/geofire/" + source);
        GeoFire geoFire = new GeoFire(ref);
        geoFire.setLocation(trip_key, new GeoLocation(location.getLat(),location.getLng()), new GeoFire.CompletionListener() {
            @Override
            public void onComplete(String key, DatabaseError error) {
                if (error != null) {
                    System.err.println("There was an error saving the location to GeoFire: " + error);
                } else {
                    System.out.println("Location saved on server successfully!" + key);
                }
            }
        });
    }

    //TODO still needs testing
    public void AddUserReview(Review review){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(review.getReviewee()).child("review");

        myRef.push();
        String r_key = myRef.getKey();

        myRef = database.getReference("review").child(review.getReviewer()).child(r_key);

        myRef.setValue(review).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(!task.isSuccessful()){
                    System.out.println(TAG + " AddUserReview Failed " + task.getException().getMessage());
                    presenter.throwError(DatabaseError.fromException(task.getException()));
                }
            }
        });

    }

    //TODO check if reviews and cars get overwritten
    //TODO reviews need their own list
    public boolean UpdateUser(User user){
        FirebaseUser fb_user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(fb_user.getUid());

        myRef.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(!task.isSuccessful()){
                    System.out.println(TAG + " UpdateUser failed " + task.getException());
                    presenter.throwError(DatabaseError.fromException(task.getException()));
                }
            }
        });
        return true;
    }

    //TODO still needs testing
    public void GetGeoTrips(Location location, float radius){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        GeoFire geoFire = new GeoFire(database.getReference("geofire"));

        GeoQuery geoQuery = geoFire.queryAtLocation(new GeoLocation(location.getLat(), location.getLng()), radius);

        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, GeoLocation location) {
                presenter.parseGeoFireTrip(key, location);
            }

            @Override
            public void onKeyExited(String key) {
                System.out.println(TAG + " GetGeoTrip key no longer matches query" + key);
            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {
                System.out.println(TAG + " GetGeoTrip key is moved" + key);
            }

            @Override
            public void onGeoQueryReady() {
                System.out.println(TAG + " GetGeoTrip query is complete");
                presenter.geoTripsFullyLoaded();
            }

            @Override
            public void onGeoQueryError(DatabaseError error) {
                System.out.println(TAG + " GetGeoTrip error" + error.getMessage());
                presenter.throwError(error);
            }
        });

    }

    //TODO still needs testing
    public void GetTrip(String trip_id){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("trips/" + trip_id);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                presenter.parseTrip((Trip) dataSnapshot.getChildren());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(TAG + " GetTrip read error " + databaseError.getMessage());
                presenter.throwError(databaseError);
            }
        });
    }

    //TODO still needs testing
    public void GetUserData(String user_id){
        //field, tag, setting
        // if tag null read field
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(user_id);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User out_user = (User) dataSnapshot.getValue();
                presenter.parseUserData(out_user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(TAG + " GetUserData Failed" + databaseError.getMessage());
                presenter.throwError(databaseError);
            }
        });

    }

    //TODO find way to deal with rating field
    //TODO figure out how to deal with orphan trips and user data
    public boolean DeleteMyUser(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.delete();
        return true;
    }

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
