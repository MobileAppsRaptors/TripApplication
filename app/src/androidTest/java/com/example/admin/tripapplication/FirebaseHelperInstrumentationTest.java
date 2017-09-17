package com.example.admin.tripapplication;

import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import com.example.admin.tripapplication.data.FirebaseHelper;
import com.example.admin.tripapplication.model.firebase.Car;
import com.example.admin.tripapplication.model.firebase.Trip;
import com.example.admin.tripapplication.model.firebase.User;
import com.example.admin.tripapplication.model.places.nearbyresult.Location;
import com.example.admin.tripapplication.view.loginactivity.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.when;

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

    boolean first_time = true;
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

    @BeforeClass
    public static void init(){
        if (!FirebaseApp.getApps(InstrumentationRegistry.getContext()).isEmpty()) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }
    }

    @Before
    public void Setup() {

        try {
            sign_in();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        make_trip();
        helper = new FirebaseHelper();

    }

    @Test
    public void trip_isAdded_toFirebase() throws Exception{
        assertTrue(helper.AddTrip(trip));
    }

    @Test
    public void user_isAdded_toFirebase() throws Exception{
        assertTrue(helper.AddNewUser(user));
    }

    private CountDownLatch authSignal = null;
    private void sign_in() throws InterruptedException {
        authSignal = new CountDownLatch(1);
        mAuth = FirebaseAuth.getInstance();
        mAuth.addAuthStateListener(mAuthListener);
        System.out.println(TAG + "starting create");
        if(mAuth.getCurrentUser() == null) {
            mAuth.createUserWithEmailAndPassword("jeff@example.com", "aruAeu234#$")
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                System.out.println(TAG + "signInWithEmail:failed" + task.getException());
                            } else {
                                System.out.println(TAG + "signInWithEmail:onComplete:" + task.isSuccessful());
                                fb_user = FirebaseAuth.getInstance().getCurrentUser();
                            }
                            authSignal.countDown();
                        }
                    });
        } else {
            authSignal.countDown();
        }
        authSignal.await(10, TimeUnit.SECONDS);
    }

    private void make_trip(){
        location = new Location();
        location.setLat(80.0);
        location.setLat(20.0);
        date = new Date(10, 3, 3);

        user = new User("manny"
                , "sing"
                , "12345"
                , null
                , null
                , null
                , 20
                , "USA"
                , "Georgia"
                , "Atlanta"
                , "77777"
                , "444 wallaby way"
                , "manny@nope.com"
                , "M"
                , "russian"
                , new Car("ford", "pickup", "1203")
                , (float) 2
                , null
                , null);

        passengerList = new ArrayList<>();
        passengerList.add(user);

        trip = new Trip(location, location, date, 10.0, user, 4, (float) 20.00, passengerList);

    }

    @After
    public void teardown(){

        if(fb_user != null){
            fb_user.delete();
        }

    }


}