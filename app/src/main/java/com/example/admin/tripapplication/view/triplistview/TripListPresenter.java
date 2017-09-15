package com.example.admin.tripapplication.view.triplistview;

import android.content.Context;

public class TripListPresenter implements TripListContract.Presenter {
    TripListContract.View view;
    private static final String TAG = "TripListActivityPresenter";
    private Context context;

    @Override
    public void attachView(TripListContract.View view) {
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
