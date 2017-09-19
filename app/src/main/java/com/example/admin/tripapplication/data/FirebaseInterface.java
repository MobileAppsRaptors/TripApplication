package com.example.admin.tripapplication.data;

import com.example.admin.tripapplication.model.firebase.Trip;
import com.example.admin.tripapplication.model.firebase.User;
import com.firebase.geofire.GeoLocation;
import com.google.firebase.database.DatabaseError;

import java.util.List;

/**
 * Created by Admin on 9/18/2017.
 */

public interface FirebaseInterface {

    void parseTrip(Trip trip);
    void parseGeoFireTrip(String trip_key, GeoLocation geoLocation);
    void geoTripsFullyLoaded();
    void parseUserData(User user);
    void throwError(DatabaseError error);
}
