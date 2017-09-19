package com.example.admin.tripapplication.view.tripview;

import android.content.Context;

import com.example.admin.tripapplication.data.FirebaseHelper;
import com.example.admin.tripapplication.model.firebase.Trip;

public class TripPresenter implements TripContract.Presenter {
    TripContract.View view;
    private static final String TAG = "TripActivityPresenter";
    private Context context;

    @Override
    public void attachView(TripContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void InsertTrip(Trip trip){

    }

}
