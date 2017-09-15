package com.example.admin.tripapplication.util;

import java.util.Hashtable;

/**
 * Created by Pancho on 9/12/2017.
 */

//TODO put your constants here
public class CONSTANTS {
    //public static final String constant_example = "";
    //To call this CONSTANTS.constant_example;


    public static final String PACKAGE_NAME = "";

    //Relation name menu -> Class Fragment
    public static Hashtable<String, String> classSubName = new Hashtable<String, String>();
    static {
        classSubName.put("Home","view.homeview.HomeView");

        classSubName.put("Publish a trip","view.tripview.TripView");
        classSubName.put("Search a trip","view.triplistview.TripListView");
        classSubName.put("Messages","view.messagesview.MessagesView");

        classSubName.put("Settings","view.settingsview.SettingsView");
        classSubName.put("About","view.aboutview.AboutView");
    }
}
