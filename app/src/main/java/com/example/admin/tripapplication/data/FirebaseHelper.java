package com.example.admin.tripapplication.data;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.admin.tripapplication.model.firebase.Trip;
import com.example.admin.tripapplication.model.firebase.User;
import com.example.admin.tripapplication.model.places.nearbyresult.Location;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by Admin on 9/12/2017.
 */

public class FirebaseHelper {

    public static final String TAG = "FirebaseHelper";

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

    public void AddImg(Uri uri, User user, String user_id){
        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
        StorageReference riversRef = mStorageRef.child(user_id + "images/" + uri.getLastPathSegment());

        riversRef.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        //TODO put a function to updateimgURL for user
                        //updateImgURL(downloadUrl, user, user_id);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });
    }


    //Not needed i think with updateImgURL is more than enough
    public void DownloadImg(String img_key){
        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
        StorageReference riversRef = mStorageRef.child(img_key);

        File localFile = null;
        try {
            localFile = File.createTempFile("images", "jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        riversRef.getFile(localFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        // Successfully downloaded data to local file
                        // ...
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle failed download
                // ...
            }
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
