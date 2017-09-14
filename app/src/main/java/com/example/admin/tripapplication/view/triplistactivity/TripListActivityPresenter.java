package com.example.admin.tripapplication.view.triplistactivity;

import android.content.Context;

public class TripListActivityPresenter implements TripListActivityContract.Presenter {
    TripListActivityContract.View view;
    private static final String TAG = "TripListActivityPresenter";
    private Context context;

    @Override
    public void attachView(TripListActivityContract.View view) {
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
