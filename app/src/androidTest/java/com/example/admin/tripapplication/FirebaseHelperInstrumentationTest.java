package com.example.admin.tripapplication;

import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;

import com.example.admin.tripapplication.data.FirebaseHelper;
import com.example.admin.tripapplication.model.firebase.Car;
import com.example.admin.tripapplication.model.firebase.Trip;
import com.example.admin.tripapplication.model.firebase.User;
import com.example.admin.tripapplication.model.firebase.UserBuilder;
import com.example.admin.tripapplication.model.places.nearbyresult.Location;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static junit.framework.Assert.assertTrue;

/**
 * Created by Admin on 9/14/2017.
 */

public class FirebaseHelperInstrumentationTest {

    public static final String TAG = "UnitTest";

    Location location;
    Date date;
    Trip trip;
    List<User> passengerList;
    User user;

    FirebaseUser fb_user;
    FirebaseHelper helper;
    private FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                // User is signed in
                System.out.println(TAG + "onAuthStateChanged:signed_in:" + user.getUid());
            } else {
                // User is signed out
                System.out.println(TAG + "onAuthStateChanged:signed_out");
            }
            // ...
        }
    };

    @Before
    public void Setup() {
        if(!FirebaseApp.getApps(InstrumentationRegistry.getContext()).isEmpty()) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }
        sign_in();
        make_trip();
        //helper = new FirebaseHelper();

    }

    @Test
    public void trip_isAdded_toFirebase() throws Exception{
        assertTrue(helper.AddTrip(trip));
    }

    //TODO add logic to check if user exists
    private void sign_in(){
        mAuth = FirebaseAuth.getInstance();
        mAuth.addAuthStateListener(mAuthListener);
        System.out.println(TAG + "starting create");
        mAuth.createUserWithEmailAndPassword("jeff@example.com", "aruAeu234#$")
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {
                            System.out.println(TAG + "signInWithEmail:failed" + task.getException());
                        } else {
                            System.out.println(TAG + "signInWithEmail:onComplete:" + task.isSuccessful());
                            fb_user = FirebaseAuth.getInstance().getCurrentUser();
                        }
                    }
                });
    }

    private void make_trip(){
        location = new Location(0.0, 0.0);
        location.setLat(80.0);
        location.setLat(20.0);
        date = new Date(10, 3, 3);

        //user = new UserBuilder().setFirst_name_tag("manny").setFirstName("sing").createUser();
        passengerList = new ArrayList<>();
        passengerList.add(user);

        trip = new Trip(location, location, date, 10.0, user, 4, (float) 20.00, passengerList);

    }

    @After
    public void teardown(){

        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
         if(fb_user != null){
            fb_user.delete();
        }

    }
}