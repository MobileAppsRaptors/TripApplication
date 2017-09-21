package com.example.admin.tripapplication.util;

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
    public static final String SIGN_UP_CANCEL = "SIGN_UP_CANCEL";



    // -------------------Used for fragments and Navigation Drawer --------------------------
    public static final String PACKAGE_NAME = "";

    //Relation name menu -> Class Fragment   and Class Fragment -> Menu
    public static Hashtable<String, String> classSubName = new Hashtable<String, String>();
    static {
        classSubName.put("Home","view.homeview.HomeView");
        classSubName.put("HomeView","Home");

        classSubName.put("Publish a trip","view.tripview.TripView");
        classSubName.put("TripView","Publish a trip");

        classSubName.put("Search a trip","view.triplistview.TripListView");
        classSubName.put("TripListView","Search a trip");

        classSubName.put("Messages","view.messagesview.MessagesView");
        classSubName.put("MessagesView","Messages");

        classSubName.put("Settings","view.settingsview.SettingsView");
        classSubName.put("SettingsView","Settings");

        classSubName.put("About","view.aboutview.AboutView");
        classSubName.put("AboutView","About");

        classSubName.put("Profile","view.profileview.ProfileView");
        classSubName.put("ProfileView","Profile");
    }
}
