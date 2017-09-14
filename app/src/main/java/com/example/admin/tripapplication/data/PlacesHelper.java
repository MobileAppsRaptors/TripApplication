package com.example.admin.tripapplication.data;

import android.location.Location;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.example.admin.tripapplication.model.places.autocompleteresult.AutocompleteResult;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Admin on 9/12/2017.
 */

public class PlacesHelper {

    //https://maps.googleapis.com/maps/api/place/autocomplete/json?input=ban&key=API_KEY
    //https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=500&type=restaurant&keyword=cruise&key=YOUR_API_KEY

    public static final String TAG = "PlacesAPI";
    public static final String API_KEY = "AIzaSyAWtUVJsk4Ukxs6paYKrPiAmk2uVSdYlyA";

    OkHttpClient client = new OkHttpClient();
    PlacesHelper(){}


    //TODO add language settings

    //https://maps.googleapis.com/maps/api/place/queryautocomplete/json?key=YOUR_API_KEY&language=fr&input=pizza+near%20par
    //https://maps.googleapis.com/maps/api/place/autocomplete/json?input=ban&location=lat,lng&radius=10000&type=address&key=API_KEY
    //types: establishment, address

    //TODO might need to add address type
    public AutocompleteResult GetAutocompleteData(String input) {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("maps.googleapis.com")
                .addPathSegment("maps")
                .addPathSegment("api")
                .addPathSegment("place")
                .addPathSegment("autocomplete")
                .addPathSegment("json")
                .addQueryParameter("type", "geocode")
                .addQueryParameter("input", input)
                .addQueryParameter("key", API_KEY)
                .build();

        final Request request = new Request.Builder().url(url).build();

        String result = "";

        try {
            result = client.newCall(request).execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "GetAutocompleteData: " + result);
        Gson gson = new Gson();
        AutocompleteResult foo = gson.fromJson(result, AutocompleteResult.class);
        return foo;
    }

}

