package com.example.admin.tripapplication;


import com.example.admin.tripapplication.data.FirebaseHelper;
import com.example.admin.tripapplication.model.firebase.Car;
import com.example.admin.tripapplication.model.firebase.Trip;
import com.example.admin.tripapplication.model.firebase.User;
import com.example.admin.tripapplication.model.places.nearbyresult.Location;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static junit.framework.Assert.assertTrue;

/**
 * Created by Admin on 9/14/2017.
 */

public class FirebaseHelperUnitTest {

    Location location;
    Date date;
    Trip trip;
    List<User> passengerList;
    User user;

    FirebaseHelper helper;

    @Before
    public void Setup() {
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

        helper = new FirebaseHelper();
    }

    @Test
    public void trip_isAdded_toFirebase() throws Exception{
        assertTrue(helper.AddTrip(trip));
    }
}
