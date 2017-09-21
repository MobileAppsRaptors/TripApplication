package com.example.admin.tripapplication.data;

import android.net.Uri;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static com.example.admin.tripapplication.util.CONSTANTS.*;

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

        //This adds a random key under trip

        String trip_key = myRef.push().getKey();
        myRef.child(trip_key).setValue(trip)
                .addOnCompleteListener(new OnCompleteListener<Void>() {

                    @Override
                    public void onComplete(@NonNull final Task<Void> task) {
                        if (!task.isSuccessful()) {
                            System.out.println(TAG + "Trip add Failed");
                        } else {
                            System.out.println(TAG + "Trip Added");
                        }
                    }
                });

        AddTripUser(trip_key, trip);

        return true;
    }

    private void AddTripUser(final String trip_key, final Trip trip) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/tripList");

        myRef.child(trip_key).setValue(trip)
                .addOnCompleteListener(new OnCompleteListener<Void>() {

                    @Override
                    public void onComplete(@NonNull final Task<Void> task) {
                        if (!task.isSuccessful()) {
                            Log.d(TAG, "onComplete:  Error inside addtripuser" + task.getException().getMessage());
                            presenter.throwError(DatabaseError.fromException(task.getException()));
                        } else {
                            AddGeoFire(trip_key, trip.getOrigin(), trip.getDestination());
                        }
                    }
                });
    }

    private void AddGeoFire(final String trip_key, Location origin, final Location destination) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("geofire/origin");
        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("geofire/destination");
        final GeoFire geoFire = new GeoFire(ref);
        final GeoFire geofire1 = new GeoFire(ref2);
        geoFire.setLocation(trip_key, new GeoLocation(origin.getLat(),origin.getLng()), new GeoFire.CompletionListener() {
            @Override
            public void onComplete(String key, DatabaseError error) {
                if (error != null) {
                    System.err.println("There was an error saving the location to GeoFire: " + error);
                    presenter.throwError(error);
                } else {
                    System.out.println("Location saved on server successfully!" + key);
                    geofire1.setLocation(trip_key, new GeoLocation(destination.getLat(), destination.getLng()), new GeoFire.CompletionListener() {
                        @Override
                        public void onComplete(String key, DatabaseError error) {
                            if(error != null){
                                System.err.println("There was an error saving the location to GeoFire: " + error);
                                presenter.throwError(error);
                            } else {
                                presenter.operationSuccess(ADD_TRIP_SUCC);
                            }
                        }
                    });
                }
            }
        });
    }

    //TODO still needs testing
    public void AddUserReview(Review review){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(review.getReviewee()).child("review");

        String r_key = myRef.push().getKey();

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

    public void GetUserReviews(String user_id){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference myRef = db.getReference("users").child(user_id).child("review");
        final Map<String, Review> out_map = new HashMap<>();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    out_map.put(data.getKey(), data.getValue(Review.class));
                }
                presenter.parseUserReviews(out_map);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                presenter.throwError(databaseError);
            }
        });
    }

    //TODO check if reviews and cars get overwritten
    //TODO reviews need their own list
    public boolean UpdateUser(User user, Uri customImg) {

        if(customImg == null)
            UpdateUser(user);
        else {
            //First insert image img then that calls updateuser
            FirebaseUser fb_user = FirebaseAuth.getInstance().getCurrentUser();
            AddImg(user, customImg, fb_user.getUid());
        }

        return true;
    }

    public void UpdateUser(User user){
        FirebaseUser fb_user = FirebaseAuth.getInstance().getCurrentUser();


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(fb_user.getUid());

        final FirebaseInterface presenter = this.presenter;
        myRef.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(!task.isSuccessful()){
                    System.out.println(TAG + " UpdateUser failed " + task.getException());
                    presenter.throwError(DatabaseError.fromException(task.getException()));
                } else {

                    presenter.operationSuccess(ADD_USER_SUCC);
                }
            }
        });
    }

    //TODO still needs testing
    public void GetGeoTrips(Location location, float radius){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        GeoFire geoFire = new GeoFire(database.getReference("geofire/destination"));

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

    public void AddImg(final User user, final Uri uri, final String user_id){
        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
        StorageReference riversRef = mStorageRef.child(user_id + "images/" + uri.getLastPathSegment());

        riversRef.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        //TODO put a function to updateimgURL for user
                        user.setImageURL(downloadUrl.toString());
                        UpdateUser(user);
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

    //TODO still needs testing
    public void GetTrip(String trip_id){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("trips/" + trip_id);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                presenter.parseTrip(dataSnapshot.getValue(Trip.class));
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
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(user_id);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User out_user = dataSnapshot.getValue(User.class);
                presenter.parseUserData(out_user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(TAG + " GetUserData Failed" + databaseError.getMessage());
                presenter.throwError(databaseError);
            }
        });
    }

    public void GetPublicUserData(String user_id){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference pictureRef = database.getReference("users").child(user_id).child("imageURL");
        DatabaseReference firstNameRef = database.getReference("users").child(user_id).child("firstName");
        DatabaseReference lastNameRef = database.getReference("users").child(user_id).child("lastName");
        DatabaseReference phoneRef = database.getReference("users").child(user_id).child("phoneNumber");
        DatabaseReference emailRef = database.getReference("users").child(user_id).child("email");
        DatabaseReference genRef = database.getReference("users").child(user_id).child("sex");

        final User user = new UserBuilder().setUser_id(user_id).createUser();
        final DatabaseError[] throw_error = {null};

        //get map of sources
        //map of tasks
        //get list from map

        final TaskCompletionSource<DataSnapshot> dbPictureSource = new TaskCompletionSource<>();
        final TaskCompletionSource<DataSnapshot> dbFirstNameSource = new TaskCompletionSource<>();
        final TaskCompletionSource<DataSnapshot> dbLastNameSource = new TaskCompletionSource<>();
        final TaskCompletionSource<DataSnapshot> dbPhoneNumberSource = new TaskCompletionSource<>();
        final TaskCompletionSource<DataSnapshot> dbEmailSource = new TaskCompletionSource<>();
        final TaskCompletionSource<DataSnapshot> dbGenSource = new TaskCompletionSource<>();
        final Task dbPictureTask = dbPictureSource.getTask();
        final Task dbLastNameTask = dbLastNameSource.getTask();
        final Task dbFirstNameTask = dbFirstNameSource.getTask();
        final Task dbPhoneNumberTask = dbPhoneNumberSource.getTask();
        final Task dbEmailTask = dbEmailSource.getTask();
        final Task dbGenTask = dbGenSource.getTask();

        pictureRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dbPictureSource.setResult(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                dbPictureSource.setException(databaseError.toException());
            }
        });

        firstNameRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dbFirstNameSource.setResult(dataSnapshot);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                dbFirstNameSource.setException(databaseError.toException());
            }
        });

        lastNameRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println(TAG + " Getting Public Data");
                dbLastNameSource.setResult(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                dbLastNameSource.setException(databaseError.toException());
            }
        });

        phoneRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dbPhoneNumberSource.setResult(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                dbPhoneNumberSource.setException(databaseError.toException());
            }
        });

        emailRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dbEmailSource.setResult(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                dbEmailSource.setException(databaseError.toException());
            }
        });

        genRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dbGenSource.setResult(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                dbGenSource.setException(databaseError.toException());
            }
        });

        Task allTask = Tasks.whenAll(dbPictureTask,dbFirstNameTask,dbLastNameTask,dbPhoneNumberTask,dbEmailTask);
        allTask.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                DataSnapshot data;
                //Picture
                data = (DataSnapshot) dbPictureTask.getResult();
                user.setImageURL(data.getValue(String.class));
                //Firstname
                data = (DataSnapshot) dbFirstNameTask.getResult();
                user.setFirstName(data.getValue(String.class));
                //Lastname
                data = (DataSnapshot) dbLastNameTask.getResult();
                user.setLastName(data.getValue(String.class));
                //Phone number
                data = (DataSnapshot) dbPhoneNumberTask.getResult();
                user.setPhoneNumber(data.getValue(String.class));
                //Email
                data = (DataSnapshot) dbEmailTask.getResult();
                user.setEmail(data.getValue(String.class));
                //Gender
                data = (DataSnapshot) dbGenTask.getResult();
                user.setSex(data.getValue(String.class));
                presenter.parseUserData(user);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                presenter.throwError(DatabaseError.fromException(e));
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
