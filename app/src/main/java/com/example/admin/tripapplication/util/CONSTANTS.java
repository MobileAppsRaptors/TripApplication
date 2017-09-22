package com.example.admin.tripapplication.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

/**
 * Created by Pancho on 9/12/2017.
 */

//TODO put your constants here
public class CONSTANTS {
    //public static final String constant_example = "";
    //To call this CONSTANTS.constant_example;

    //PlacesHelper Constants
    public static final String API_KEY = "AIzaSyAWtUVJsk4Ukxs6paYKrPiAmk2uVSdYlyA";
    public static final String URL_SCHEME = "https";
    public static final String GOOGLE_PLACES_HOST = "maps.googleapis.com";
    public static final String AUTOCOMPLETE_GOOGLEAPI_PATH = "maps/api/place/autocomplete/json";

    //Tag constants
    public static final String USER_ID = "USER_ID";
    public static final String ADD_IMG_SUCC = "ADD_IMG_SUCC";
    public static final String ADD_IMG_FAIL = "ADD_IMG_FAIL";
    public static final String ADD_USER_SUCC = "ADD_USER_SUCC";
    public static final String ADD_USER_FAIL = "ADD_USER_FAIL";
    public static final String GET_USER_SUCC = "GET_USER_SUCC";
    public static final String GET_USER_FAIL = "GET_USER_FAIL";
    public static final String ADD_TRIP_SUCC = "ADD_TRIP_SUCC";
    public static final String GET_TRIP_FAIL = "GET_TRIP_FAIL";
    public static final String ADD_REVIEW_SUCC = "ADD_REVIEW_SUCC";
    public static final String GET_REVIEW_SUCC = "GET_REVIEW_SUCC";
    public static final String SIGN_UP_CANCEL = "SIGN_UP_CANCEL";

    public static final String MY_PREFS = "prefs";
    public static final String MY_PREFS_ZIP = "zip";
    public static final String MY_PREFS_UNITS = "units";

    //Event constants
    public static final int ACTIVITY_SUCC = 123;
    public static final int ACTIVITY_LOG_OUT = 1234;
    public static final String START_SIGNUP_ACTIVITY = "START_SIGNUP_ACTIVITY";
    public static final String START_GOOGLE_TRIP = "START_GOOGLE_TRIP";
    public static final String START_PUBLISH = "START_PUBLISH";
    public static final String START_PROFILE = "START_PROFILE";
    public static final String START_MESSAGE = "START_MESSAGE";
    public static final String START_SEARCH = "START_SEARCH";
    public static final String PASS_VALUES_GOOGLE = "PASS_VALUES_GOOGLE";
    public static final String UPDATED_USER_PROFILE = "UPDATED_USER_PROFILE";
    public static final String PARSE_SUBMITTED_REVIEW = "PARSE_SUBMITTED_REVIEW";
    public static final String OPEN_TRIP_VIEW = "OPEN_TRIP_VIEW";
    public static final String PASS_VALUES_MYTRIP ="PASS_VALUES_MYTRIP";

    // -------------------Used for fragments and Navigation Drawer --------------------------
    public static final String PACKAGE_NAME = "";

    //Review fragment
    public static final String CLEAR_STAR = "\uf006";
    public static final String FILLED_STAR = "\uf005";
    public static final String HALF_STAR = "\uf123";

    //Relation name menu -> Class Fragment   and Class Fragment -> Menu
    public static Hashtable<String, String> classSubName = new Hashtable<String, String>();
    static {
        classSubName.put("Home","view.homeview.HomeView");
        classSubName.put("HomeView","Home");

        classSubName.put("Publish a trip","view.tripview.TripView");
        classSubName.put("TripView","Publish a trip");

        classSubName.put("Search a trip","view.triplistview.TripListView");
        classSubName.put("TripListView","Search a trip");

        classSubName.put("View Trips","view.mytripsview.MyTripsView");
        classSubName.put("TripView","View Trips");

        classSubName.put("Messages","view.messagesview.MessagesView");
        classSubName.put("MessagesView","Messages");

        classSubName.put("Settings","view.settingsview.SettingsView");
        classSubName.put("SettingsView","Settings");

        classSubName.put("About","view.aboutview.AboutView");
        classSubName.put("AboutView","About");

        classSubName.put("Profile","view.profileview.ProfileView");
        classSubName.put("ProfileView","Profile");

        classSubName.put("Trips near your area","view.googletripview.GoogleTripView");
        classSubName.put("GoogleTripView","Trips near your area");
    }
}
