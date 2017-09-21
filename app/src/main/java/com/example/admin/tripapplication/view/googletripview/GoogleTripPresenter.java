package com.example.admin.tripapplication.view.googletripview;

import android.content.Context;

import com.example.admin.tripapplication.view.googletripview.GoogleTripContract;

public class GoogleTripPresenter implements GoogleTripContract.Presenter {
    GoogleTripContract.View view;
    private static final String TAG = "GoogleTripActivityPresenter";
    private Context context;

    @Override
    public void attachView(GoogleTripContract.View view) {
        this.view = view;
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

}
