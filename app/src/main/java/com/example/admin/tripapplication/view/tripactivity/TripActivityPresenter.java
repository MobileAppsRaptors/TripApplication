package com.example.admin.tripapplication.view.tripactivity;

import android.content.Context;

public class TripActivityPresenter implements TripActivityContract.Presenter {
    TripActivityContract.View view;
    private static final String TAG = "TripActivityPresenter";
    private Context context;

    @Override
    public void attachView(TripActivityContract.View view) {
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
