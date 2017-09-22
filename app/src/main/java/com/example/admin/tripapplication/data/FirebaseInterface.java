package com.example.admin.tripapplication.data;

import com.example.admin.tripapplication.model.firebase.Review;
import com.example.admin.tripapplication.model.firebase.Trip;
import com.example.admin.tripapplication.model.firebase.User;
import com.example.admin.tripapplication.util.Events;
import com.firebase.geofire.GeoLocation;
import com.google.firebase.database.DatabaseError;

import java.util.List;
import java.util.Map;

/**
 * Created by Admin on 9/18/2017.
 */

public interface FirebaseInterface {

    void parseTrip(String trip_id, Trip trip, Events.MessageEvent messageEvent);
    void parseGeoFireTrip(String trip_key, GeoLocation geoLocation, String source);
    void geoTripsFullyLoaded();
    void parseUserData(String user_id, User user);
    void parseUserReviews(Map<String, Review> reviewList);
    void throwError(DatabaseError error);
    void operationSuccess(String operation);
}
