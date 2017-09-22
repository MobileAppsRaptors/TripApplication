package com.example.admin.tripapplication.view.tripinfoview;

import android.content.Context;

import com.example.admin.tripapplication.model.firebase.Trip;
import com.example.admin.tripapplication.view.tripinfoview.TripInfoContract;

public class TripInfoPresenter implements TripInfoContract.Presenter{
    TripInfoContract.View view;
    private static final String TAG = "TripInfoPresenter";
    private Context context;

    @Override
    public void attachView(TripInfoContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

}
