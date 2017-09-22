package com.example.admin.tripapplication.view.mytripsview;

import android.content.Context;

/**
 * Created by Admin on 9/22/2017.
 */

public class MyTripsPresenter implements MyTripsContract.Presenter{
    MyTripsContract.View view;
    private static final String TAG = "ProfilePresenter";
    private Context context;

    @Override
    public void attachView(MyTripsContract.View view) {
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
